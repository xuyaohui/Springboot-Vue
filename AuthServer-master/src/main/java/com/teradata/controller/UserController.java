package com.teradata.controller;

import com.teradata.model.common.Result;
import com.teradata.model.user.SysUserVO;
import com.teradata.repository.master.UserRepository;
import com.teradata.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //获取所有用户信息
    @GetMapping("/getUsers")
    public Result<SysUserVO> getUsers(
            @RequestParam(value = "currentPage", required = false) int currentPage
            , @RequestParam(value = "limit", required = false) int limit //单页容量
            , @RequestParam(value = "userId", required = false) String userId ){

        int beginRow = 0;
        int endRow = 0;
        beginRow = 1 + (currentPage - 1) * limit;
        endRow = currentPage * limit;
        HashMap<String,Object> map= new HashMap<String,Object>();
        map.put("beginRow",beginRow);
        map.put("endRow",endRow);
        map.put("userId",userId);
        List<SysUserVO> users=null;
        int count=0;
        try{
            users=userRepository.getUserByUserId(map);
            count=userRepository.getCountByuserIdAndDepartName(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success(users,count);
    }

    //根据用户ID获取用户详细信息
    @GetMapping("/getUserInfo")
    public Result<SysUserVO> getUserInfo(@RequestParam(value = "userId", required = false) String userId, HttpServletRequest request){
        HashMap<String,Object> map= new HashMap<String,Object>();
        map.put("userId",userId);
        map.put("beginRow",0);
        map.put("endRow",10);
        List<SysUserVO> users=null;
        int count=0;
        try{
            users=userRepository.getUserByUserId(map);
            count=userRepository.getCountByuserIdAndDepartName(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success(users,count);
    }


    @GetMapping("/success")
    public String success() {
        return "login success";
    }
}
