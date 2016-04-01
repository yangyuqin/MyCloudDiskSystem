<%--
  Created by IntelliJ IDEA.
  User: yyq
  Date: 11/27/15
  Time: 8:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script lang="javascript">
        function OnClose(check)
        {
            if(check == true){
                window.close();
                return true;
            }else{
                window.close();
                return false;
            }
        }
    </script>
</head>
<body scroll="no">
<form action="/averagecount.html" onsubmit="OnClose(check)">
<div style="width:200px; left:40px; top:40px; position:absolute; height:55px">
    <div  style="width:208px; left:50px; top:0px; position:absolute; height:24px">
        <font class="big1_text">输入读取文件路径:</font>
    </div>
    <div style="width:auto; left:50px; top:25px; position:absolute; height:20px">
        <input type="text" name ="path1" size="12" maxlength="200"/>
    </div>
    <div  style="width:208px; left:50px; top:50px; position:absolute; height:24px">
        <font class="big1_text">输入结果输出路径:</font>
    </div>
    <div style="width:auto; left:50px; top:75px; position:absolute; height:20px">
        <input type="text" name ="path2" size="12" maxlength="200"/>
    </div>

    <div style="width:60px; left:150px; top:100px; position:absolute; height:20px">
        <input type="submit" value="确认" onclick="OnClose(true)"/>
    </div>
    <div style="width:60px; left:60px; top:100px; position:absolute; height:20px">
        <input type="button" value="取消" onclick="OnClose(false)" />
    </div>
</div>
</form>
</body>
</html>

