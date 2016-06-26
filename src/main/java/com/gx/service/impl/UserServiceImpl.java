package com.gx.service.impl;

import com.gx.domain.User;
import com.gx.mapper.UserMapper;
import com.gx.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhaoguoxin on 16/6/4.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> listAll(Integer offset,Integer limit) {
        return userMapper.listAll(offset,limit);
    }

    public User findUserByLoginName(String name) {
        return userMapper.findUserByLoginName(name);
    }
}
