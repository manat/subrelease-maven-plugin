package com.codedeck.maven.plugins.invoker;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.codedeck.maven.plugins.model.Artifact;

public class CliInvokerTest {

	private static int SUCCESS = 0;

	private CliInvoker invoker;

	@Before
	public void setUp() {
		invoker = new CliInvoker("C:/dev/maven/dep-a");
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
