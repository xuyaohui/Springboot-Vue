package com.teradata.repository.master;

import com.teradata.model.user.SysUserVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xuyaohui on 2017/11/27.
 */

@Repository
public interface UserRepository {

    List<SysUserVO> getUserByUserId(HashMap<String,Object> hashMap);

    int getCountByuserIdAndDepartName(HashMap<String,Object> hashMap);

    List<SysUserVO> getUserByUserIdAndPassWd(HashMap<String,Object> hashMap);
}
