package com.gx.service;

import com.gx.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhaoguoxin on 16/6/4.
 */
public interface UserService {
    List<User> listAll(Integer offset,Integer limit);
    User getOne(String name);
}
