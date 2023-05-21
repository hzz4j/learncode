package org.hzz;

import org.apache.commons.beanutils.BeanUtils;
import org.hzz.entity.User;

import java.lang.reflect.InvocationTargetException;

public class MainTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        User user = new User("hzz", 18);
        User user1 = new User();
        System.out.println(user1);
        BeanUtils.copyProperties(user1,user);
        System.out.println(user1);
    }
}
