package com.github.manat.maven.plugins.subrelease.reader;

import static java.nio.file.Files.readAllBytes;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.manat.maven.plugins.model.Artifact;

/**
 * Reads the output file using plain default Java lib.
 *
 */
public class DefaultOutputReader implements OutputReader {

	@Override
	public List<Artifact> getResolvedArtifacts(Path outputFile) {
		try {
			String output = new String(readAllBytes(outputFile), UTF_8.name());
			String[] rawArtifacts = output.split("[\\r\\n]+\\s");
			List<Artifact> artifacts = new ArrayList<>();

			for (int i = 1; i < rawArtifacts.length; i++) {
				artifacts.add(new Artifact(rawArtifacts[i]));
			}

			return artifacts;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return Collections.<Artifact> emptyList();
	}

	@Override
	public List<Artifact> getResolvedSnapshotArtifacts(Path outputFile) {
		List<Artifact> resolvedArtifacts = getResolvedArtifacts(outputFile);
		List<Artifact> snapshotArtifacts = new ArrayList<>();

		for (Artifact artifact : resolvedArtifacts) {
			if (artifact.getVersion().contains("-SNAPSHOT")) {
				snapshotArtifacts.add(artifact);
			}
		}

		return snapshotArtifacts;
	}

}
