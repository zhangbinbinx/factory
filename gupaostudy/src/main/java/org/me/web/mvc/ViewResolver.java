package org.me.web.mvc;

import lombok.Data;

import java.io.File;
import java.util.Locale;
@Data
public class ViewResolver {
    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";
    private File tempRootDir;
    private String viewName;
    public ViewResolver(String path) {
        String tempPath = this.getClass().getClassLoader().getResource(path).getFile();
        tempRootDir = new File(tempPath);
    }

    public View resolverViewName(String viewName, Locale locale) {
        this.viewName = viewName;
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : viewName + DEFAULT_TEMPLATE_SUFFIX;
        File templateFile = new File((tempRootDir.getPath() + "/" + viewName ).replaceAll("/+","/"));
        return new View(templateFile);
    }

}
