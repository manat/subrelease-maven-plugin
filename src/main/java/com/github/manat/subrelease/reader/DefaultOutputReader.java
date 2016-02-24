package com.github.manat.subrelease.reader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;

import com.github.manat.subrelease.model.Dependency;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reads the output file using plain default Java lib.
 */
public class DefaultOutputReader implements OutputReader {

    @Override
    public List<Dependency> getResolvedArtifacts(Path outputFile) {
        try {
            String output = new String(readAllBytes(outputFile), UTF_8.name());
            String[] rawArtifacts = output.trim().split("[\\r\\n]+\\s");
            List<Dependency> dependencies = new ArrayList<>();

            for (int i = 1; i < rawArtifacts.length; i++) {
                dependencies.add(new Dependency(rawArtifacts[i]));
            }

            return dependencies;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.<Dependency>emptyList();
    }

    @Override
    public List<Dependency> getResolvedSnapshotArtifacts(Path outputFile) {
        List<Dependency> resolvedDependencies = getResolvedArtifacts(outputFile);
        List<Dependency> snapshotDependencies = new ArrayList<>();

        for (Dependency dependency : resolvedDependencies) {
            if (dependency.getVersion().contains("-SNAPSHOT")) {
                snapshotDependencies.add(dependency);
            }
        }

        return snapshotDependencies;
    }

}
