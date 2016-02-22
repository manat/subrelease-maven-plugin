package com.github.manat.subrelease.actions;

import com.github.manat.subrelease.model.Artifact;

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
     * Executes mvn dependency:unpack of the given artifact value.
     *
     * @param artifact an Artifact to be unpack
     * @return true for success; false otherwise
     */
    boolean unpackArtifact(Artifact artifact);

    /**
     * Executes mvn scm:checkout of the given artifact, using provided connection.
     *
     * @param artifact   an Artifact to be checkout
     * @param connection an information indicates scm type and connection url
     * @return true for success; false otherwise
     */
    boolean checkout(Artifact artifact, String connection);

    /**
     * Executes mvn release:prepare
     *
     * @return true for success; false otherwise
     */
    boolean release();
}
