package com.github.manat.subrelease.invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This abstract class provides common methods those would be used any classes which to implement
 *
 * @{code Invoker}.
 * @see Invoker
 */
public abstract class AbstractInvoker implements Invoker {

    List<String> prepareArgs(String[] commands, String[] options) {
        List<String> args = new ArrayList<>();

        args.addAll(Arrays.asList(commands));
        if (options != null) {
            for (String opt : options) {
                if (opt != null && opt.trim().length() > 0) {
                    args.add(opt.startsWith("-D") ? opt : "-D" + opt);
                }
            }
        }

        return args;
    }
}
