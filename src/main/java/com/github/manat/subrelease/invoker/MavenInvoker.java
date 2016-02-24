package com.github.manat.subrelease.invoker;

import java.nio.file.Path;
import java.util.List;

import org.apache.maven.shared.invoker.*;
import org.apache.maven.shared.invoker.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of @{Invoker}, using maven-invoker-plugin.
 */
public class MavenInvoker extends AbstractInvoker {

    private final Logger logger = LoggerFactory.getLogger(MavenInvoker.class);

    private Path pomPath;

    public MavenInvoker(Path pomPath) {
        this.pomPath = pomPath;
    }

    @Override
    public boolean execute(String[] commands, String... options) {
        assert commands != null;

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

        if (result.getExecutionException() != null) {
            logger.error("An error is found during maven execution:\n{}",
                    result.getExecutionException().getMessage());
        }

        return result.getExitCode() == 0;
    }
}
