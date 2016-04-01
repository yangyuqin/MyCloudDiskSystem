package com.controller;

import com.cons.CommonConstant;
import com.entity.User;
import com.service.FileServiceImpl;
import com.service.UserServiceImpl;
import org.apache.hadoop.fs.FileStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by yyq on 11/17/15.
 */

@Controller
@SessionAttributes("user")
public class LoginController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping(value = "/index")
    public String loginPage(){
        return "loginForm";
    }

    @ModelAttribute("user")
    public User getUser(){
        User user = new User();
        user.setLastVisit(new Date());
        return user;
    }

    @RequestMapping(value = "/loginCheck", method= RequestMethod.POST)
    public ModelAndView loginCheck(HttpServletRequest request,@ModelAttribute("user") User user ) throws IOException {
        ModelAndView view = new ModelAndView();
        view.setViewName("forward:/loginForm.jsp");
        //将要登录的密码进行MD5加密再与数据库比较
        String MD5Password = userService.makeMD5(user.getPassword());
        boolean isValidUser = userService.hasMatchUser(user.getUserName(),MD5Password);
        if (!isValidUser) {
            view.addObject("error", "用户名或密码错误");
        } else {
            User login_user = userService.findUserByUserName(user.getUserName());
            login_user.setLastIp(request.getLocalAddr());
            login_user.setLastVisit(new Date());
            userService.loginSuccess(login_user);
            setSessionUser(request,login_user);

            FileStatus[] list =  fileService.listFiles(CommonConstant.HDFS + File.separator + user.getUserName());
            String usedCapacity = fileService.getUsedCapacity(CommonConstant.HDFS + File.separator + user.getUserName());
            request.setAttribute("usedCapacity",usedCapacity);
            request.setAttribute("list",list);
            request.setAttribute("userName",user.getUserName());
            logger.info("LoginLog~~~~~");
            view.setViewName("redirect:/mainAction.html");
        }
        return view;
    }

    /**
     * 展现到主界面
     */
    @RequestMapping(value = "/mainAction")
    public String mainForm(HttpServletRequest request,ModelMap modelMap,SessionStatus sessionStatus) throws IOException {
        User user = (User)modelMap.get("user");
        if(user != null){
            setSessionUser(request,user);
            FileStatus[] list =  fileService.listFiles(CommonConstant.HDFS + File.separator + user.getUserName());
            String usedCapacity = fileService.getUsedCapacity(CommonConstant.HDFS + File.separator + user.getUserName());
            request.setAttribute("usedCapacity",usedCapacity);
            request.setAttribute("userName",user.getUserName());
            request.setAttribute("list",list);
            logger.info("LoginLog: "+user.getUserName()+new Date()+request.getLocalAddr());
            //sessionStatus.setComplete();
        }
        return "mainForm";
    }


    /**
     * 登录注销
     * @param session
     * @return
     */
    @RequestMapping("/doLogout")
    public String logout(HttpSession session) {
        session.removeAttribute(CommonConstant.USER_CONTEXT);
        logger.info("LoginOutLog~~~");
        return "redirect:/loginForm.jsp";
    }
}
