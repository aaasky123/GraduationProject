package com.wxj.work.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/admin")
public class LayuiController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return  "index";
    }

    @RequestMapping("/admin/adminLogin")
    public String login(String username, String password, String companyId, Model model){
        //获取当前的用户
        Subject subject= SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);

        //执行登录的方法
        try{
            subject.login(token);
            return "index";
        }catch (UnknownAccountException e){//登录信息错误
            model.addAttribute("msg","登录信息错误");
            return "login";
        }
        catch (IncorrectCredentialsException e){//登录信息错误
            model.addAttribute("msg","登录信息错误");
            return "login";
        }
    }
}
