package com.github.manat.subrelease.model;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DependencyTest {

    @Test
    public void thatArtifactCanBeCreatedFromRawDependency() {
        String dep = "  com.codedeck.sample:dep-a:jar:0.0.1-SNAPSHOT:compile";
        Dependency expected = new Dependency("com.codedeck.sample", "dep-a", "0.0.1-SNAPSHOT");

        Dependency actual = new Dependency(dep);

        assertThat(actual, equalTo(expected));
    }
}
