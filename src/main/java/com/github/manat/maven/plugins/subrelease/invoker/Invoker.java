package com.github.manat.maven.plugins.subrelease.invoker;

import java.nio.file.Path;

import com.github.manat.maven.plugins.subrelease.model.Artifact;

/**
 * Responsible in invoking maven command.
 *
 */
public interface Invoker {

	/**
	 * Executes mvn dependency:resolve, then the result will be stored at the
	 * given output path.
	 * 
	 * @param artifact
	 *            an artifact to find dependencies
	 * @param output
	 *            resolved dependencies are stored here
	 * @return true for success; false otherwise
	 */
	boolean resolveDependency(Artifact artifact, Path output);

	/**
	 * Executes mvn dependency:unpack of the given artifact value.
	 * 
	 * @param artifact
	 *            an Artifact to be unpack
	 * @return true for success; false otherwise
	 */
	boolean unpackArtifact(Artifact artifact);

	/**
	 * Executes mvn scm:checkout of the given artifact, using provided
	 * connection.
	 * 
	 * @param artifact
	 *            an Artifact to be checkout
	 * @param connection
	 *            an information indicates scm type and connection url
	 * @return true for success; false otherwise
	 */
	boolean checkout(Artifact artifact, String connection);

	/**
	 * Executes mvn release:prepare
	 * 
	 * @return true for success; false otherwise
	 */
	boolean release();

	/**
	 * Executes mvn release:prepare for the given projectPath.
	 * 
	 * @param projectPath
	 *            location of a maven project
	 * @return true for success; false otherwise
	 */
	boolean release(Path projectPath);
}
