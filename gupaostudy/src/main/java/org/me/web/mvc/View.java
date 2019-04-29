package org.me.web.mvc;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class View {
    private final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";
    private File viewFile;

    public View(File templateFile) {
        this.viewFile = templateFile;
    }

    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        StringBuffer sb = new StringBuffer();
        RandomAccessFile ra = new RandomAccessFile(this.viewFile, "r");
        try {
            String line = null;
            while ((line = ra.readLine()) != null) {
                line = new String(line.getBytes("ISO-8859-1"), "utf-8");
                Pattern pattern = Pattern.compile("￥\\{[^\\}]+\\}", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(line);
                //缺少多个参数时的处理
                if (matcher.find()) {
                    String paramName = matcher.group();
                    paramName = paramName.replaceAll("￥\\{|\\}", "");
                    Object paramValue = model.get(paramName);
                    if (null == paramValue) {
                        continue;
                    }
                    line = matcher.replaceFirst(makeStringForRegExp(paramValue.toString()));
                    matcher = pattern.matcher(line);
                }
                sb.append(line);

            }

        } finally {
            if (ra != null) {
                ra.close();
            }
        }

    }

    //处理特殊字符
    public static String makeStringForRegExp(String str) {
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }
}
