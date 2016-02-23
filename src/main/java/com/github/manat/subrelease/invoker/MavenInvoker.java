package com.github.manat.subrelease.invoker;

import java.nio.file.Path;
import java.util.List;

import org.apache.maven.shared.invoker.*;
import org.apache.maven.shared.invoker.Invoker;

/**
 * An implementation of @{Invoker}, using maven-invoker-plugin.
 */
public class MavenInvoker extends AbstractInvoker {

    private Path pomPath;

    public MavenInvoker(Path pomPath) {
        this.pomPath = pomPath;
    }

    @Override
    public boolean execute(String[] commands, String... options) {
        List<String> goals = prepareArgs(commands, options);
        InvocationRequest req = new DefaultInvocationRequest();
        req.setPomFile(pomPath.toFile());
        req.setGoals(goals);

        Invoker invoker = new DefaultInvoker();
        InvocationResult result;
        try {
            result = invoker.execute(req);
        } catch (MavenInvocationException e) {
            e.printStackTrace();
            return false;
        }

        if (result.getExitCode() == 0) {
            return true;
        } else {
            System.err.println(
                    "MavenInvoker.execute = " + result.getExecutionException().getMessage());
            return false;
        }

    }
}
