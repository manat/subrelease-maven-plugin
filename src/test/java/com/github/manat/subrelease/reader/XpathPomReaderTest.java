package com.github.manat.subrelease.reader;

import static java.nio.file.Paths.get;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.github.manat.subrelease.model.Dependency;
import org.junit.Test;

public class XpathPomReaderTest {

    private PomReader pomReader;

    private List<Dependency> expectedDeps;

    @Test
    public void thatGetDependencyListOfSingleSnapshot() {
        Dependency dep1 = new Dependency();
        dep1.setGroupId("com.codedeck.sample");
        dep1.setArtifactId("dep-a");
        dep1.setVersion("0.0.1-SNAPSHOT");

        expectedDeps = new ArrayList<>(1);
        expectedDeps.add(dep1);

        pomReader = new XpathPomReader(get("src/test/resources/reader/pom/pom-with-snapshot.xml"));

        List<Dependency> dependencies = pomReader.getSnapshotDependencies();

        assertThat(dependencies, equalTo(expectedDeps));
    }

    @Test
    public void thatGetDependencyListOfMultipleSnapshot() {
        Dependency dep1 = new Dependency();
        dep1.setGroupId("com.codedeck.sample");
        dep1.setArtifactId("dep-a");
        dep1.setVersion("0.0.1-SNAPSHOT");

        Dependency dep2 = new Dependency();
        dep2.setGroupId("com.codedeck.sample");
        dep2.setArtifactId("dep-b");
        dep2.setVersion("0.0.1-SNAPSHOT");

        expectedDeps = new ArrayList<>(2);
        expectedDeps.add(dep1);
        expectedDeps.add(dep2);

        pomReader = new XpathPomReader(
                get("src/test/resources/reader/pom/pom-with-multiple-snapshots.xml"));

        List<Dependency> dependencies = pomReader.getSnapshotDependencies();

        assertThat(dependencies, equalTo(expectedDeps));
    }

    @Test
    public void thatGetDependencyListOfNoSnapshot() {
        pomReader = new XpathPomReader(
                get("src/test/resources/reader/pom/pom-with-no-snapshot.xml"));

        List<Dependency> dependencies = pomReader.getSnapshotDependencies();

        assertThat(dependencies, is(empty()));
    }

    @Test
    public void thatGetScmConnectionWorks() {
        pomReader = new XpathPomReader(get("src/test/resources/reader/pom/pom-with-scm.xml"));

        String connection = pomReader.getScmConnection();

        assertThat(connection, is(equalTo("scm:git:https://github.com/apache/maven-release.git")));
    }
}