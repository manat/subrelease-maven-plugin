package com.github.manat.subrelease.actions;

import com.github.manat.subrelease.invoker.Invoker;
import com.github.manat.subrelease.model.Dependency;

import java.nio.file.Path;

/**
 * Default implementor to perform a series of subrelease.
 */
public class DefaultActor implements Subrelease {

    public static final String BASE_WORKSPACE = "target/dependency/workspace/";

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
    public boolean unpackArtifact(Dependency dependency) {
        return invoker.execute(new String[] { "dependency:unpack" }, "artifact=" + dependency,
                "outputDirectory=" + BASE_WORKSPACE + dependency.getArtifactId());
    }

    @Override
    public boolean checkout(Dependency dependency, String connection) {
        return invoker
                .execute(new String[] { "scm:checkout" }, "connectionType=developerConnection",
                        "checkoutDirectory=" + BASE_WORKSPACE + dependency.getArtifactId(),
                        "developerConnectionUrl=" + connection);
    }

    @Override
    public boolean exists(Dependency dependency) {
        return invoker.execute(new String[] { "dependency:get" }, "artifact=" + dependency);
    }

    @Override
    public boolean releaseExists(Dependency dependency) {
        return invoker.execute(new String[] { "dependency:get" }, "artifact=" + dependency.toReleaseString());
    }

    @Override
    public boolean release() {
        return invoker.execute(new String[] { "--batch-mode", "release:clean", "release:prepare" });
    }

    @Override
    public boolean release(String scmCommentPrefix) {
        return invoker.execute(new String[] { "--batch-mode", "release:clean", "release:prepare" },
                "scmCommentPrefix=\"" + scmCommentPrefix + " \"");
    }

    @Override
    public boolean perform(String... options) {
        return invoker.execute(new String[] { "--batch-mode", "release:perform" }, options);
    }

    @Override
    public boolean commit() {
        return invoker.execute(new String[] { "scm:checkin" },
                "message=\"[subrelease-maven-plugin] Resolved any SNAPSHOT dependencies.\"");
    }

    @Override
    public boolean commit(String scmCommentPrefix) {
        return invoker.execute(new String[] { "scm:checkin" }, "message=\"" + scmCommentPrefix
                + "\" \"[subrelease-maven-plugin] Resolved any SNAPSHOT dependencies.\"");
    }
}
