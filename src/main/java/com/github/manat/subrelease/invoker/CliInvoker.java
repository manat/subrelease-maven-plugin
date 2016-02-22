package com.github.manat.subrelease.invoker;

import org.apache.maven.cli.MavenCli;

import java.util.List;

/**
 * An implementation of @{Invoker}, using maven-embedder (aka maven cli).
 */
public class CliInvoker extends AbstractInvoker {

    private String projectDir;

    private MavenCli cli;

    public CliInvoker(String projectDir) {
        this.projectDir = projectDir;
        this.cli = new MavenCli();

        System.setProperty("maven.multiModuleProjectDirectory", "$M2_HOME");
    }

    @Override
    public boolean execute(String[] commands, String... options) {
        List<String> args = prepareArgs(commands, options);
        int result = cli.doMain(args.toArray(new String[0]), projectDir, System.out, System.err);

        return result == 0;
    }
}
