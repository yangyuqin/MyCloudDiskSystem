package com.entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


import java.io.Serializable;
import java.util.Date;


public class User implements Serializable{

	private int userId;
    @NotBlank(message="用户名不能为空")
    //@Pattern(regexp = "[a~zA~Z0~9_]{6,14}")
    private String userName;
    @NotBlank(message="密码不能为空")
//    @Pattern(regexp = "[a~zA~Z0~9_]{6,14}")
	private String password;
    @NotBlank(message="用户邮箱不能为空")
    @Email
    private String userEmail;
    private String lastIp;
    private Date lastVisit;


    public User() {
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	public Date getLastVisit() {
		return lastVisit;
	}
	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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

}
