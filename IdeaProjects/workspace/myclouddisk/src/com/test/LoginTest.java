package com.test;

import com.cons.CommonConstant;
import com.dao.interfaces.LoginLogDao;
import com.dao.interfaces.UserDao;
import com.entity.LoginLog;
import com.entity.User;
import com.mr.AverageCount;
import com.mr.WordCount;
import com.service.FileServiceImpl;
import com.service.UserServiceImpl;
import com.util.ParentPath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by yyq on 11/17/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
public class LoginTest {
    @Autowired
    private User user;
    @Autowired
    private UserDao te;
    @Autowired
    private LoginLog loginLog;
    @Autowired
    private LoginLogDao ll;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FileServiceImpl fileService;

    public static Log log = LogFactory.getLog(LoginTest.class);

    @Test
    public void testDatabaseLink() {
        //验证链接数据库成功
        System.out.println(te.getMatchCount("hadoop", "00"));
        System.out.println(te.getMatchCount("admin", "123456"));
        System.out.println(te.findUserByUserName("admin"));
        System.out.println(te.findUserByUserName("abc"));
        user = te.findUserByUserName("admin");
        user.setUserId(1);
        user.setUserName("admin");
        user.setLastIp("192.168.12.7");
        user.setLastVisit(new Date());
        te.updateLoginInfo(user);

        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        ll.insertLoginLog(loginLog);
    }

    @Test
    public void testHasMatchUser() {
        boolean b1 = userService.hasMatchUser("admin", "123456");
        boolean b2 = userService.hasMatchUser("admin", "1111");
        assertTrue(b1);
        assertTrue(!b2);
    }

    @Test
    public void testFindUserByUserName() {
        user = userService.findUserByUserName("admin");
        assertEquals(user.getUserName(), "admin");
    }

    @Test
    public void testAddLoginLog() {
        user = userService.findUserByUserName("admin");
        user.setUserId(1);
        user.setUserName("admin");
        user.setLastIp("192.168.12.7");
        user.setLastVisit(new Date());
        userService.loginSuccess(user);
    }
    @Test
    public void testCreateNewUser() throws IOException {
        User user1 = new User();
        user1.setUserName("yangyuqin");
        user1.setPassword("123456789");
        user1.setLastVisit(new Date());
        user1.setUserEmail("yangyq_2012@163.com");
        user1.setLastIp("112.112.120.120");
        userService.createNewUser(user1);
    }

    @Test
    public void test(){
        //log.error("怎么回事？");
        log.info("怎么回事？怎么回事？怎么回事？");
    }

    @Test
    public void capacityTest() throws IOException{
        User user = new User();
        user.setUserName("gao123");
        System.out.println("aaaaaaaaaaaaa");
        FileStatus[]  demo = fileService.listFiles(CommonConstant.HDFS + File.separator + user.getUserName());
        System.out.println(demo[0].getLen());
        System.out.println(demo[1]);
        System.out.println(demo[1].getPath().toString());
        //fileService.listFiles(CommonConstant.HDFS + File.separator + user.getUserName());
       // String usedCapacity = fileService.getUsedCapacity(CommonConstant.HDFS + File.separator + user.getUserName() + "/a");
        //hdfs://localhost:9000/user/yyq/filesSpace/gao123/a
        //hdfs://localhost:9000/user/yyq/filesSpace/gao123/a
        //String usedCapacity = fileService.getUsedCapacity("hdfs://localhost:9000/user/yyq/filesSpace/gao123/a");
        String usedCapacity = fileService.getUsedCapacity(demo[0].getPath().toString());
        System.out.println("UsedCapacity:"+ usedCapacity);
    }

    @Test
    public void parentPathTest() throws IOException{
        User user = new User();
        user.setUserName("yuqin");
        FileStatus[]  demo = fileService.listFiles(CommonConstant.HDFS + File.separator + user.getUserName());
        //System.out.println(demo[0].getPath().getName());
        System.out.println(ParentPath.getParentPath("hdfs://localhost:9000/user/yyq/filesSpace/yuqin/org.springframework.instrument-3.0.6.RELEASE.jar"));
    }

    @Test
    public void mrTest1() throws Exception{
        String p1 = "hdfs://localhost:9000/user/yyq/filesSpace/gao123/input01";
        String p2 = "hdfs://localhost:9000/user/yyq/filesSpace/gao123/output04";
        AverageCount.averagecountTest(p1,p2);
    }

    @Test
    public void mrTest2() throws Exception{
        String p1 = "hdfs://localhost:9000/user/yyq/filesSpace/gao123/input01";
        String p2 = "hdfs://localhost:9000/user/yyq/filesSpace/gao123/output02";
        WordCount.wordcountTest(p1, p2);
    }
}