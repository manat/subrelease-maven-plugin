package com.github.manat.maven.plugins.subrelease.reader;

import static java.nio.file.Paths.get;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.manat.maven.plugins.subrelease.model.Artifact;
import com.github.manat.maven.plugins.subrelease.reader.PomReader;
import com.github.manat.maven.plugins.subrelease.reader.XpathPomReader;

public class XpathPomReaderTest {

	private PomReader pomReader;

	private List<Artifact> expectedDeps;

	@Test
	public void thatGetDependencyListOfSingleSnapshot() {
		Artifact dep1 = new Artifact();
		dep1.setGroupId("com.codedeck.sample");
		dep1.setArtifactId("dep-a");
		dep1.setVersion("0.0.1-SNAPSHOT");

		expectedDeps = new ArrayList<>(1);
		expectedDeps.add(dep1);

		pomReader = new XpathPomReader(get("src/test/resources/reader/pom/pom-with-snapshot.xml"));

		List<Artifact> dependencies = pomReader.getSnapshotDependencies();

		assertThat(dependencies, equalTo(expectedDeps));
	}

	@Test
	public void thatGetDependencyListOfMultipleSnapshot() {
		Artifact dep1 = new Artifact();
		dep1.setGroupId("com.codedeck.sample");
		dep1.setArtifactId("dep-a");
		dep1.setVersion("0.0.1-SNAPSHOT");

		Artifact dep2 = new Artifact();
		dep2.setGroupId("com.codedeck.sample");
		dep2.setArtifactId("dep-b");
		dep2.setVersion("0.0.1-SNAPSHOT");

		expectedDeps = new ArrayList<>(2);
		expectedDeps.add(dep1);
		expectedDeps.add(dep2);

		pomReader = new XpathPomReader(get("src/test/resources/reader/pom/pom-with-multiple-snapshots.xml"));

		List<Artifact> dependencies = pomReader.getSnapshotDependencies();

		assertThat(dependencies, equalTo(expectedDeps));
	}

	@Test
	public void thatGetDependencyListOfNoSnapshot() {
		pomReader = new XpathPomReader(get("src/test/resources/reader/pom/pom-with-no-snapshot.xml"));

		List<Artifact> dependencies = pomReader.getSnapshotDependencies();

		assertThat(dependencies, is(nullValue()));
	}

	@Test
	public void thatGetScmConnectionWorks() {
		pomReader = new XpathPomReader(get("src/test/resources/reader/pom/pom-with-scm.xml"));

		String connection = pomReader.getScmConnection();

		assertThat(connection, is(equalTo("scm:git:https://github.com/apache/maven-release.git")));
	}
}