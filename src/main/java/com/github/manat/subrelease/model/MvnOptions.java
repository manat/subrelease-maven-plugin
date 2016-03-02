package com.github.manat.subrelease.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides common manipulator methods.
 */
public class MvnOptions {

    public static final String SCM_COMMENT_PREFIX = "scmCommentPrefix";

    public static final String SCM_MESSAGE = "message";

    public static final String SCM_USERNAME = "username";

    public static final String SCM_PASSWORD = "password";

    private String[] options;

    public MvnOptions(String[] options) {
        if (options == null) {
            throw new IllegalArgumentException("Options cannot be null.");
        }

        this.options = options;
    }

    public void mergeScmMessageWithPrefix(String message) {
        String prefixValue = scanFor(SCM_COMMENT_PREFIX);
        if ((scanFor(SCM_MESSAGE) != null) || (prefixValue == null)) {
            return;
        }

        List<String> processedOptions = new ArrayList<>(options.length);
        for (String option : options) {
            String[] keyPair = option.split("=");
            if (isOptionEqual(SCM_COMMENT_PREFIX, keyPair[0])) {
                processedOptions.add("message=\"" + prefixValue + " " + message + "\"");
            } else {
                processedOptions.add(option);
            }
        }

        this.options = processedOptions.toArray(new String[processedOptions.size()]);
    }

    public String[] getValues() {
        return options;
    }

    public String scanFor(String argument) {
        if (options != null) {
            for (String option : options) {
                String[] keyPair = option.split("=");
                if (keyPair.length != 2) {
                    throw new IllegalArgumentException("Invalid MVN options.");
                }

                if (isOptionEqual(argument, keyPair[0])) {
                    return keyPair[1];
                }
            }
        }
        return null;
    }

    public boolean isOptionEqual(String option1, String option2) {
        return option1.replaceFirst("-D", "").equals(option2.replaceFirst("-D", ""));
    }
}
