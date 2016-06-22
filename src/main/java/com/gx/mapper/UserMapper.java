package com.gx.mapper;

import com.gx.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhaoguoxin on 16/6/4.
 */
public interface UserMapper {
    List<User> listAll(@Param("offset") Integer offset,@Param("limit")Integer limit);

    User getOne(@Param("name") String name);
}
