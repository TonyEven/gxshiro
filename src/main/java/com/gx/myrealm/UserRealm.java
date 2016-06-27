package com.gx.myrealm;

import com.gx.domain.User;
import com.gx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
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
        String username = uptoken.getUsername();
        User user = userService.findUserByLoginName(username);
        if( user == null ) {
            throw new UnknownAccountException("用户名"+user.getName()+"不存在.");
        }
        if (user.getEnabled() != 1){
            throw new LockedAccountException(user.getName()+"被锁定");
        }
        String principal = user.getName()+"("+user.getRole().getName()+")";
        Object hashedCredentials = user.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        String realmName = getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,hashedCredentials, credentialsSalt,realmName);
        return info;
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

    public static void main(String[] args){
        //盐值602QINVzKxVqIsVnwtr+iQ==    t7BUUxPTmwYpE/MhEMzgdA==
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        ByteSource byteSource = secureRandomNumberGenerator.nextBytes();
        String algorithmName = "MD5";
        Object source = "123456";
        Object salt = byteSource;
        System.out.println(salt);//rvL3/eOcpGSW0pSWOvtSjA==
        int hashIterations = 1024;
        Object result = new SimpleHash(algorithmName,source,salt,hashIterations);
        System.out.println(result);//9b2da8547c0e3cac047c332191cd0735
    }

}
