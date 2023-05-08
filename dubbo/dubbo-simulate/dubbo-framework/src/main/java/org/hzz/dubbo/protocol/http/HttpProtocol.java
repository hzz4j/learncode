package org.hzz.dubbo.protocol.http;

import org.hzz.dubbo.Invocation;
import org.hzz.dubbo.Protocol;
import org.hzz.dubbo.URL;

public class HttpProtocol implements Protocol {
    @Override
    public void start(URL url) {

    }

    @Override
    public String send(URL url, Invocation invocation) {
        return null;
    }
}
