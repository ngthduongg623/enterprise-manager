package io.github.ngthduongg623.enterprise_manager.dao;

import io.github.ngthduongg623.enterprise_manager.entity.User;

public interface UserDao {

    User findByUserName(String userName);
    void save(User user);
    
}
