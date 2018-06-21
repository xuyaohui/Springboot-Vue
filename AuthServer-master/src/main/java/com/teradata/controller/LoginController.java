package com.teradata.controller;

import com.teradata.interceptor.LoginInterceptor;
import com.teradata.model.user.SysUserVO;
import com.teradata.model.user.User;
import com.teradata.repository.master.UserRepository;
import com.teradata.repository.slave.UserSlaveRepository;
import com.teradata.utils.JwtUtil;
import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginController {

    private static Logger logger= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserSlaveRepository userSlaveRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login.do")
    public Map<String,Object> login(@RequestBody  Map<String,Object> reqMap, HttpSession session) throws ServletException {
        HashMap userMap=new HashMap();
        Map<String,Object> resultMap=new HashMap<String,Object>();
        logger.info("得到的pic: "+reqMap.get("picCode").toString());
        //校验用户验证码
        String picCode=reqMap.get("picCode").toString();
        if (!picCode.equalsIgnoreCase(String.valueOf(session.getAttribute(LoginInterceptor.SESSION_KEY)))){
            resultMap.put("success","false");
            return resultMap;
        }

        userMap.put("userId",reqMap.get("userId").toString());
        userMap.put("userPass",reqMap.get("userPass").toString());
        List<SysUserVO> users=userRepository.getUserByUserIdAndPassWd(userMap);
        resultMap.put("token",JwtUtil.getToken(reqMap.get("userId").toString(),""));
        System.out.println(JwtUtil.getToken(reqMap.get("userId").toString(),""));
        resultMap.put("data",users.get(0));
        resultMap.put("success","true");
        return resultMap;
    }

    //依据token获取userId，以此获取用户相关信息
    public Map<String,Object> getUserInfo(){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        return resultMap;
    }

    //随机生成字符串，用户拖拉验证码后获取字符串
    @PostMapping("/getPicCode")
    public String getPicChar(HttpSession session){
        String uuid=UUID.randomUUID().toString();
        session.setAttribute(LoginInterceptor.SESSION_KEY,uuid);
        return uuid;
    }

    @GetMapping("/getUsers")
    public List<User> getSalveUsers(){
        return userSlaveRepository.getSalveUsers();
    }

}
