package com.teradata.repository.slave;

import com.teradata.model.user.User;

import java.util.List;

public interface UserSlaveRepository {

    List<User> getSalveUsers();
}
