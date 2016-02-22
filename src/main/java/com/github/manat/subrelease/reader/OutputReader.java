package com.github.manat.subrelease.reader;

import com.github.manat.subrelease.model.Artifact;

import java.nio.file.Path;
import java.util.List;

/**
 * An interface to read result of output generated from maven command.
 */
public interface OutputReader {

    /**
     * Retrieves a list of resolved artifacts from the given output file.
     *
     * @param outputFile file content should be generated by mvn dependency:resolve command, with
     *                   outputFile option.
     * @return a list of Artifact corresponding to the given outputFile; empty collection otherwise
     * @see https://maven.apache.org/plugins/maven-dependency-plugin/resolve- mojo.html
     */
    List<Artifact> getResolvedArtifacts(Path outputFile);

    /**
     * Retrieves a list of resolved artifacts, those who are snapshot, from the given output file.
     *
     * @param outputFile file content should be generated by mvn dependency:resolve command, with
     *                   outputFile option.
     * @return a list of Artifact corresponding to the given outputFile; empty collection otherwise
     * @see https://maven.apache.org/plugins/maven-dependency-plugin/resolve- mojo.html
     */
    List<Artifact> getResolvedSnapshotArtifacts(Path outputFile);
}
