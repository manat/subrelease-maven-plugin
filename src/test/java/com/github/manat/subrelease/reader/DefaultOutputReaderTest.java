package com.github.manat.subrelease.reader;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import com.github.manat.subrelease.model.Dependency;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        List<Dependency> expectedDependencies = new ArrayList<>(3);
        expectedDependencies.add(new Dependency("com.codedeck.sample", "dep-a", "0.0.1-SNAPSHOT"));
        expectedDependencies
                .add(new Dependency("org.codehaus.plexus", "plexus-component-annotations", "1.5.4"));
        expectedDependencies.add(new Dependency("org.apache.maven", "maven-plugin-api", "3.0"));

        List<Dependency> dependencies = reader.getResolvedArtifacts(output);

        assertThat(dependencies, equalTo(expectedDependencies));
    }

    @Test
    public void thatGetResolvedSnapshotArtifactsWorksWithOutput1() {
        Path output = Paths.get(BASE_PATH, "output1.txt");
        List<Dependency> expectedDependencies = new ArrayList<>(1);
        expectedDependencies.add(new Dependency("com.codedeck.sample", "dep-a", "0.0.1-SNAPSHOT"));

        List<Dependency> dependencies = reader.getResolvedSnapshotArtifacts(output);

        assertThat(dependencies, equalTo(expectedDependencies));
    }
}
