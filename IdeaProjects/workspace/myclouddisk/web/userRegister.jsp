<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户注册</title>
    <meta http-equiv="expires" content="0">
    <style type="text/css">.style1 {
        color: rgba(48, 65, 151, 0.95);
        font-size: 24px;
        font-weight: bold;
    }
    </style>
</head>

<body>
<form name="userRegisterForm" method="post" action="<c:url value="/userRegister.html"/>" onsubmit="return checkForm(this)">
    <form:errors path="*"/>

    <table width="60%" border="0" align="center" cellpadding="2" cellspacing="0">
        <caption>
            <font color="blue" style="font-size: 20px">用户注册</font><br></caption>
        <tr bgcolor="#EFEFEF">
            <td height="40" width="25%" align="right">用户名:</td>
            <td width="30%" valign="middle">
                <input name="userName" type="text" id="userName" size="20" maxlength="50"></td>
            <td width="45%">*
                <form:errors path="userName" cssClass="errorClass"/>
                <c:if test="${!empty error1}">
                    <font size=2px color="red"><c:out value="${error1}"/></font>
                </c:if>
            </td>
        </tr>

        <tr>
            <td height="40" align="right">密　码:</td>
            <td><input name="password" type="password" id="password" size="20" maxlength="50"></td>
            <td>*(长度6～14位,支持数字和字母以及下划线)
                <form:errors path="password" cssClass="errorClass"/>
                <c:if test="${!empty error4}">
                    <font size=2px color="red"><c:out value="${error4}"/></font>
                </c:if>
            </td>
        </tr>

        <tr bgcolor="#EFEFEF">
            <td height="40" align="right" valign="middle">密码确认:</td>
            <td><input name="confirmPassword" type="password" id="confirmPassword" size="20" maxlength="50"></td>
            <td>*(请再输一遍，以便确认)
                <form:errors path="userName" cssClass="errorClass"/>
                <c:if test="${!empty error3}">
                    <font size=2px color="red"><c:out value="${error3}"/></font>
                </c:if>
            </td>
        </tr>

        <tr>
            <td height="40" align="right" valign="middle">电子邮件:</td>
            <td><input name="userEmail" type="text" size="20" maxlength="50"></td>
            <td>*(请输入正确的E-mail地址)
                <form:errors path="userName" cssClass="errorClass"/>
            </td>
        </tr>
        <tr>
            <td colspan="3" align="center" valign="middle" >
                <input type="submit" value="提    交"
                       style=font-size:16px;font-weight:bold;width:100px;height:30px;background-color:#C6BDBB>
                <a href="http://localhost:8080/loginForm.jsp" onclick="history.back();">返回</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>

<script language="javascript">
    function checkForm(form) {
        if (isEmpty(form.userName.value) || isEmpty(form.password.value) || isEmpty(form.confirmPassword.value) || isEmpty(form.userName.value)) {
            alert("请将必填项填写完整");
            return false;
        }
        if (form.password.value.length < 6 || form.password.value.length > 14) {
            alert("密码长度不得少于6个字符，不得多于14个字符");
            return false;
        }
        if (form.password.value != form.confirmPassword.value) {
            alert("两次密码不匹配");
            return false;
        }
        return true;
    }

    function isEmpty(str) {
        if (str == null || str.length == 0)
            return true;
        else
            return false;
    }
</script>

