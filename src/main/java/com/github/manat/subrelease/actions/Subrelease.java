package com.github.manat.subrelease.actions;

import com.github.manat.subrelease.model.Dependency;

import java.nio.file.Path;

/**
 * This interface class describes appropriate actions those are required to perform a series of
 * subrelease.
 */
public interface Subrelease {

    /**
     * Executes mvn dependency:resolve, then the result will be stored at the given output path.
     *
     * @param output resolved dependencies are stored here
     * @return true for success; false otherwise
     */
    boolean resolveDependency(Path output);

    /**
     * Executes mvn dependency:unpack of the given dependency value.
     *
     * @param dependency an Dependency to be unpack
     * @return true for success; false otherwise
     */
    boolean unpackArtifact(Dependency dependency);

    /**
     * Executes mvn scm:checkout of the given dependency, using provided connection.
     *
     * @param dependency   an Dependency to be checkout
     * @param connection an information indicates scm type and connection url
     * @return true for success; false otherwise
     */
    boolean checkout(Dependency dependency, String connection);

    /**
     * Executes mvn release:prepare.
     *
     * @return true for success; false otherwise
     */
    boolean release();

    /**
     * Executes mvn release:perform which bacically to deploy artifacts.
     *
     * @return true for success; false otherwise
     */
    boolean perform();

    /**
     * Executes mvn scm:checkin to commit any changes within the repository.
     *
     * @return true for success; false otherwise
     */
    boolean commit();
}
