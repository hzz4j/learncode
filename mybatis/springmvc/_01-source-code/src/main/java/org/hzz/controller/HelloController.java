package org.hzz.controller;

import lombok.extern.slf4j.Slf4j;
import org.hzz.entity.User;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {
        @GetMapping("/say")
        public String say() {
            log.info("HelloController say");
            return "Hello World!";
        }
//
//
//        @GetMapping("/say2")
//        public String say2(@RequestParam("username")String name, Integer age) {
//            log.info("name = "+ name + ", age = " + age);
//            return "success";
//        }
//
//
//    /**
//     * 不可用
//     */
//    @GetMapping("/say3")
//        public String say3(@RequestBody Map<String,Object> map) {
//            log.info("name = "+ map.get("username") + ", age = " + map.get("age"));
//            return "success";
//    }
//
//    @GetMapping("/say4")
//    public String say4(@RequestBody User user) {
//        log.info("name = "+ user.getUsername() + ", age = " + user.getAge());
//        return "success";
//    }
//
//    @PostMapping("/say5")
//    public String say5(@RequestBody MultiValueMap<String,Object> map) {
//        log.info("name = "+ map.get("username") + ", age = " + map.get("age"));
//        return "success";
//    }
//
//    @PostMapping("/say6")  // 需要删除@RequestBody
//    public String say6(User user) {
//        log.info("name = "+ user.getUsername() + ", age = " + user.getAge());
//        return "success";
//    }
}
