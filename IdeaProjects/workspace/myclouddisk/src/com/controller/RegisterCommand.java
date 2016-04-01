package com.controller;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by yyq on 11/19/15.
 */
public class RegisterCommand {
    @NotBlank(message="用户名不能为空")
    @Pattern(regexp = "[a~zA~Z0~9_]{6,14}")
    private String userName;
    @NotBlank(message="密码不能为空")
    @Pattern(regexp = "[a~zA~Z0~9_]{6,14}")
    private String password;
    @NotBlank(message="确认密码不能为空")
    @Pattern(regexp = "[a~zA~Z0~9_]{6,14}")
    private String confirmPassword;
    @NotBlank(message="邮箱不能为空")
    @Email
    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public RegisterCommand() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


}
