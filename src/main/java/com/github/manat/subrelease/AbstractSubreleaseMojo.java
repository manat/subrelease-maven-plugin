/**
 *
 */
package com.github.manat.subrelease;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Base class with shared configuration.
 */
public abstract class AbstractSubreleaseMojo extends AbstractMojo {

    /**
     * Location of the base directory.
     */
    @Parameter(defaultValue = "${basedir}", readonly = true, required = true)
    String baseDir;

    /**
     * Location where the code of dependency projects are resided.
     */
    @Parameter(defaultValue = "${project.build.directory}/dependency/workspace/",
               property = "dependencyWorkspace", required = true)
    String dependencyWorkspace;

    /**
     * The message prefix to use for all SCM changes.
     * <p>
     * Default is "[maven-release-plugin]".
     */
    @Parameter(defaultValue = "[maven-release-plugin]",
               property = "scmCommentPrefix")
    String scmCommentPrefix;

    /**
     * The SCM username to use.
     */
    @Parameter(property = "username")
    String username;

    /**
     * The SCM password to use.
     */
    @Parameter(property = "password")
    String password;

    /**
     * Specifies an alternative repository to which the project artifacts should be deployed ( other
     * than those specified in <distributionManagement> ). Format: id::layout::url
     */
    @Parameter(property = "altDeploymentRepository")
    String altDeploymentRepository;
}
