package com.github.manat.maven.plugins.subrelease.invoker;

import static java.nio.file.Files.readAllBytes;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.github.manat.maven.plugins.subrelease.invoker.CliInvoker;
import com.github.manat.maven.plugins.subrelease.model.Artifact;

public class CliInvokerTest {

	private static int SUCCESS = 0;

	private CliInvoker invoker;

	@Rule
	public TemporaryFolder outputFolder = new TemporaryFolder();

	@Before
	public void setUp() {
		invoker = new CliInvoker(".");
	}

	@Test
	public void thatResolveDependencyWorks() throws IOException {
		Artifact artifact = new Artifact("org.slf4j", "slf4j-simple", "1.7.5");
		Path output = outputFolder.newFile("dep.txt").toPath();

		invoker.resolveDependency(artifact, output);

		assertThat(new String(readAllBytes(output)), containsString("The following files have been resolved:"));
	}

	@Test
	public void thatUnpackCommandWorks() {
		Artifact artifact = new Artifact("org.slf4j", "slf4j-simple", "1.7.5");

		assertThat(invoker.unpackArtifact(artifact), equalTo(SUCCESS));
	}

	@Test
	public void thatUnpackCommandFailsWhenArtifactIsNotFound() {
		Artifact artifact = new Artifact();
		assertThat(invoker.unpackArtifact(artifact), not(equalTo(SUCCESS)));

		artifact = new Artifact("org0.slf4j", "slf4j-simple", "1.7.5");
		assertThat(invoker.unpackArtifact(artifact), not(equalTo(SUCCESS)));
	}

	@Test
	public void thatScmCheckoutWorks() {
		Artifact artifact = new Artifact("com.codedeck.sample", "dep-a", "0.0.1-SNAPSHOT");
		String connection = "scm:git:file://I:/ODC_BKK_Repository/ta2000/Architect/Lab/Maven/dep-a";

		assertThat(invoker.checkout(artifact, connection), equalTo(SUCCESS));
	}

	@Test
	public void thatScmCheckoutMultipleConnectionWorks() {
		Artifact artifact = new Artifact("com.codedeck.sample", "dep-a", "0.0.1-SNAPSHOT");
		String connection = "scm:git:file://I:/ODC_BKK_Repository/ta2000/Architect/Lab/Maven/dep-a";
		assertThat(invoker.checkout(artifact, connection), equalTo(SUCCESS));

		Artifact artifact2 = new Artifact("com.codedeck.sample", "hello-maven-plugin", "0.0.1-SNAPSHOT");
		String connection2 = "scm:git:file://I:/ODC_BKK_Repository/ta2000/Architect/Lab/Maven/hello-maven-plugin";
		assertThat(invoker.checkout(artifact2, connection2), equalTo(SUCCESS));
	}
}
