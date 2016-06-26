package com.gx.myrealm;

import com.gx.domain.User;
import com.gx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by zhaoguoxin on 16/6/12.
 */
public class UserRealm extends AuthorizingRealm {



    @Autowired
    private UserService userService;

    /**
     * 授权方法，在配有缓存的情况下，只加载一次。
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        User user = userService.findUserByLoginName(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles((Set<String>) user.getRoleList());
        return info;
    }

    /**
     * 登录认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
        User user = userService.findUserByLoginName(uptoken.getUsername());
        if( user != null ) {
            return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
        } else {
            return null;
        }
    }

    /**
     * 初始化 使用MD5 加密
     */
    @PostConstruct
    public void initCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("MD5");
        // 加密1024次
        hashedCredentialsMatcher.setHashIterations(1024);
        //
        setCredentialsMatcher(hashedCredentialsMatcher);
    }

}
