package com.github.manat.subrelease.invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This abstract class provides common methods those would be used any classes which to implement
 * @{code Invoker}.
 *
 * @see Invoker
 */
public abstract class AbstractInvoker implements Invoker {

    List<String> prepareArgs(String[] commands, String[] options) {
        List<String> args = new ArrayList<>(commands.length + options.length);

        args.addAll(Arrays.asList(commands));
        for (String opt : options) {
            if (!opt.startsWith("-D")) {
                args.add("-D" + opt);
            }
        }

        return args;
    }
}
