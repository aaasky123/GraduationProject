package com.wxj.work.controller;


import com.wxj.work.entity.User;
import com.wxj.work.util.Result;
import com.wxj.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        return userService.login(user);
    }

    @PostMapping("/userInformation")
    public Result userInformation(@RequestBody String number){ return userService.queryUserInformation(number); }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody String number, String oldPassword, String newPassword){ return  userService.changePassword(number, oldPassword, newPassword); }

    @PostMapping("/register")
    public Result registerUser(@RequestBody User user){ return  userService.registerUser(user); }

    @PostMapping("/queryCompanyAllUser")
    public Result queryCompanyUser(@RequestBody String number){ return userService.queryCompanyUser(number); }

    @PostMapping("/updateCompanyId")
    public Result updateCompanyId(@RequestBody User user){ return userService.updateCompanyId(user); }

    @PostMapping("/updateInformation")
    public Result updateUserInformation(@RequestBody String data){
        return userService.updateUserInformation(data);
    }

    @PostMapping("/updatePassword")
    public Result updateForgetPassword(@RequestBody User user){
        return userService.updateForgetPassword(user);
    }

    @PostMapping("/adminLogin")
    public Result adminLogin(@RequestBody String loginData){
        return userService.loginByAdmin(loginData);
    }

    @PostMapping("/delCompanyId")
    public Result delCompanyId(@RequestBody User user){
        return userService.delCompanyId(user);
    }
}
