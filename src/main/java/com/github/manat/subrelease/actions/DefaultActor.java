package com.github.manat.subrelease.actions;

import com.github.manat.subrelease.invoker.Invoker;
import com.github.manat.subrelease.model.Artifact;

import java.nio.file.Path;

/**
 * Default implementor to perform a series of subrelease.
 */
public class DefaultActor implements Subrelease {

    private Invoker invoker;

    public DefaultActor(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public boolean resolveDependency(Path output) {
        return invoker.execute(new String[] { "dependency:resolve" },
                "outputFile=" + output.toAbsolutePath());
    }

    @Override
    public boolean unpackArtifact(Artifact artifact) {
        return invoker.execute(new String[] { "dependency:unpack" }, "artifact=" + artifact,
                "outputDirectory=target/dependency/" + artifact.getArtifactId());
    }

    @Override
    public boolean checkout(Artifact artifact, String connection) {
        return invoker
                .execute(new String[] { "scm:checkout" }, "connectionType=developerConnection",
                        "checkoutDirectory=target/checkout/" + artifact.getArtifactId(),
                        "developerConnectionUrl=" + connection);
    }

    @Override
    public boolean release() {
        return invoker.execute(new String[] { "--batch-mode", "release:clean", "release:prepare" });
    }

    @Override
    public boolean commit() {
        return invoker.execute(new String[] { "scm:checkin" },
                "message=[subrelease-maven-plugin] Resolved any SNAPSHOT dependencies.");
    }
}
