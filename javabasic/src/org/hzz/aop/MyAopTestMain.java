package org.hzz.aop;

import org.hzz.aop.config.MyAop;
import org.hzz.aop.proxy.JDKProxy;
import org.hzz.aop.proxy.ProxyFactory;
import org.hzz.aop.service.IUserSrv;
import org.hzz.aop.service.UserSrv;
import org.hzz.aop.utils.AdviceUtil;

public class MyAopTestMain {

    public static void main(String[] args) {
        IUserSrv userSrv = getProxy(new UserSrv());
        userSrv.hello();
    }

    public static <T> T getProxy(T t) {
        ProxyFactory proxyFactory = new ProxyFactory();
        // aop的对象
        MyAop aop = new MyAop();
        proxyFactory.setTarget(t);
        proxyFactory.setMethodInterceptors(AdviceUtil.getMethodInterceptors(aop));

        return (T) new JDKProxy(proxyFactory).getProxy();
    }
}
