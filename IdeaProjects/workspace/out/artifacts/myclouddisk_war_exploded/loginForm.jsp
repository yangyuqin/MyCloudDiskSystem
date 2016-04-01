<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户登录</title>
    <meta http-equiv="expires" content="0">
    <style type="text/css">.style1 {
        color: rgba(48, 65, 151, 0.95);
        font-size: 24px;
        font-weight: bold;
    }
    </style>
</head>

<body>
<form action="<c:url value="/loginCheck.html"/>" method="post">
    <table width="50%" border="1" align="center" cellpadding="2" cellspacing="0" bordercolor="#CCCCCC">
        <caption>
            <span class="style1">      登 录      </span><br>
        </caption>

        <tr align="left">
            <th height="25" colspan="2" scope="row">
                <c:if test="${!empty error}">
                <font size=2px color="red"><c:out value="${error}"/></font>
                </c:if>
        </tr>

        <tr align="left">
            <th width="40%" height="35" align="right" scope="row">用户名:</th>
            <td width="60%"><input name="userName" type="text" id="userName" maxlength="20"></td>
        </tr>

        <tr align="left">
            <th height="35" align="right" scope="row">密&nbsp;&nbsp;码:</th>
            <td><input name="password" type="password" id="password" maxlength="30"></td>
        </tr>

        <tr align="center">
            <th height="35" colspan="2" scope="row">
                <input type="submit" value="登    录"
                       style=font-size:16px;font-weight:bold;width:100px;height:30px;background-color:#d9dcd9>
                <input type="reset" value="重    置"
                       style=font-size:16px;font-weight:bold;width:100px;height:30px;background-color:#d9dcd9
                       onclick="javascript:window.location='loginForm.jsp'">
            </th>
        </tr>

        <tr align="center">
            <th height="35" colspan="2" scope="row">
                <input type="button" value="注    册"
                       style=font-size:16px;font-weight:bold;width:100px;height:30px;background-color:#d9dcd9
                       onclick="javascript:window.location='userRegister.jsp'">
        </tr>

    </table>
</form>
</body>
</html>
