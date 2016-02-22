package com.github.manat.subrelease.invoker;

/**
 * A proxy for executing maven command.
 */
public interface Invoker {

    boolean execute(String[] commands, String... options);
}
