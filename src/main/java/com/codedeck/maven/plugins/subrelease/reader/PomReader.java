package com.codedeck.maven.plugins.subrelease.reader;

import java.util.List;

import com.codedeck.maven.plugins.model.Artifact;

/**
 * An interface to read Maven pom information.
 * 
 */
public interface PomReader {

	List<Artifact> getSnapshotDependencies();

	String getScmConnection();
}
