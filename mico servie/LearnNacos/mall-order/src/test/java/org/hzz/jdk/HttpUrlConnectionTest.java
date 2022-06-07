package org.hzz.jdk;


import com.alibaba.nacos.common.http.HttpUtils;
import com.alibaba.nacos.common.http.param.MediaType;
import com.alibaba.nacos.common.utils.IoUtils;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUrlConnectionTest {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        String urlstr = "http://localhost:8888/nacos/v1/ns/instance?username=hzz";
        URI url = new URI(urlstr);
        HttpURLConnection conn = (HttpURLConnection)url.toURL().openConnection();

        // header
        Map<String,String> headers  = new HashMap<>();
        headers.put("Accept-Charset","UTF-8");
        headers.put("User-Agent","Nacos-Java-Client:1.4.2");
        // application/x-www-form-urlencoded;charset=UTF-8
        headers.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }

        // 连接的超时时间
        conn.setConnectTimeout(3000);
        // 读超时时间
        conn.setReadTimeout(50000);
        conn.setRequestMethod("POST");

        // 处理body
        Map<String,String> body = new HashMap<>();
        body.put("username","q10viking");
        body.put("book","java");

        if(body!=null){
            // 以application/x-www-form-urlencoded的方式编码成
            // body-key2=body-val2&body-key1=body-val1&
            String bodyStr = HttpUtils.encodingParams(body, headers.get("Accept-Charset"));

            // 发送body
            conn.setDoOutput(true);
            byte[] b = bodyStr.getBytes();
            conn.setRequestProperty("Content-Length",String.valueOf(b.length));
            OutputStream outputStream = conn.getOutputStream();  // 这里建立tcp连接
            outputStream.write(b,0,b.length);
            outputStream.flush();
            IoUtils.closeQuietly(outputStream);
        }

        conn.connect(); // 这里建立tcp连接？前面的已经建立过来，可写可不写，最后写一下

        RestResult<String> result = handleResponse(conn);
        System.out.println(result);
//        System.out.println("等待");
//        Thread.sleep(5000);
//        int code = conn.getResponseCode();
//        System.out.println("结束 "+code);
    }

    private static RestResult<String> handleResponse(HttpURLConnection conn) throws IOException{
        // headers
        Map<String,List<String>> headers = new HashMap<>();
        for (Map.Entry<String, List<String>> entry:
             conn.getHeaderFields().entrySet()) {
            if(entry.getKey()!=null){
                headers.put(entry.getKey(),entry.getValue());
            }else{
                System.out.println("key 为null的情况 "+entry.getValue());
            }

        }

        // body
         Reader reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
         Writer writer =  new CharArrayWriter();
        // 4096 4kb
        char[] c = new char[1<<12];
        long count = 0;
        for(int n = 0;(n = reader.read(c)) != -1;){
            writer.write(c,0,n);
            count += n;
        }
        System.out.println("----------------------------------------------");
        System.out.format("read %d chars\n",count);
        System.out.println("read "+count+" chars");
        System.out.println("----------------------------------------------");
        String body = writer.toString();

        // message
        String responseMessage = conn.getResponseMessage();
        // status code
        int responseCode = conn.getResponseCode();

        return RestResult.<String>builder()
                .withCode(responseCode)
                .withHeaders(headers)
                .withData(body)
                .withMsg(responseMessage)
                .build();
    }
}
