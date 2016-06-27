package com.gx.controller;

import com.gx.domain.User;
import com.gx.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    private String login(@RequestParam String username, String password, RedirectAttributes attributes){
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        usernamePasswordToken.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException ex) {//用户名没有找到
            ex.printStackTrace();
        } catch (IncorrectCredentialsException ex) {//用户名密码不匹配
            ex.printStackTrace();
        }catch (LockedAccountException lae) {// 用户被锁定

        }catch (AuthenticationException e) {//其他的登录错误
            e.printStackTrace();
        }

        if(subject.isAuthenticated()){
            return "authorityList";
        }else{
            return "login";
        }

    }

    @RequestMapping(name = "/list", method= RequestMethod.GET)
    public String list(Model model, HttpRequest request){
        List<User> userList=userService.listAll(0,10);
        return "userList";
    }
}
