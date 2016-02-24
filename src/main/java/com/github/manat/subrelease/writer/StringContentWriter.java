package com.github.manat.subrelease.writer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;

import com.github.manat.subrelease.model.Artifact;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Manipulates pom content by scanning file content.
 */
public class StringContentWriter implements PomWriter {

    private Path pomPath;

    public StringContentWriter(Path pomPath) {
        this.pomPath = pomPath;
    }

    @Override
    public boolean updateSnapshotVersion(List<Artifact> artifacts) {
        try {
            String content = new String(readAllBytes(pomPath), UTF_8);
            content = content.replaceFirst("-SNAPSHOT", "-SNAP_SH_OT");
            content = content.replaceAll("-SNAPSHOT", "");
            content = content.replaceFirst("-SNAP_SH_OT", "-SNAPSHOT");
            write(pomPath, content.getBytes(UTF_8));

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
