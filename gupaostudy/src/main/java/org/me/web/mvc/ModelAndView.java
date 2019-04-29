package org.me.web.mvc;

import lombok.Data;

import java.util.Map;
@Data
public class ModelAndView {
    private String viewName;
    private Map<String,?> model;
    public ModelAndView(String s) {
        this(s,null);
    }

    public ModelAndView(String s, Map<String,?> model) {
        this.viewName = s;
        this.model = model;
    }

}
