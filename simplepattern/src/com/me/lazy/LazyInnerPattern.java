package com.me.lazy;

public class LazyInnerPattern {
    private LazyInnerPattern(){}
    public static LazyInnerPattern getInstance(){
        return LazyInnerPatternHolder.lazyInnerPattern;
    }
    private static class LazyInnerPatternHolder{
        public static final LazyInnerPattern lazyInnerPattern = new LazyInnerPattern();
    }
}
