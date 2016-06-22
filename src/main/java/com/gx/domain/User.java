package com.gx.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zhaoguoxin on 16/6/3.
 */
public class User {
    private Integer id;
    private String name;
    private String password;
    private int enabled ;

    //盐值, 进行密码加密
    private String salt;
    //该用户拥有的角色
    private Role role;

    public User(){}
    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Collection<String> getRoleList() {
        Collection<String> roles =  new ArrayList<String>();

        if(role != null){
            for(Authority authority: role.getAuthorities()){
                roles.add(authority.getName());
            }
        }

        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", salt='" + salt + '\'' +
                ", role=" + role +
                '}';
    }
}
