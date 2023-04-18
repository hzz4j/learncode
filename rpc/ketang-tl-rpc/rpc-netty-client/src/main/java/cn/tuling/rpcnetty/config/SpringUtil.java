package cn.tuling.rpcnetty.config;

import org.springframework.context.ApplicationContext;

public class SpringUtil {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return SpringUtil.applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }
}
