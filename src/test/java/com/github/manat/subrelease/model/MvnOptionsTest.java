package com.github.manat.subrelease.model;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

public class MvnOptionsTest {

    private MvnOptions mvnOptions;

    @Test
    public void thatGetValuesWorks() {
        mvnOptions = new MvnOptions(new String[] { "scmCommentPrefix=[MIT-101]", "-DdryRun=true" });

        mvnOptions.mergeScmMessageWithPrefix("Test Message.");

        assertThat(mvnOptions.getValues(),
                equalTo(new String[] { "message=\"[MIT-101] Test Message.\"", "-DdryRun=true" }));
    }

    @Test
    public void thatMergeScmMessageWithPrefixWorks() throws Exception {
        mvnOptions = new MvnOptions(new String[] { "scmCommentPrefix=[MIT-101]" });

        mvnOptions.mergeScmMessageWithPrefix("Test Message.");

        assertThat(mvnOptions.getValues(),
                equalTo(new String[] { "message=\"[MIT-101] Test Message.\"" }));
    }

    @Test
    public void thatMergeScmMessageWithPrefixDoesNothingWhenMessageIsPresent() throws Exception {
        mvnOptions = new MvnOptions(
                new String[] { "message=\"Einstein was real.\"", "scmCommentPrefix=[MIT-101]" });

        mvnOptions.mergeScmMessageWithPrefix("Test Message.");

        assertThat(mvnOptions.getValues(), equalTo(new String[] { "message=\"Einstein was real.\"",
                "scmCommentPrefix=[MIT-101]" }));
    }

    @Test
    public void thatIsEqualWorks() throws Exception {
        mvnOptions = new MvnOptions(new String[] {});

        assertThat(mvnOptions.isOptionEqual("-DscmCommentPrefix", "scmCommentPrefix"),
                equalTo(true));
    }

    @Test
    public void testScanFor() throws Exception {
        mvnOptions = new MvnOptions(
                new String[] { "-Dmessage=\"Einstein was real.\"", "scmCommentPrefix=[MIT-101]" });

        assertThat(mvnOptions.scanFor("message"), equalTo("\"Einstein was real.\""));
        assertThat(mvnOptions.scanFor("scmCommentPrefix"), equalTo("[MIT-101]"));
    }
}