package cn.tuling.rpc.remote.vo;

import java.io.Serializable;

/**
 * @author Mark老师
 * 类说明：注册中心注册服务的实体类
 */
public class RegisterServiceVo implements Serializable {
    private final String host;/*服务提供者的ip地址*/
    private final int port;/*服务提供者的端口*/

    public RegisterServiceVo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
