package com.github.manat.subrelease.actions;

import java.nio.file.Path;

import com.github.manat.subrelease.invoker.Invoker;
import com.github.manat.subrelease.model.Dependency;
import com.github.manat.subrelease.model.MvnOptions;

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
        return invoker.execute(new String[] { "dependency:get" },
                "artifact=" + dependency.toReleaseString());
    }

    @Override
    public boolean release(String... options) {
        return invoker.execute(new String[] { "--batch-mode", "release:clean", "release:prepare" },
                options);
    }

    @Override
    public boolean perform(String... options) {
        return invoker.execute(new String[] { "--batch-mode", "release:perform" }, options);
    }

    @Override
    public boolean commit(String... options) {
        String message = "[subrelease-maven-plugin] Resolved any SNAPSHOT dependencies.";
        MvnOptions mvnOptions;

        if (options == null || options.length == 0) {
            mvnOptions = new MvnOptions(new String[] { "message=\"" + message + "\"" });
        } else {
            mvnOptions = new MvnOptions(options);
            mvnOptions.mergeScmMessageWithPrefix(message);
        }

        return invoker.execute(new String[] { "scm:checkin" }, mvnOptions.getValues());
    }
}
