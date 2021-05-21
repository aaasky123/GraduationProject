package com.wxj.work.controller;

import com.wxj.work.entity.Helper;
import com.wxj.work.service.HelperService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helper")
public class HelperController {
    @Autowired
    private HelperService helperService;

    @PostMapping("/addHelper")
    public Result addHelper(@RequestBody Helper helper){
        return helperService.addHelper(helper);
    }

    @PostMapping("/queryHelper")
    public Result queryHelper(@RequestBody Helper helper){
        return helperService.queryHelper(helper);
    }

    @PostMapping("/deleteHelper")
    public Result delHelper(@RequestBody String helperId){
        return helperService.deleteHelper(helperId);
    }
}
