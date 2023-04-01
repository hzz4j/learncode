package com.zhouyu;

import com.spring.ZhouyuApplicationContext;
import com.zhouyu.service.UserInterface;
import com.zhouyu.service.UserService;

/**
 * @author 周瑜
 */
public class Test {

    public static void main(String[] args) {

        // 扫描--->创建单例Bean BeanDefinition BeanPostPRocess
        ZhouyuApplicationContext applicationContext = new ZhouyuApplicationContext(AppConfig.class);

        UserInterface userService = (UserInterface) applicationContext.getBean("userService");
        userService.test();
    }
}
