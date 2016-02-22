package com.github.manat.subrelease.invoker;

public class GenericCliInvokerTest extends InvokerTestBase {

    @Override
    protected InvokerProxy createInvoker() {
        return new GenericCliInvoker(".");
    }
}