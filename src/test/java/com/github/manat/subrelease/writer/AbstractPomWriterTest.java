package com.github.manat.subrelease.writer;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.delete;
import static java.nio.file.Paths.get;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractPomWriterTest {

    static final String BASE_PATH = "src/test/resources/";

    PomWriter pomWriter;

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
}
