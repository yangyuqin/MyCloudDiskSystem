<%--
  Created by IntelliJ IDEA.
  User: yyq
  Date: 11/26/15
  Time: 10:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script lang="javascript">
        function OnClose(check)
        {
            if(check == true)
            {
                var value = document.getElementById("fn").value;
                window.returnValue = value;
            }
            window.close();
        }
    </script>
</head>
<body scroll="no">
<div style="width:200px; left:40px; top:40px; position:absolute; height:55px">
    <div  style="width:208px; left:50px; top:0px; position:absolute; height:24px">
        <font class="big1_text">输入新建文件名:</font>
    </div>
    <div style="width:auto; left:50px; top:25px; position:absolute; height:20px">
        <input type="text" id ="fn" size="12" maxlength="20"/>
    </div>
    <div style="width:60px; left:150px; top:55px; position:absolute; height:20px">
        <input type="button" value="确认" onclick="OnClose(true)" />
    </div>
    <div style="width:60px; left:60px; top:55px; position:absolute; height:20px">
        <input type="button" value="取消" onclick="OnClose(false)" />
    </div>
</div>
</body>
</html>
