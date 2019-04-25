package org.me.beans.support;

import org.me.beans.config.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BeanDefinitionReader {
    private List<String> registryBeanClasses = new ArrayList<String>();
    private Properties config = new Properties();
    //固定配置文件中的 key，相对于 xml 的规范
    private final String SCAN_PACKAGE = "scanPackage";
    public BeanDefinitionReader(String ... configLocations) {
        //如果多个配置，会有问题
        InputStream is = this.getClass().getClassLoader().getResourceAsStream((configLocations[0].replace("classpath:","")));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File file = new File(url.getFile());
        for (File f : file.listFiles()) {
            if(f.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else{
                if(!f.getName().endsWith(".class")){continue;}
                String className = scanPackage + f.getName().replace(".class","");
                registryBeanClasses.add(className);
            }
        }

    }
    public List<BeanDefinition> loadBeanDefinitions(){
        List<BeanDefinition> result = new ArrayList<BeanDefinition>();
        try {
            for (String className : registryBeanClasses) {
                Class<?> beanClass = Class.forName(className);
                if(beanClass.isInterface()){continue;}
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));
                Class<?> []interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                   result.add(doCreateBeanDefinition(toLowerFirstCase(i.getSimpleName()),i.getName()));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    private BeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setFactoryBeanName(factoryBeanName);
        beanDefinition.setBeanClassName(beanClassName);
        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
//之所以加，是因为大小写字母的 ASCII 码相差 32，
// 而且大写字母的 ASCII 码要小于小写字母的 ASCII 码
//在 Java 中，对 char 做算学运算，实际上就是对 ASCII 码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
