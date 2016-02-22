package com.github.manat.subrelease.invoker;

import static java.nio.file.Paths.get;

public class GenericMavenInvokerTest extends InvokerTestBase {

    @Override
    protected InvokerProxy createInvoker() {
        return new GenericMavenInvoker(
                get("/Users/sid/Workspaces/java/maven/subrelease-maven-plugin/pom.xml"));
    }

}