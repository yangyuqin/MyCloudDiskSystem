package com.dao.interfaces;

import com.entity.User;

/**
 * Created by yyq on 11/23/15.
 */
public interface UserDao {
    int getMatchCount(String userName, String password);

    User findUserByUserName(String userName);

    void updateLoginInfo(User user);

    void saveUser(User user);
}
