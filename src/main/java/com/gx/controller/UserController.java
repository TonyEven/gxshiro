package com.gx.controller;

import com.gx.domain.User;
import com.gx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/**
 * Created by zhaoguoxin on 16/6/4.
 */
@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "login",method = RequestMethod.POST)
    private String login(){

        return "";

    }

    @RequestMapping(name = "/list", method= RequestMethod.GET)
    public String list(Model model, HttpRequest request){
        List<User> userList=userService.listAll(0,10);
        return "userList";
    }
}
