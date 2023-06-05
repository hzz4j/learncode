package org.hzz.controller;

import lombok.extern.slf4j.Slf4j;
import org.hzz.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@Slf4j
public class EmployeeController {

    Map<Long, Employee> employeeMap = new HashMap<>();

    @ModelAttribute("employees")
    public List<Employee> initEmployees() {
        log.info("initEmployees: size="+ employeeMap.size());
        employeeMap.put(1L, new Employee(1L, "John", "223334411", "rh"));
        employeeMap.put(2L, new Employee(2L, "Peter", "22001543", "informatics"));
        employeeMap.put(3L, new Employee(3L, "Mike", "223334411", "admin"));
        return new ArrayList<Employee>(employeeMap.values());
    }

    @ModelAttribute
    public void addAttributes(final Model model) {
        log.info("addAttributes");
        model.addAttribute("msg", "Welcome to the Netherlands!");
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showEmployees(@ModelAttribute("employees") final List<Employee> employees) {
        System.out.println(Arrays.toString(employees.toArray()));
        return new ModelAndView("employee/employeeHome");
    }

    @RequestMapping(value = "/employee/{Id}",method = RequestMethod.GET)
    public @ResponseBody Employee getEmployeeById(@PathVariable final Long Id) {
        return employeeMap.get(Id);
    }
}
