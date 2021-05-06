package com.jackxue.monitor.controller;

import com.jackxue.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list1")
    public String list1(){
        return userService.list1().toString();
    }
    @GetMapping("/list2")
    public String list2(){
        return userService.list2().toString();
    }
}
