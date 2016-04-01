package com.dao;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dao.interfaces.UserDao;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by yyq on 11/17/15.
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl() {
        System.out.println("UserDao");
    }

//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    @Override
    public int getMatchCount(String userName, String password) {
        String sqlStr = " SELECT count(*) FROM t_user "
                + " WHERE user_name =? and password=? ";
        return jdbcTemplate.queryForInt(sqlStr, new Object[] { userName, password });
    }

    @Override
    public User findUserByUserName(final String userName) {
        String sqlStr = " SELECT user_id,user_name,user_email "
                + " FROM t_user WHERE user_name =? ";
        final User user = new User();
        jdbcTemplate.query(sqlStr, new Object[] { userName },
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                        user.setUserId(rs.getInt("user_id"));
                        user.setUserName(rs.getString("user_name"));
                        user.setUserEmail(rs.getString("user_email"));
                    }
                });
        return user;
    }

    @Override
    public void updateLoginInfo(User user) {
        String sqlStr = " UPDATE t_user SET last_visit=?,last_ip=?"
                + " WHERE user_id =?";
        jdbcTemplate.update(sqlStr, new Object[] { user.getLastVisit(),
                user.getLastIp(),user.getUserId()});

    }

    @Override
    public void saveUser(User user){
        String sqlStr = " Insert into t_user(user_name,user_email,password,last_visit,last_ip)"
                + "values(?,?,?,?,?)";
        jdbcTemplate.update( sqlStr, new Object[]{user.getUserName(),user.getUserEmail(),user.getPassword(),
                user.getLastVisit(),user.getLastIp()});
    }
}
