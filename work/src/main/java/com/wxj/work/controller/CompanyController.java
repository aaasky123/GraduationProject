package com.wxj.work.controller;

import com.wxj.work.entity.Company;
import com.wxj.work.service.CompanyService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public Result registerCompany(@RequestBody Company company){ return companyService.registerCompany(company); }

    @PostMapping("/queryAdmin")
    public Result queryAdmin(@RequestBody Company company){
        return companyService.queryAdmin(company);
    }

    @PostMapping("/addAdmin")
    public Result addAdmin(@RequestBody String addAdminData){
        return companyService.addAdmin(addAdminData);
    }

    @PostMapping("/delAdmin")
    public Result delAdmin(@RequestBody Company company){
        return companyService.delAdmin(company);
    }
}
