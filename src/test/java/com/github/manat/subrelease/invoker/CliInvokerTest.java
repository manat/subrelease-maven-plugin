package com.github.manat.subrelease.invoker;

public class CliInvokerTest extends InvokerTestBase {

    @Override
    protected Invoker createInvoker() {
        return new CliInvoker(".");
    }
}