package org.me.web.mvc;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;
@Data
public class HandlerMapping {
    private Object controller;
    private Pattern pattern;
    private Method method;
    public HandlerMapping(Pattern pattern, Object controller, Method m) {
        this.pattern = pattern;
        this.controller = controller;
        this.method = m;
    }
}
