package com.github.manat.subrelease.model;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ArtifactTest {

	@Test
	public void thatArtifactCanBeCreatedFromRawDependency() {
		String dep = "  com.codedeck.sample:dep-a:jar:0.0.1-SNAPSHOT:compile";
		Artifact expected = new Artifact("com.codedeck.sample", "dep-a", "0.0.1-SNAPSHOT");

		Artifact actual = new Artifact(dep);

		assertThat(actual, equalTo(expected));
	}
}
