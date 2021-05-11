package com.jackxue.monitor.controller;

import com.jackxue.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list1")
    public String list1(Model model){
        model.addAttribute("users", userService.list1());
        return "index";
    }
    @GetMapping("/list2")
    public String list2(){
        return userService.list2().toString();
    }
}
