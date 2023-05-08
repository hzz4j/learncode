package org.hzz.dubbo;

import org.hzz.dubbo.protocol.dubbo.DubboProtocol;
import org.hzz.dubbo.protocol.http.HttpProtocol;

public class ProtocolFactory {
    public static Protocol getProtocol() {

        String name = System.getProperty("protocolName");
        System.out.println("name: " + name);
        if (name == null || name.equals("")) name = "http";
        switch (name) {
            case "http":
                return new HttpProtocol();
            case "dubbo":
                return new DubboProtocol();
            default:
                break;
        }
        return new HttpProtocol();

//        ExtensionLoader<Protocol> extensionLoader =
//                ExtensionLoader.getExtensionLoader(Protocol.class);

        // http
//        Protocol protocol = extensionLoader.getExtension(用户配置);

//        protocol.start(new URL("localhost", 8080));
    }
}
