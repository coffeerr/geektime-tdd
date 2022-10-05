package com.coffeerr.geektimetdd.args;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description:
 * @author: Desmond
 * @time: 2022/10/5 10:17 AM
 */

public class ArgsTest {
    //[-l], [-p, 8080], [-d, /usr/logs] 按功能划分，处理特定数组（简单）💡 课程中选取了最简单的方案实现
    // single input
    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(option.logging());
    }

    @Test
    public void should_set_boolean_option_to_true_if_flag__not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    static record BooleanOption(@Option("l") boolean logging) {
    }

    @Test
    public void should_parse_int_as_option_value() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(option.port(), 8080);
    }


    static record IntOption(@Option("p") int port) {
    }

    // TODO -d string
    @Test
    public void should_parse_string_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals(option.directory(), "/usr/logs");
    }


    static record StringOption(@Option("d") String directory) {
    }

    // multi input
    // TODO -l -p 8080 -d /usr/logs
    @Test
    public void should_parse_multi_options() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    // sad path
    // TODO 布尔类型：输入 -l a; -l 3
    // TODO 单整数类型：输入 -p a; -p 3.14
    // TODO 单个连续字符串类型：输入 -d a b c; -d /usr/logs /usr/logs/a.log
    // default value
    // TODO - bool : false
    // TODO - int : 0
    // TODO - string ""

    @Test
    @Disabled
    public void should_example_2() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging);
        assertEquals(8080, options.port);
        assertEquals("/usr/logs", options.directory);
    }

    record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }
}
