package com.github.manat.subrelease.writer;

import static java.nio.file.Paths.get;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import com.github.manat.subrelease.model.Dependency;
import com.github.manat.subrelease.reader.PomReader;
import com.github.manat.subrelease.reader.XpathPomReader;
import org.junit.Test;

import java.util.Collections;

public class StringContentWriterTest extends AbstractPomWriterTest {

    @Test
    public void testUpdateSnapshotVersion() throws Exception {
        pomWriter = new StringContentWriter(get(BASE_PATH, "writer/pom/pom-with-multiple-snapshots.xml"));
        assertThat(pomWriter.updateSnapshotVersion(Collections.<Dependency>emptyList()),
                equalTo(true));

        PomReader pomReader = new XpathPomReader(
                get(BASE_PATH, "writer/pom/pom-with-multiple-snapshots.xml"));
        assertThat(pomReader.getSnapshotDependencies(), is(empty()));
    }
}