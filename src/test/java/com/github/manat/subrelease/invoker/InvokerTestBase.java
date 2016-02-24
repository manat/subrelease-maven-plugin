package com.github.manat.subrelease.invoker;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * This class gives base test cases those should be verified through implementation classes.
 */
public abstract class InvokerTestBase {

    private Invoker invoker;

    abstract Invoker createInvoker();

    @Before
    public void setUp() {
        invoker = createInvoker();
    }

    @Test
    public void testExecuteCompile() throws Exception {
        boolean result = invoker.execute(new String[] { "compile" });

        assertThat(result, equalTo(true));
    }

    @Test
    public void testExecuteDependencyResolve() throws Exception {
        boolean result = invoker
                .execute(new String[] { "dependency:resolve" }, "outputFile=resolvedDep.txt");

        assertThat(result, equalTo(true));
    }

    @Test
    public void testExecuteReleasePrepareWithOptions() throws Exception {
        boolean result = invoker
                .execute(new String[] { "dependency:tree" }, "scope=compile", "verbose=true");

        assertThat(result, equalTo(true));
    }

    @Test
    public void testExecuteDependencyGet() {
        boolean result = invoker
                .execute(new String[] { "dependency:get" }, "artifact=junit:junit:4.12");

        assertThat(result, equalTo(true));
    }

    @Test
    public void testExecuteDependencyGetWhichDoesNotExist() {
        boolean result = invoker
                .execute(new String[] { "dependency:get" }, "artifact=junit:junit:90000");

        assertThat(result, equalTo(false));
    }
}
