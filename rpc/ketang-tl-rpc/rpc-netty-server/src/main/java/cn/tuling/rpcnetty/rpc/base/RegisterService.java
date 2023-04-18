package cn.tuling.rpcnetty.rpc.base;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类说明：注册服务到本地缓存
 */
@Service
public class RegisterService {

    /*本地可以提供服务的一个容器*/
    private static final Map<String,Class> serviceCache = new ConcurrentHashMap<>();

    /*注册本服务*/
    public void regService(String serviceName,Class impl){
        serviceCache.put(serviceName,impl);
    }

    /*获取服务*/
    public Class getLocalService(String serviceName){
        return serviceCache.get(serviceName);
    }


}
