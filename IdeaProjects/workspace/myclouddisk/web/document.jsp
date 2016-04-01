<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="head.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page session="true" language="java" %>
<%@page import="org.apache.hadoop.fs.FileStatus" %>
<%@ page import="com.util.CapaticyUnitsUtil" %>
<%@ page import="com.service.FileServiceImpl" %>


<body style="text-align:center;margin-bottom:100px;">
<div class="navbar">
    <div class="navbar-inner">
        <a class="brand" href="#" style="margin-left:200px;">小象网盘</a>
        <ul class="nav">
            <li class="active"><a href="/mainAction.html">首页</a></li>
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
        </ul>
        <ul style="float:left;padding-left: 150px;padding-top:8px">
            当前用户：${userName}
        </ul>
        <ul style="float:left;padding-left: 50px;padding-top:8px">
            已使用容量：${usedCapacity}
        </ul>
    </div>
</div>

<div style="margin:0px auto; text-align:left;width:1200px; height:50px;">
        <form class="form-inline" method="post" action="/uploadFileInDoc.html" enctype="multipart/form-data">
            <div style="padding-top:7px;line-height:50px;float:left;">
                <input type="submit" name="submit" value="上   传"/>
            </div>
            <div style=" padding-top:0px;line-height:50px;float:left;">
                <input type="file" name="file" size="30"/>
            </div>
            <div style="line-height: 10px;float: right">
                <input style="color: #cccccc; border-color:#cccccc;" type="text" name="filePath"
                       value="<%=(String)request.getAttribute("filePath")%>"/>
            </div>
        </form>
</div>

<div style="text-align:left;margin:0px auto;width:1200px; height:50px;">
    <form method="post" action="/createFolder.html" onsubmit="ShowInputBox()">
        <input type="submit" name="submit" value="新建文件夹"/>
        <input type="text" name="folderName" id="folderName"/>
        <input type="button" name="btncount1" value="日志文件统计" style="float: right;margin-right: 30px" onclick="averageCountTest()"/>
        <input type="button" name="btncount2" value="文件单词统计" style="float: right;margin-right: 30px" onclick="wordCountTest()"/>
        <input type="button" name="btncount3" value="文件类型统计" style="float: right;margin-right: 30px" onclick="SayHello()"/>
    </form>
</div>

<div style="margin:0px auto; width:1200px;height:500px; background:#fff">
    <table class="table table-hover" style="width:1000px;margin-left:100px;">
        <tr style=" border-bottom:2px solid #ddd">
            <td>文件名</td>
            <td style="width:100px">类型</td>
            <td style="width:100px;">大小</td>
            <td style="width:100px;">操作</td>
            <td style="width:100px;">操作</td>
        </tr>
        <%
            FileServiceImpl fileService = new FileServiceImpl();
            FileStatus[] list = (FileStatus[]) request.getAttribute("list");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
        %>
        <tr style="border-bottom:1px solid #eee">
            <%
                if (list[i].isDir()) {
                    out.print("<td> <a href=\"document.html?filePath=" + list[i].getPath() + "\">" + list[i].getPath().getName() + "</a></td>");
                } else {
                    out.print("<td>" + list[i].getPath().getName() + "</td>");
                }
            %>
            <td><%= (list[i].isDir() ? "目录" : "文件") %>
            </td>
            <td><%out.print(fileService.getUsedCapacity(list[i].getPath().toString()));%>
            </td>
            <td>
                <a href="/deleteFile.html?filePath=<%=(list[i].getPath().toString()) %>">删除</a>
            </td>
            <td>
                <a href="/downloadFile.html?filePath=<%=(list[i].getPath().toString()) %>">下载</a>
            </td>
        </tr>
        <%
                }
            }

        %>
    </table>
    <%
        String uploadInfo = (String) request.getAttribute("uploadInfo");
        if (uploadInfo != null) {
    %>
    <script type="text/javascript" language="javascript">
        alert("<%=uploadInfo%>")
    </script>
    <%
        }
    %>

    <%
        String deleteFileInfo = (String) request.getAttribute("deleteFileInfo");
        String downloadFileInfo = (String) request.getAttribute("downloadFileInfo");
        if (deleteFileInfo != null) {
    %>
    <script type="text/javascript" language="javascript">
        alert("<%=deleteFileInfo%>")
    </script>
    <%
        }
        if (downloadFileInfo != null) {
    %>
    <script type="text/javascript" language="javascript">
        alert("<%=downloadFileInfo%>")
    </script>
    <%
        }
    %>
</div>

<script language="JavaScript">

    function averageCountTest(){
        var p1 = "hdfs://localhost:9000/user/yyq/filesSpace/gao123/input01";
        var p2 = "hdfs://localhost:9000/user/yyq/filesSpace/gao123/output01";

        alert("Success")
    }

    function wordCountTest(){

    }

    function ShowInputBox() {
        var fn = window.showModalDialog("inputbox.jsp", "box", "dialogHeight: 200px; dialogWidth:400px; edge: Sunken; center: Yes; resizable: No; status: No;");
        if (fn) {
            document.getElementById("folderName").value = fn;
            return true;
        }
        return false;
    }

    function converFileSize(size) {
        kb = 1024;
        mb = kb * 1024;
        gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", size / gb);
        } else if (size >= mb) {
            f = size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            f = size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }
</script>
</body>