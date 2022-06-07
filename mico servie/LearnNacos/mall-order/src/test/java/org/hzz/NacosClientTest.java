package org.hzz;

import com.alibaba.nacos.client.naming.net.NamingHttpClientManager;
import com.alibaba.nacos.client.naming.utils.UtilAndComs;
import com.alibaba.nacos.common.constant.HttpHeaderConsts;
import com.alibaba.nacos.common.http.HttpRestResult;
import com.alibaba.nacos.common.http.client.NacosRestTemplate;
import com.alibaba.nacos.common.http.param.Header;
import com.alibaba.nacos.common.http.param.Query;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.alibaba.nacos.common.utils.VersionUtils;

import java.util.HashMap;
import java.util.Map;

public class NacosClientTest {
    public static void main(String[] args) throws Exception {
        NacosRestTemplate nacosRestTemplate = NamingHttpClientManager.getInstance().getNacosRestTemplate();
        Header header = builderHeader();
        Map<String, String> params = new HashMap<>();
        params.put("username","hzz");
        Map<String, String> body = new HashMap<>();
        body.put("body-key1","body-val1");
        body.put("body-key2","body-val2");
        String method = "POST";

        String url = "http://localhost:8888/nacos/v1/ns/instance";
        HttpRestResult<String> restResult = nacosRestTemplate
                .exchangeForm(url, header, Query.newInstance().initParams(params), body, method, String.class);

        System.out.println("我执行了吗");
        System.out.println(restResult);
    }

    public static Header builderHeader(){
        Header header = Header.newInstance();
        header.addParam(HttpHeaderConsts.CLIENT_VERSION_HEADER, VersionUtils.version);
        header.addParam(HttpHeaderConsts.USER_AGENT_HEADER, UtilAndComs.VERSION);
        header.addParam(HttpHeaderConsts.ACCEPT_ENCODING, "gzip,deflate,sdch");
        header.addParam(HttpHeaderConsts.CONNECTION, "Keep-Alive");
        header.addParam(HttpHeaderConsts.REQUEST_ID, UuidUtils.generateUuid());
        header.addParam(HttpHeaderConsts.REQUEST_MODULE, "Naming");
        return header;
    }
}
/**
 POST /nacos/v1/ns/instance?username=hzz HTTP/1.1
 Accept-Charset: UTF-8
 Accept-Encoding: gzip,deflate,sdch
 Client-Version: 1.4.2
 Content-Type: application/x-www-form-urlencoded;charset=UTF-8
 Request-Module: Naming
 RequestId: cb84e476-62e7-4435-a209-6c992889be4e
 User-Agent: Nacos-Java-Client:v1.4.2
 Host: localhost:8888
 Accept: text/html, image/gif, image/jpeg, *; q=.2, *\/*; q=.2
 Connection: keep-alive
 Content-Length: 40

 body-key2=body-val2&body-key1=body-val1&
 */