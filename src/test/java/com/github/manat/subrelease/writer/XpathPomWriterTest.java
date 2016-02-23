package com.github.manat.subrelease.writer;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.delete;
import static java.nio.file.Paths.get;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import com.github.manat.subrelease.model.Artifact;
import com.github.manat.subrelease.reader.PomReader;
import com.github.manat.subrelease.reader.XpathPomReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class XpathPomWriterTest {

    private static final String BASE_PATH = "src/test/resources/";

    private PomWriter pomWriter;

    @Before
    public void setUp() throws IOException {
        Path reader = get(BASE_PATH, "reader/pom/pom-with-multiple-snapshots.xml");
        Path writer = get(BASE_PATH, "writer/pom/pom-with-multiple-snapshots.xml");
        copy(reader, writer);
    }

    @After
    public void destroy() throws IOException {
        Path writer = get(BASE_PATH, "writer/pom/pom-with-multiple-snapshots.xml");
        delete(writer);
    }

    //    @Test
    public void testUpdateSnapshotVersion() throws Exception {
        pomWriter = new XpathPomWriter(
                get(BASE_PATH, "writer/pom/pom-with-multiple-snapshots.xml"));
        List<Artifact> snapshotDeps = getSnapshotDeps();
        assertThat(pomWriter.updateSnapshotVersion(snapshotDeps), equalTo(true));

        PomReader pomReader = new XpathPomReader(
                get(BASE_PATH, "writer/pom/pom-with-multiple-snapshots.xml"));
        assertThat(pomReader.getSnapshotDependencies(), is(empty()));

    }

    private List<Artifact> getSnapshotDeps() {
        Artifact dep1 = new Artifact();
        dep1.setGroupId("com.codedeck.sample");
        dep1.setArtifactId("dep-a");
        dep1.setVersion("0.0.1-SNAPSHOT");

        Artifact dep2 = new Artifact();
        dep2.setGroupId("com.codedeck.sample");
        dep2.setArtifactId("dep-b");
        dep2.setVersion("0.0.1-SNAPSHOT");

        List<Artifact> snapshotDeps = new ArrayList<>(2);
        snapshotDeps.add(dep1);
        snapshotDeps.add(dep2);

        return snapshotDeps;
    }
}