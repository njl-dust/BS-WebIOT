package com.example.service;

import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    public Admin login(Admin admin)
    {
        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());

        //异常处理
        if(dbAdmin == null){
            //不存在账号
            throw new CustomException("账号不存在！");
        }

        if(!dbAdmin.getPassword().equals(admin.getPassword())){
            //密码错误
            throw new CustomException("密码错误！");
        }
        //登录成功
        return dbAdmin;
    }

}
