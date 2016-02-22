package com.github.manat.subrelease.writer;

import com.github.manat.subrelease.model.Artifact;

import java.util.List;

/**
 * An interface to manipulate pom information.
 */
public interface PomWriter {

    /**
     * Updates version of the given artifact by removing -SNAPSHOT off their version.
     *
     * @param artifacts the given SNAPSHOT dependencies
     * @return true if success; false otherwise
     */
    boolean updateSnapshotVersion(List<Artifact> artifacts);
}
