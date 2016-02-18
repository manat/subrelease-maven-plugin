package com.codedeck.maven.plugins.invoker;

import java.nio.file.Path;

import org.apache.maven.cli.MavenCli;

import com.codedeck.maven.plugins.model.Artifact;

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
		// int result = cli.doMain(new String[] { "release:prepare" },
		// "C:/dev/maven/dep-a",
		// System.out, System.out);

		// int result = cli.doMain(new String[] {
		// "dependency:copy-dependencies", "-DcopyPom=true" },
		// "C:/dev/maven/dep-a",
		// System.out, System.out);

		// int result = cli.doMain(new String[] {"clean", "dependency:get",
		// "-DgroupId=org.slf4j", "-DartifactId=slf4j-simple",
		// "-Dtransitive=false", "-Dversion=1.7.5" }, "C:/dev/maven/dep-a",
		// System.out, System.out);

		int result = cli.doMain(
				new String[] { "clean", "dependency:unpack", "-Dartifact=org.slf4j:slf4j-simple:1.7.5" },
				"C:/dev/maven/dep-a", System.out, System.out);

		System.out.println("result=" + result);

		return result;
	}

}
