package com.codedeck.maven.plugins.subrelease.reader;

import java.util.List;

import com.codedeck.maven.plugins.model.Artifact;

/**
 * An interface to read Maven pom information.
 * 
 */
public interface PomReader {

	/**
	 * Retrieves a list of dependency those are snapshots.
	 * 
	 * @return a list of dependency those are snapshots; empty collection
	 *         otherwise
	 */
	List<Artifact> getSnapshotDependencies();

	/**
	 * Retrieves a scm connection from pom.
	 * 
	 * @return a scm connection indicates scm type, and connection information;
	 *         {@code null} otherwise
	 */
	String getScmConnection();
}
