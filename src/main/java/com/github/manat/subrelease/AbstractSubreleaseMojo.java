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
    @Parameter(defaultValue = "${project.build.directory}/dependency/workspace/", property = "dependencyWorkspace", required = true)
    String dependencyWorkspace;
}
