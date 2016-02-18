package com.github.manat.maven.plugins.subrelease.reader;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.manat.maven.plugins.subrelease.model.Artifact;
import com.github.manat.maven.plugins.subrelease.reader.DefaultOutputReader;
import com.github.manat.maven.plugins.subrelease.reader.OutputReader;

public class DefaultOutputReaderTest {

	private static final String BASE_PATH = "src/test/resources/reader/output/";

	private OutputReader reader;

	@Before
	public void setUp() {
		reader = new DefaultOutputReader();
	}

	@Test
	public void thatGetResolvedArtifactsWorksWithOutput1() {
		Path output = Paths.get(BASE_PATH, "output1.txt");
		List<Artifact> expectedArtifacts = new ArrayList<>(3);
		expectedArtifacts.add(new Artifact("com.codedeck.sample", "dep-a", "0.0.1-SNAPSHOT"));
		expectedArtifacts.add(new Artifact("org.codehaus.plexus", "plexus-component-annotations", "1.5.4"));
		expectedArtifacts.add(new Artifact("org.apache.maven", "maven-plugin-api", "3.0"));

		List<Artifact> artifacts = reader.getResolvedArtifacts(output);

		assertThat(artifacts, equalTo(expectedArtifacts));
	}

	@Test
	public void thatGetResolvedSnapshotArtifactsWorksWithOutput1() {
		Path output = Paths.get(BASE_PATH, "output1.txt");
		List<Artifact> expectedArtifacts = new ArrayList<>(1);
		expectedArtifacts.add(new Artifact("com.codedeck.sample", "dep-a", "0.0.1-SNAPSHOT"));

		List<Artifact> artifacts = reader.getResolvedSnapshotArtifacts(output);

		assertThat(artifacts, equalTo(expectedArtifacts));
	}
}
