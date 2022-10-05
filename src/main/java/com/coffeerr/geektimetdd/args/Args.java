package com.coffeerr.geektimetdd.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: Desmond
 * @time: 2022/10/5 10:33 AM
 */

public class Args {
    public static <T> T parse(Class<T> optionClass, String... args) {

        try {
            Constructor<?> constructor = optionClass.getDeclaredConstructors()[0];
            List<String> arguments = Arrays.asList(args);
            Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOption(it, arguments)).toArray();
            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(Parameter parameter, List<String> arguments) {
        Object value = null;
        Option option = parameter.getAnnotation(Option.class);
        if (parameter.getType() == boolean.class) {
            value = arguments.contains("-" + option.value());
        }
        if (parameter.getType() == int.class) {
            int index = arguments.indexOf("-" + option.value());
            value = Integer.parseInt(arguments.get(index + 1));
        }
        if (parameter.getType() == String.class) {
            int index = arguments.indexOf("-" + option.value());
            value = String.valueOf(arguments.get(index + 1));
        }
        return value;
    }
}
