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
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import java.nio.file.Path;
import java.util.ArrayList;
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
            List<Artifact> artifacts = reader.getResolvedArtifacts(resolvedDepPath);

            for (Artifact artifact : artifacts) {
                actor.unpackArtifact(artifact);
                Path subProjectPath = get(dependencyWorkspace, artifact.getArtifactId());
                PomReader pomReader = new XpathPomReader(subProjectPath);
                String connection = pomReader.getScmConnection();

                if (actor.checkout(artifact, connection)) {
                    Invoker subInvoker = new MavenInvoker(subProjectPath);
                    Subrelease subActor = new DefaultActor(subInvoker);

                    if (subActor.release()) {
                        snapshotDependencies.add(artifact);
                    }
                }
            }

            for (Artifact artifact : snapshotDependencies) {

            }
        }
    }

}
