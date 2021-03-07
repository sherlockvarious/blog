package com.sherlockvarious.blog.service;

import com.sherlockvarious.blog.entity.User;

import java.util.List;

/**
 * @author sunchao
 * @created at 2020-11-08-16:44
 */
public interface UserService {

    public User selectById(Integer userId);

    //用户登录
    public User userLogin(User user);

    //发送注册时的验证码
    public List<String> sendVerificationCode(String email);


    public User selectByEmail(String email);

    public boolean register(User user,String msg);

    boolean resetPassword(User user);
}
