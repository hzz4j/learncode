package org.hzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class ViewPageController {
    @GetMapping("/showViewPage")
    public String passParametersWithModel(Model model) {
        Map<String, String> map = new HashMap<>();
        map.put("spring", "mvc");
        model.addAttribute("message", "Q10Viking-model");
        model.mergeAttributes(map);
        return "view/viewPage";
    }

    @GetMapping("/printViewPage")
    public String passParametersWithModelMap(ModelMap map) {
        map.addAttribute("welcomeMessage", "welcome");
        map.addAttribute("message", "Q10Viking-modelMap");
        return "view/viewPage";
    }

    @GetMapping("/goToViewPage")
    public ModelAndView passParametersWithModelAndView() {
        ModelAndView modelAndView = new ModelAndView("view/viewPage");
        modelAndView.addObject("message", "Q10Viking-modelAndView");
        return modelAndView;
    }
}
