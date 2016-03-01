package com.github.manat.subrelease;

import static java.lang.System.currentTimeMillis;
import static java.nio.file.Paths.get;

import java.sql.Timestamp;

import com.github.manat.subrelease.actions.DefaultActor;
import com.github.manat.subrelease.actions.Subrelease;
import com.github.manat.subrelease.invoker.Invoker;
import com.github.manat.subrelease.invoker.MavenInvoker;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basically performs mvn release:prepare through standard maven-release-plugin.
 *
 * @see http://maven.apache.org/plugins/maven-release-plugin/examples/prepare-release.html
 */
@Mojo(name = "release")
public class ReleaseMojo extends AbstractSubreleaseMojo {

    private final Logger logger = LoggerFactory.getLogger(PrepareMojo.class);

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Invoker invoker = new MavenInvoker(get(baseDir));
        Subrelease actor = new DefaultActor(invoker);

        if (actor.release()) {
            logger.info("----- subrelease -----");
            logger.info("Finished subrelease:release at {}", new Timestamp(currentTimeMillis()));
            logger.info("----------------------");
        }
    }
}
