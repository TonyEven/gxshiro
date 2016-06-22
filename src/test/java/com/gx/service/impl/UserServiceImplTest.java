package com.gx.service.impl;


import com.gx.domain.User;
import com.gx.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zhaoguoxin on 16/6/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Test
    public void listAll()   {
        List<User> users = userService.listAll(0, 10);

        System.out.println(users);
    }

}