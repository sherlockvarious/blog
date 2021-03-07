package com.sherlockvarious.blog.Controller.admin;

import com.sherlockvarious.blog.entity.User;
import com.sherlockvarious.blog.service.UserService;
import com.sherlockvarious.blog.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchao
 * @created at 2020-11-08-16:27
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    UserService userService;


    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/page/index";
    }

    @RequestMapping("/user")
    @ResponseBody
    public Object userLogin(User user, HttpSession session) {

        User one = userService.userLogin(user);
        if (one == null) {
            return Result.error("登陆失败 请检查用户名和密码");
        }

        session.setAttribute("user", one);


        return Result.success(one);

    }

    @RequestMapping("/forgetPasswordVerificationCode")
    @ResponseBody
    public Object sendForgetPasswordVerificationCode(String email){

        if (userService.selectByEmail(email)!=null){
            return Result.success(userService.sendVerificationCode(email).get(1));
        }else {
            return Result.error("该邮箱未注册");
        }

    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    public Object resetPassword(User user){
        //在发送验证码的那一步已经检测过是否有该邮箱存在了

        if (userService.resetPassword(user)){
            return Result.success();
        }else {
            return Result.error("更改密码失败");
        }
    }

    @RequestMapping("/sendRegisterVerificationCode")
    @ResponseBody
    public List<String> sendVerificationCode(String email, HttpServletResponse response){

        List<String> retMsg = new ArrayList<>();

        if (userService.selectByEmail(email)==null){
            return userService.sendVerificationCode(email);
        }else {
            retMsg.add("该邮箱已被注册");
            return retMsg;
        }

    }
    @RequestMapping("/register")
    @ResponseBody
    public String userRegister(User user){
        String msg = "";

        userService.register(user,msg);
        return msg;
    }
}
