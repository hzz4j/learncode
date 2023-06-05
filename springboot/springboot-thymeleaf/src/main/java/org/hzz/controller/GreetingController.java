package org.hzz.controller;


import lombok.extern.slf4j.Slf4j;
import org.hzz.entity.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class GreetingController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello(Model model) {
        return model.toString();
    }

    @GetMapping("/greeting") // 获取表单页面
    public String greetingForm(Model model) {
        log.info(model.toString());
        model.addAttribute("greeting", new Greeting());
        return "greeting/greeting";
    }


    // 提交表单
    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("greeting", greeting);
        log.info(greeting.toString());
        log.info(model.toString());
        return "greeting/result";
    }

}
