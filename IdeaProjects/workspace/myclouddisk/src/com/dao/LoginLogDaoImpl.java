package com.dao;

import com.dao.interfaces.LoginLogDao;
import com.entity.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by yyq on 11/17/15.
 */
@Repository
public class LoginLogDaoImpl implements LoginLogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LoginLogDaoImpl() {
        System.out.println("LoginLogDao");
    }

    @Override
    public void insertLoginLog(LoginLog loginLog) {
        String sqlStr = "INSERT INTO t_login_log(user_id,ip,login_datetime) "
                + "VALUES(?,?,?)";
        Object[] args = { loginLog.getUserId(), loginLog.getIp(),loginLog.getLoginDate() };
        jdbcTemplate.update(sqlStr, args);
    }
}
