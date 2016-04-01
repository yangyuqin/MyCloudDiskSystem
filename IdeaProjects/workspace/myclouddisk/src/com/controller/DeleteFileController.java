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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by yyq on 11/21/15.
 */
@Controller
public class DeleteFileController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(DeleteFileController.class);

    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
    public ModelAndView deleteFile(@RequestParam("filePath") String filePath, HttpServletRequest request) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("forward:/mainForm.jsp");
        User user = getSessionUser(request);
        System.out.println("User Name:" + user.getUserName() + "----------");
        System.out.println("====" + filePath + "====");

        try{
            fileService.deleteFile(filePath);
            request.setAttribute("deleteFileInfo","成功删除文件");
            logger.info("DeleteFileController Success~~~");
        }catch (Exception e){
            System.out.println("DeleteFileController Fail~~~");
            logger.error("DeleteFileController Fail~~~");
            request.setAttribute("deleteFileInfo","删除文件失败");
        }
        //FileStatus[] list = fileService.listFiles(CommonConstant.HDFS + File.separator + user.getUserName());
        FileStatus[] list = fileService.listFiles(ParentPath.getParentPath(filePath));
        String usedCapacity = fileService.getUsedCapacity(CommonConstant.HDFS + File.separator + user.getUserName());
        request.setAttribute("usedCapacity",usedCapacity);
        request.setAttribute("list", list);
        request.setAttribute("userName",user.getUserName());
        request.setAttribute("filePath",ParentPath.getParentPath(filePath));
        return view;
    }
}
