/**
 *
 */
package com.github.manat.subrelease;

import static java.nio.file.Paths.get;

import com.github.manat.subrelease.actions.DefaultActor;
import com.github.manat.subrelease.actions.Subrelease;
import com.github.manat.subrelease.invoker.Invoker;
import com.github.manat.subrelease.invoker.MavenInvoker;
import com.github.manat.subrelease.model.Dependency;
import com.github.manat.subrelease.reader.DefaultOutputReader;
import com.github.manat.subrelease.reader.OutputReader;
import com.github.manat.subrelease.reader.PomReader;
import com.github.manat.subrelease.reader.XpathPomReader;
import com.github.manat.subrelease.writer.StringContentWriter;
import com.github.manat.subrelease.writer.PomWriter;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Prepares for a release in SCM, by automatically releasing any SNAPSHOT dependencies, then
 * prepares a release of itself through standard maven-release-plugin.
 *
 * @see http://maven.apache.org/plugins/maven-release-plugin/examples/prepare- release.html
 * @see https://github.com/manat/subrelease-maven-plugin
 */
@Mojo(name = "prepare", aggregator = true)
public class SubreleaseMojo extends AbstractSubreleaseMojo {

    private final Logger logger = LoggerFactory.getLogger(SubreleaseMojo.class);

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Invoker invoker = new MavenInvoker(get(baseDir));
        Subrelease actor = new DefaultActor(invoker);
        List<Dependency> snapshotDependencies = new ArrayList<>();

        Path resolvedDepPath = get(baseDir, "resolvedDependency.txt");
        if (actor.resolveDependency(resolvedDepPath)) {
            OutputReader reader = new DefaultOutputReader();
            List<Dependency> dependencies = reader.getResolvedSnapshotArtifacts(resolvedDepPath);

            for (Dependency dependency : dependencies) {
                logger.debug("Starts unpacking: {}.", dependency);
                actor.unpackArtifact(dependency);
                Path subProjectPath = get(dependencyWorkspace, dependency.getArtifactId());
                Path sumProjectPomPath = get(subProjectPath.toString(), "META-INF", "maven",
                        dependency.getGroupId(), dependency.getArtifactId(), "pom.xml");

                PomReader pomReader = new XpathPomReader(sumProjectPomPath);
                String connection = pomReader.getScmConnection();

                logger.info("Starts Checking out: {} from url={}.", dependency, connection);
                if (actor.checkout(dependency, connection)) {
                    Invoker subInvoker = new MavenInvoker(subProjectPath);
                    Subrelease subActor = new DefaultActor(subInvoker);

                    if (subActor.release() && subActor.perform()) {
                        logger.info("Subactor for ({}) has released completely.");
                        snapshotDependencies.add(dependency);
                    }
                }
            }

            PomWriter pomWriter = new StringContentWriter(get(baseDir, "pom.xml"));
            pomWriter.updateSnapshotVersion(snapshotDependencies);

            if (actor.commit() && actor.release()) {
                logger.info("Subrelease Completed at {}.", Calendar.getInstance());
            }
        }
    }

}
