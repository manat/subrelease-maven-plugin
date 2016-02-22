package com.github.manat.subrelease.invoker;

import static java.nio.file.Paths.get;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.nio.file.Path;
import java.util.List;

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
        invoker.setMavenHome(get("/usr/local/Cellar/maven/3.3.9/libexec").toFile());
        InvocationResult result;
        try {
            result = invoker.execute(req);
        } catch (MavenInvocationException e) {
            e.printStackTrace();
            return false;
        }

        return result.getExitCode() == 0;
    }
}
