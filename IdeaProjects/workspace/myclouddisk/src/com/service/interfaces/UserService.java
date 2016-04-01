package com.service.interfaces;

import com.entity.User;

import java.io.IOException;

/**
 * Created by yyq on 11/23/15.
 */
public interface UserService {
    boolean hasMatchUser(String userName, String password);

    User findUserByUserName(String userName);

    void loginSuccess(User user);

    void createNewUser(User user) throws IOException;

    String makeMD5(String password);
}
