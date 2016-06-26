package com.gx.service;

import com.gx.domain.User;

import java.util.List;

/**
 * Created by zhaoguoxin on 16/6/4.
 */
public interface UserService {
    List<User> listAll(Integer offset,Integer limit);
    User findUserByLoginName(String name);
}
