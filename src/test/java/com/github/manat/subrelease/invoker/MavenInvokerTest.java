package com.github.manat.subrelease.invoker;

import static java.nio.file.Paths.get;

public class MavenInvokerTest extends InvokerTestBase {

    @Override
    protected Invoker createInvoker() {
        return new MavenInvoker(get("pom.xml"));
    }

}