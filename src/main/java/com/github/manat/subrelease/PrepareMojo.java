package com.github.manat.subrelease;

import static com.github.manat.subrelease.model.MvnOptions.*;
import static java.lang.System.currentTimeMillis;
import static java.nio.file.Paths.get;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.github.manat.subrelease.actions.DefaultActor;
import com.github.manat.subrelease.actions.Subrelease;
import com.github.manat.subrelease.invoker.Invoker;
import com.github.manat.subrelease.invoker.MavenInvoker;
import com.github.manat.subrelease.model.Dependency;
import com.github.manat.subrelease.reader.DefaultOutputReader;
import com.github.manat.subrelease.reader.OutputReader;
import com.github.manat.subrelease.reader.PomReader;
import com.github.manat.subrelease.reader.XpathPomReader;
import com.github.manat.subrelease.writer.PomWriter;
import com.github.manat.subrelease.writer.StringContentWriter;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prepares for a release in SCM, by automatically releasing any SNAPSHOT dependencies, then updates
 * SNAPSHOT dependencies of master project to be released version.
 */
@Mojo(name = "prepare")
public class PrepareMojo extends AbstractSubreleaseMojo {

    private final Logger logger = LoggerFactory.getLogger(PrepareMojo.class);

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        logger.info("----- subrelease -----");
        logger.info("Preparing {}", baseDir);
        Invoker invoker = new MavenInvoker(get(baseDir));
        Subrelease actor = new DefaultActor(invoker);
        List<Dependency> snapshotDependencies = new ArrayList<>();

        Path resolvedDepPath = get(baseDir, "resolvedDependency.txt");
        if (actor.resolveDependency(resolvedDepPath)) {
            OutputReader reader = new DefaultOutputReader();
            List<Dependency> dependencies = reader.getResolvedSnapshotArtifacts(resolvedDepPath);

            for (Dependency dependency : dependencies) {
                if (actor.releaseExists(dependency)) {
                    snapshotDependencies.add(dependency);
                } else {
                    boolean isDepRelease = releaseDependency(actor, dependency);
                    if (isDepRelease) {
                        snapshotDependencies.add(dependency);
                    }
                }
            }

            if (snapshotDependencies.containsAll(dependencies) && (dependencies
                    .containsAll(snapshotDependencies))) {
                PomWriter pomWriter = new StringContentWriter(get(baseDir, "pom.xml"));
                pomWriter.updateSnapshotVersion(snapshotDependencies);

                if (actor.commit(makeOpt(SCM_COMMENT_PREFIX))) {
                    logger.info("----- subrelease commit-----");
                    logger.info(
                            "Successfully updates SNAPSHOT dependencies, and commit a change to pom.");
                    logger.info("----------------------");
                }
            }
        }

        logger.info("----- subrelease -----");
        logger.info("Finished subrelease:subprepare at {}", new Timestamp(currentTimeMillis()));
        logger.info("----------------------");
    }

    private boolean releaseDependency(Subrelease actor, Dependency dependency) {
        logger.info("\n\n--- Starts unpacking: {} ---\n\n", dependency);
        actor.unpackArtifact(dependency);
        Path subProjectPath = get(dependencyWorkspace, dependency.getArtifactId());
        Path sumProjectPomPath = get(subProjectPath.toString(), "META-INF", "maven",
                dependency.getGroupId(), dependency.getArtifactId(), "pom.xml");

        PomReader pomReader = new XpathPomReader(sumProjectPomPath);
        String connection = pomReader.getScmConnection();

        logger.info("\n\n--- Starts Checking out: {} from url={} ---\n\n", dependency, connection);
        if (actor.checkout(dependency, connection)) {
            Invoker subInvoker = new MavenInvoker(subProjectPath);
            Subrelease subActor = new DefaultActor(subInvoker);

            boolean releaseResult = subActor.release(makeOpt(SCM_COMMENT_PREFIX));
            boolean performResult = false;
            if (!releaseResult) {
                performResult = subActor
                        .perform(makArgsOpts(ALT_DEPLOY_REPO), makeOpt(SCM_USERNAME),
                                makeOpt(SCM_PASSWORD));
            }

            logger.info("\n\n--- Result of subactor for {}: ---", dependency);
            logger.info("\trelease result: {}", releaseResult);
            logger.info("\tperform result: {}\n\n\n", performResult);

            return releaseResult && performResult;
        }

        return false;
    }

    private String makArgsOpts(String key) {
        String opt = makeOpt(key);
        if (opt == null) {
            return null;
        }

        return "-Darguments=-D" + opt;
    }

    private String makeOpt(String key) {
        try {
            Field field = this.getClass().getSuperclass().getDeclaredField(key);
            field.setAccessible(true);

            return makeOpt(key, (String) field.get(this));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String makeOpt(String key, String value) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }

        return key + "=" + value;
    }
}
