package com.controller;

import com.cons.CommonConstant;
import com.dao.HDFSDaoImpl;
import com.entity.User;
import com.service.FileServiceImpl;
import com.util.ParentPath;
import org.apache.hadoop.fs.FileStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by yyq on 11/21/15.
 */
@Controller
public class DownloadFileController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(DownloadFileController.class);

    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public String downloadFile(@RequestParam("filePath") String filePath, HttpServletRequest request) throws Exception {
        User user = getSessionUser(request);
        System.out.println("User Name:" + user.getUserName() + "----------");
        System.out.println("====" + filePath + "====");
        try{
            fileService.downloadFile(filePath,CommonConstant.LOCALPATH);
            request.setAttribute("downloadFileInfo","文件下载成功");
        }catch (Exception e){
            System.out.println("DownLoadFileController Fail~~~");
            request.setAttribute("downloadFileInfo","文件下载失败");
        }
        //FileStatus[] list =  fileService.listFiles(CommonConstant.HDFS + File.separator + user.getUserName());
        FileStatus[] list = fileService.listFiles(ParentPath.getParentPath(filePath));
        String usedCapacity = fileService.getUsedCapacity(CommonConstant.HDFS + File.separator + user.getUserName());
        request.setAttribute("usedCapacity",usedCapacity);
        request.setAttribute("list", list);
        request.setAttribute("userName",user.getUserName());
        request.setAttribute("filePath",ParentPath.getParentPath(filePath));
        return "forward:/mainForm.jsp";
    }
}
