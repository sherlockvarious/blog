package com.sherlockvarious.blog.service;

import com.sherlockvarious.blog.dao.UserMapper;
import com.sherlockvarious.blog.entity.User;
import com.sherlockvarious.blog.entity.UserExample;
import com.sherlockvarious.blog.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author sunchao
 * @created at 2020-11-08-16:44
 */
@Service
public class UserServiceImp implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public User selectById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<String> sendVerificationCode(String email) {

        Random random = new Random();
        StringBuilder checkNum = new StringBuilder();
        List<String> retMsg = new ArrayList<>();

        String msg;
        try {
            for (int i = 0; i < 4; i++) {
                checkNum.append(Integer.toString(random.nextInt(10)));
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("2089105070@qq.com");
            message.setTo(email);
            message.setSubject("欢迎注册博客");
            message.setText("验证码：" + checkNum + "，15分钟内输入有效，立即注册");

            mailSender.send(message);
            msg = "200";
        } catch (Exception e) {
            msg = "出错了";
            retMsg.add(msg);
            return retMsg;
        }

        retMsg.add(msg);
        retMsg.add(checkNum.toString());

        return retMsg;

    }

    @Override
    public boolean resetPassword(User user) {

        try {
            String pw = MD5Utils.code(user.getPassword());

            user.setPassword(pw);
            UserExample example = new UserExample();
            example.createCriteria().andEmailEqualTo(user.getEmail());

            userMapper.updateByExampleSelective(user, example);

            return true;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean register(User user) {

        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        //初始化头像地址
        String first = "https://picsum.photos/id/";
        String id = Integer.toString(new Random().nextInt(1000));
        String avator = first+id+"/100/100";

        user.setAvatar(avator);

        String pw = MD5Utils.code(user.getPassword());
        user.setPassword(pw);

        if (userMapper.insertSelective(user) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public User selectByEmail(String email) {
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(email);
        if (userMapper.selectByExample(example).size() != 0) {
            return userMapper.selectByExample(example).get(0);
        }

        return null;
    }

    @Override
    public User userLogin(User user) {

        String password = user.getPassword();

        user.setPassword(MD5Utils.code(password));

        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(user.getEmail()).andPasswordEqualTo(user.getPassword());

        List<User> users = userMapper.selectByExample(example);

        if (users.size() == 1) {
            return users.get(0);
        }

        return null;
    }
}
