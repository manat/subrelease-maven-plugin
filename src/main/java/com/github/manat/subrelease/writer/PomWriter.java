package com.github.manat.subrelease.writer;

import com.github.manat.subrelease.model.Dependency;

import java.util.List;

/**
 * An interface to manipulate pom information.
 */
public interface PomWriter {

    /**
     * Updates version of the given artifact by removing -SNAPSHOT off their version.
     *
     * @param dependencies the given SNAPSHOT dependencies
     * @return true if success; false otherwise
     */
    boolean updateSnapshotVersion(List<Dependency> dependencies);
}
