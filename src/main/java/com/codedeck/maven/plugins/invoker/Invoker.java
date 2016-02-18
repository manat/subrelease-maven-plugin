package com.codedeck.maven.plugins.invoker;

import com.codedeck.maven.plugins.model.Artifact;

/**
 * Responsible in invoking maven command.
 *
 */
public interface Invoker {

	int unpackArtifact(Artifact artifact);

	int checkout(Artifact artifact, String connection);

	void release();
}
