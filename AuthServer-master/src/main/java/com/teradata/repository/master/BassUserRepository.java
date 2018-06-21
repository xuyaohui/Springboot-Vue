package com.teradata.repository.master;

import com.teradata.model.user.BassUserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  xuyaohui
 *
 * 對OA用戶進行新增、刪除操作
 */
@Repository
public interface BassUserRepository {

    //批量插入OA用戶
    int insertBassUser(BassUserDTO user);

    //刪除表中所有OA用戶
    int deleteAllUsers();
}
