package com.controller;

import com.cons.CommonConstant;
import com.entity.User;
import com.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by yyq on 11/17/15.
 */
@Controller
public class RegisterController {
    private static Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ModelAndView userRegister(HttpServletRequest request, @Valid RegisterCommand registerCommand,BindingResult bindingResult) throws IOException {
        ModelAndView view = new ModelAndView();
        view.setViewName("forward:/userRegister.jsp");

        Pattern userNamePattern = Pattern.compile(CommonConstant.userNameRegrex);
        Pattern passwordPatter = Pattern.compile(CommonConstant.passwordRegrex);
        Pattern emailPatter = Pattern.compile(CommonConstant.emailRegrex);

        User user = userService.findUserByUserName(registerCommand.getUserName());
        if(!userNamePattern.matcher(registerCommand.getUserName()).matches()){
            view.addObject("error1", "用户名格式错误，长度为2～14，包括数字、大小写字母");
        }else if(!passwordPatter.matcher(registerCommand.getPassword()).matches()){
            view.addObject("error1", "密码格式错误");
        }else if(!emailPatter.matcher(registerCommand.getUserEmail()).matches()){
            view.addObject("error1","邮箱格式错误");
        }else if (user.getUserName() != null) {
            view.addObject("error1", "此用户名已存在");
        }else if (bindingResult.hasErrors()){
            return view;
        } else if (registerCommand.getUserName() == null || registerCommand.getUserName().trim().equals("") ||
                registerCommand.getPassword() == null || registerCommand.getPassword().trim().equals("") ||
                registerCommand.getConfirmPassword() == null || registerCommand.getConfirmPassword().trim().equals("") ||
                registerCommand.getUserEmail() == null || registerCommand.getUserEmail().trim().equals("")) {
            view.addObject("error2", "请将必填项填写完整");
        } else if (!registerCommand.getPassword().equals(registerCommand.getConfirmPassword())) {
            view.addObject("error3", "两次密码不匹配");
        } else if (registerCommand.getPassword().length() < 6 || registerCommand.getPassword().length() > 14) {
            view.addObject("error4", "密码长度不得少于6个字符，不得多于14个字符");
        } else {
            User newUser = new User();
            newUser.setUserName(registerCommand.getUserName());

            //新用户密码加密存储 MD5
            String MD5Password = userService.makeMD5(registerCommand.getPassword());
            newUser.setPassword(MD5Password);
            newUser.setUserEmail(registerCommand.getUserEmail());
            newUser.setLastIp(request.getLocalAddr());
            newUser.setLastVisit(new Date());
            userService.createNewUser(newUser);
            logger.info("RegisterController~~~~~");
            view.setViewName("redirect:/loginForm.jsp");
        }
        return view;
    }
}
