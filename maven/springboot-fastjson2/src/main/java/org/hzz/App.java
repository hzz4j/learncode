package org.hzz;

import cn.hutool.core.io.resource.ClassPathResource;
import org.hzz.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@RestController
@EnableWebSocket
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/")
    public Object test(){
        User user = new User();
        user.setName("Q10Viking");
        user.setPassword("Root.123456");
        return user;
    }

    @GetMapping(value = "/hello",produces = {"text/html;charset=UTF-8"})
    public void hello(HttpServletResponse response){
        //返回一个html测试页面
        try (ServletOutputStream servletOutputStream = response.getOutputStream();
        ) {
            ClassPathResource classPathResource = new ClassPathResource("hello.html");

            servletOutputStream.write(classPathResource.readBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
