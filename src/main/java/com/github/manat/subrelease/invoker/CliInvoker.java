package com.github.manat.subrelease.invoker;

import java.nio.file.Path;

import org.apache.maven.cli.MavenCli;

import com.github.manat.subrelease.model.Artifact;

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
	public boolean resolveDependency(Path output) {
		System.out.println("projectDir=" + projectDir);
		System.out.println("output=" + output.toAbsolutePath());
		int result = cli.doMain(new String[] { "dependency:resolve", "-DoutputFile=" + output.toAbsolutePath() },
				projectDir, System.out, System.out);
		System.out.println("Clean!!!");
		System.out.println("result=" + result);

		return result == 0;
	}

	@Override
	public boolean unpackArtifact(Artifact artifact) {
		int result = cli.doMain(new String[] { "dependency:unpack", "-Dartifact=" + artifact }, projectDir, System.out,
				System.out);

		return result == 0;
	}

	@Override
	public boolean checkout(Artifact artifact, String connection) {
		int result = cli.doMain(new String[] { "scm:checkout", "-DconnectionType=developerConnection",
				"-DcheckoutDirectory=target/checkout/" + artifact.getArtifactId(),
				"-DdeveloperConnectionUrl=" + connection }, projectDir, System.out, System.out);

		return result == 0;
	}

	@Override
	public boolean release() {
		return release(projectDir);
	}

	@Override
	public boolean release(Path projectPath) {
		return release(projectPath.toString());
	}

	private boolean release(String projectPath) {
		int result = cli.doMain(new String[] { "--batch-mode", "release:clean", "release:prepare" }, projectPath,
				System.out, System.out);

		return result == 0;
	}

}
