package com.gx.controller;

import com.gx.domain.Authority;
import com.gx.domain.User;
import com.gx.myrealm.UserRealm;
import com.gx.service.UserService;
import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoguoxin on 16/6/4.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/menu")
    private String menu(HttpSession session, Map<String,Object> map){
        MenuRepository repository = new MenuRepository();
        ServletContext servletContext = session.getServletContext();
        MenuRepository defaultRepository = (MenuRepository) servletContext.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
        repository.setDisplayers(defaultRepository.getDisplayers());
        MenuComponent component = new MenuComponent();
        String name = "Shiro";
        component.setName(name);
        repository.addMenu(component);

        map.put("repository",repository);
        component.setTitle("List");
        UserRealm.ShiroUser shiroUser= (UserRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        List<Authority> authorities = shiroUser.getAuthorities();
        String contextPath = session.getServletContext().getContextPath();
        Map<Integer,MenuComponent> parentMenus = new HashMap<Integer, MenuComponent>();
        for(Authority authority : authorities){
            MenuComponent menu = new MenuComponent();
            menu.setName(authority.getName());
            menu.setTitle(authority.getDisplayName());
            menu.setLocation(contextPath + "/" + authority.getUrl());
            MenuComponent parentMenu = parentMenus.get(authority.getParentAuthority().getId());
            if (parentMenu == null) {
                parentMenu = new MenuComponent();
                parentMenu.setName(authority.getParentAuthority().getName()+ authority.getParentAuthority().getId());
                parentMenu.setTitle(authority.getParentAuthority().getDisplayName());
                parentMenu.setParent(component);
                parentMenus.put(authority.getParentAuthority().getId(),parentMenu);
            }
            menu.setParent(parentMenu);
            System.out.println(authority.getDisplayName());
        }
        return "menu";
    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    private String login(@RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes attributes){
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

    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/shiro-login";
    }
    @RequestMapping(name = "/list", method= RequestMethod.GET)
    public String list(Model model, HttpRequest request){
        List<User> userList=userService.listAll(0,10);
        return "userList";
    }
}
