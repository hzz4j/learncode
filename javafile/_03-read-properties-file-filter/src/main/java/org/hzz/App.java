package org.hzz;

import org.hzz.autoconfigure.AutoConfiguration;
import org.hzz.filters.Filter;
import org.hzz.filters.OnBeanCondition;
import org.hzz.filters.OnClassCondition;
import org.hzz.filters.OnWebApplicationCondition;
import org.hzz.readfile.AutoConfigureRead;
import org.hzz.readfile.MetadataRead;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    private final List<String> configurations;
    private final Properties metadata;

    private static final List<Filter> filters = new ArrayList<>();
    static {
        // add filters
        filters.add(new OnClassCondition());
        filters.add(new OnBeanCondition());
        filters.add(new OnWebApplicationCondition());
    }

    public App(){
        logger.info("加载自动配置类");
        this.configurations = new AutoConfigureRead().readFile(AutoConfiguration.class, App.class.getClassLoader());
        logger.info("加载要过滤的文件");
        this.metadata =  new MetadataRead().loadSpringFactories(App.class.getClassLoader());
        logger.info("加载完成");
    }

    public static void main(String[] args) {
        App app = new App();

        // 循环过滤
        logger.info("开始过滤,共有"+app.configurations.size()+"个自动配置类");

        String[] candidates = app.configurations.toArray(new String[0]);
        for (Filter filter : filters) {
            boolean[] match = filter.filter(app.configurations, app.metadata);
            for(int i = 0; i < match.length; i++){
                if(!match[i]){
                    candidates[i] = null;
                }
            }
        }

        List<String> result = new ArrayList<>(candidates.length);
        for (String candidate : candidates) {
            if(candidate != null){
                result.add(candidate);
            }
        }

        logger.info("过滤完成，只剩下"+result.size()+"个自动配置类");
    }


}
