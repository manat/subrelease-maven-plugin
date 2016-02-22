package com.github.manat.subrelease.invoker;

import com.github.manat.subrelease.model.Artifact;

import java.nio.file.Path;

/**
 * Default implementation of invoker.
 */
public class DefaultInvoker implements Invoker {

    @Override
    public boolean resolveDependency(Path output) {
        return false;
    }

    @Override
    public boolean unpackArtifact(Artifact artifact) {
        return false;
    }

    @Override
    public boolean checkout(Artifact artifact, String connection) {
        return false;
    }

    @Override
    public boolean release() {
        return false;
    }

    @Override
    public boolean release(Path projectPath) {
        return false;
    }
}
