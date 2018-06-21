package com.teradata.scheduled;

import com.teradata.model.user.BassUserDTO;
import com.teradata.repository.master.BassUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import shmc.bass.security.client.impl.SecurityContextUtils;
import shmc.bass.security.domain.BassUser;
import shmc.bass.security.service.AccountManager;
import shmc.bass.security.client.SecurityManager;

import java.util.List;


/**
 * @author xuyaohui
 * 定时请求业务人员手机号码存入表当中
 *
 * 通过所有部门编号获取所有用户号码
 */

@Configuration
@EnableScheduling
public class GetUserPhoneTask {

    @Autowired
    private BassUserRepository bassUserRepository;
    private static final Logger logger = LoggerFactory.getLogger(GetUserPhoneTask.class);

    /**
     * 每一秒中执行以下改方法
     * cron是用来指定执行的 秒，分钟，日期等
     */
    @Scheduled(cron="0 02 16 * * ?")
    public void insertBassUsers(){

        SecurityManager securityManager = SecurityContextUtils.getSecurityManager();
        AccountManager accountManager = securityManager.getAccountManager();
        //List<BassUser> users=accountManager.getUserByDepartmentId(Long.parseLong("52286"));
        //BassUser user=accountManager.getBassUserByUserId(Long.parseLong("9653"));
        List <BassUser> users =accountManager.getAllUserList();
        logger.info("长度： "+users.size());
        try {
            bassUserRepository.deleteAllUsers();
        }catch (Exception e){
            logger.info("數據更新失敗！！！");
            e.printStackTrace();
        }
        BassUserDTO bassUserDTO=new BassUserDTO();
        for (BassUser user :users){
            bassUserDTO.setLoginId(user.getLoginId());
            bassUserDTO.setUserName(user.getUserName());
            bassUserDTO.setUserPhone(user.getUserMobile());
            //logger.info(user.getLoginId()+" "+user.getUserName()+" "+user.getUserMobile());
            bassUserRepository.insertBassUser(bassUserDTO);
        }
        logger.info("數據更新成功！！！");

    }
}
