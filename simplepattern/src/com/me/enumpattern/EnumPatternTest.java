package com.me.enumpattern;

public class EnumPatternTest {
    public static void main(String[] args) {
        EnumPattern enumPattern = EnumPattern.getInstance(1);
        enumPattern.setColor("white");
        System.out.println(enumPattern);
    }
}
