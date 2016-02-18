package com.github.manat.maven.plugins.subrelease.invoker;

import java.nio.file.Path;

import org.apache.maven.cli.MavenCli;

import com.github.manat.maven.plugins.subrelease.model.Artifact;

/**
 * Invoking maven command using Maven Embedder.
 */
public class CliInvoker implements Invoker {

	private String projectDir;

	private MavenCli cli;

	public CliInvoker(String projectDir) {
		this.projectDir = projectDir;
		this.cli = new MavenCli();

		System.setProperty("maven.multiModuleProjectDirectory", "$M2_HOME");
	}

	@Override
	public int resolveDependency(Artifact artifact, Path output) {
		int result = cli.doMain(new String[] { "dependency:resolve", "-DoutputFile=" + output.toAbsolutePath() },
				projectDir, System.out, System.out);

		return result;
	}

	@Override
	public int unpackArtifact(Artifact artifact) {
		int result = cli.doMain(new String[] { "dependency:unpack", "-Dartifact=" + artifact }, projectDir, System.out,
				System.out);

		return result;
	}

	@Override
	public int checkout(Artifact artifact, String connection) {
		int result = cli.doMain(new String[] { "scm:checkout", "-DconnectionType=developerConnection",
				"-DcheckoutDirectory=target/checkout/" + artifact.getArtifactId(),
				"-DdeveloperConnectionUrl=" + connection }, projectDir, System.out, System.out);

		return result;
	}

	@Override
	public int release() {
		int result = cli.doMain(new String[] { "--batch-mode", "release:prepare", "-DdryRun=true" }, projectDir,
				System.out, System.out);

		return result;
	}

}
