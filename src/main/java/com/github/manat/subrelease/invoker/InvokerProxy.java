package com.github.manat.subrelease.invoker;

/**
 * A proxy for executing maven command.
 */
public interface InvokerProxy {

    boolean execute(String[] commands, String... options);
}
