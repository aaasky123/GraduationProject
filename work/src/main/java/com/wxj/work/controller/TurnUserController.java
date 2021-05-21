package com.wxj.work.controller;

import com.wxj.work.entity.TurnUser;
import com.wxj.work.service.TurnUserService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contactPeople")
public class TurnUserController {
    @Autowired
    private TurnUserService turnUserService;

    @PostMapping("/addCommonTurnUser")
    public Result addCommonTurnUser(@RequestBody TurnUser turnUser){ return turnUserService.addCommonTurnUser(turnUser); }

    @PostMapping("/queryCommonTurnUser")
    public Result queryCommonTurnUserList(@RequestBody String number){ return  turnUserService.queryCommonTurnUserList(number); }

    @PostMapping("/delCommonTurnUser")
    public Result delCommonTurnUser(@RequestBody TurnUser turnUser){ return turnUserService.delCommonTurnUser(turnUser); }
}
