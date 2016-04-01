package com.service;

import com.cons.CommonConstant;
import com.dao.interfaces.LoginLogDao;
import com.dao.interfaces.UserDao;
import com.entity.LoginLog;
import com.entity.User;
import com.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by yyq on 11/17/15.
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private com.dao.interfaces.HDFSDao hdfsDao;

    public UserServiceImpl() {
        System.out.println("UserService");
    }

    @Override
    public boolean hasMatchUser(String userName, String password) {
        int matchCount =userDao.getMatchCount(userName, password);
        return matchCount > 0;
    }

    @Override
    public User findUserByUserName(String userName) {
        return userDao.findUserByUserName(userName);
    }

    @Override
    public void loginSuccess(User user) {
    //    user.em( 5 + user.getCredits());
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        userDao.updateLoginInfo(user);
        loginLogDao.insertLoginLog(loginLog);
    }

    @Override
    public void createNewUser(User user) throws IOException {
        User u = userDao.findUserByUserName(user.getUserName());
        String path = CommonConstant.HDFS+"/"+user.getUserName();
        if (u.getUserName() != null || hdfsDao.isExsis(path) ){
            System.out.println("用户名已经存在 或 文件夹名已存在");
        }
        else{
            hdfsDao.mkdirs(path);
            userDao.saveUser(user);
        }
    }

    @Override
    public String makeMD5(String password) {
        MessageDigest md = null;
        try {
            // 生成一个MD5加密计算摘要
            md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(password.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String pwd = new BigInteger(1, md.digest()).toString(16);
            System.err.println(pwd);
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
}
