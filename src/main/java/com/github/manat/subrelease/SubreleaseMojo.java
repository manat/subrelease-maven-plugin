/**
 *
 */
package com.github.manat.subrelease;

import static java.nio.file.Paths.get;

import com.github.manat.subrelease.actions.DefaultActor;
import com.github.manat.subrelease.actions.Subrelease;
import com.github.manat.subrelease.invoker.Invoker;
import com.github.manat.subrelease.invoker.MavenInvoker;
import com.github.manat.subrelease.model.Artifact;
import com.github.manat.subrelease.reader.DefaultOutputReader;
import com.github.manat.subrelease.reader.OutputReader;
import com.github.manat.subrelease.reader.PomReader;
import com.github.manat.subrelease.reader.XpathPomReader;
import com.github.manat.subrelease.writer.FileWriter;
import com.github.manat.subrelease.writer.PomWriter;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Invoker invoker = new MavenInvoker(get(baseDir));
        Subrelease actor = new DefaultActor(invoker);
        List<Artifact> snapshotDependencies = new ArrayList<>();

        Path resolvedDepPath = get(baseDir, "resolvedDependency.txt");
        if (actor.resolveDependency(resolvedDepPath)) {
            OutputReader reader = new DefaultOutputReader();
            List<Artifact> artifacts = reader.getResolvedSnapshotArtifacts(resolvedDepPath);

            for (Artifact artifact : artifacts) {
                actor.unpackArtifact(artifact);
                Path subProjectPath = get(dependencyWorkspace, artifact.getArtifactId());
                Path sumProjectPomPath = get(subProjectPath.toString(), "META-INF", "maven",
                        artifact.getGroupId(), artifact.getArtifactId(), "pom.xml");

                System.out.println(
                        "\n\n\nsumProjectPomPath: " + sumProjectPomPath.toString() + "\n\n\n");

                PomReader pomReader = new XpathPomReader(sumProjectPomPath);
                String connection = pomReader.getScmConnection();

                System.out.println("\n\nStart Checking out: " + connection + "\n\n");
                if (actor.checkout(artifact, connection)) {
                    Invoker subInvoker = new MavenInvoker(subProjectPath);
                    Subrelease subActor = new DefaultActor(subInvoker);

                    if (subActor.release() && subActor.perform()) {
                        System.out.println(
                                "\n\n\nSubactor Release Completed (" + artifact.toString()
                                        + ")\n\n\n");
                        snapshotDependencies.add(artifact);
                    }
                }
            }

            PomWriter pomWriter = new FileWriter(get(baseDir, "pom.xml"));
            pomWriter.updateSnapshotVersion(snapshotDependencies);

            if (actor.commit()) {
                if (actor.release()) {
                    System.out.println("Subrelease Completed at: " + Calendar.getInstance());
                }
            }
        }
    }

}
