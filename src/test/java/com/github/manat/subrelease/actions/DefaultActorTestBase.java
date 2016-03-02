package com.github.manat.subrelease.actions;

import static org.mockito.Mockito.verify;

import com.github.manat.subrelease.invoker.Invoker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;

/**
 * This class gives base test cases those should be verified through implementation classes.
 */
public abstract class DefaultActorTestBase {

    @Mock
    private Invoker invoker;

    private DefaultActor actor;

    abstract DefaultActor createActor(Invoker invoker);

    @Before
    public void setUp() {
        actor = createActor(invoker);
    }

    @Test
    public void testReleaseWithNoOptions() throws Exception {
        actor.release();

        verify(invoker)
                .execute(new String[] { "--batch-mode", "release:clean", "release:prepare" });
    }

    @Test
    public void testReleaseWithMultipleOptions() throws Exception {
        actor.release("dryRun=true", "scmCommentPrefix=[MIT-101]");

        verify(invoker).execute(new String[] { "--batch-mode", "release:clean", "release:prepare" },
                "dryRun=true", "scmCommentPrefix=[MIT-101]");
    }

    @Test
    public void testPerformWithNoOptions() throws Exception {
        actor.perform();

        verify(invoker).execute(new String[] { "--batch-mode", "release:perform" });
    }

    @Test
    public void testPerformWithMultipleOptions() throws Exception {
        actor.perform("dryRun=true", "releaseProfiles=test,moreTest");

        verify(invoker).execute(new String[] { "--batch-mode", "release:perform" }, "dryRun=true",
                "releaseProfiles=test,moreTest");
    }

    @Test
    public void testCommitWithNoOptions() throws Exception {
        actor.commit();

        verify(invoker).execute(new String[] { "scm:checkin" },
                "message=\"[subrelease-maven-plugin] Resolved any SNAPSHOT dependencies.\"");
    }

    @Test
    public void testCommitWithScmCommentPrefix() throws Exception {
        actor.commit("scmCommentPrefix=[MIT-101]", "includes=*.MD,*.java");

        verify(invoker).execute(new String[] { "scm:checkin" },
                "message=\"[MIT-101] [subrelease-maven-plugin] Resolved any SNAPSHOT dependencies.\"",
                "includes=*.MD,*.java");
    }
}
