package com.controller;

import com.cons.CommonConstant;
import com.entity.User;
import com.service.FileServiceImpl;
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
 * Servlet implementation class DocumentServlet
 */
@Controller
public class DocumentController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping(value = "/document", method = RequestMethod.GET)
    public String document(@RequestParam("filePath") String filePath, HttpServletRequest request) throws Exception {
        User user = getSessionUser(request);
        System.out.println("User Name:" + user.getUserName() + "----------");
        System.out.println("====" + filePath + "====");

        try{
            FileStatus[] list = fileService.listFiles(filePath);
            String usedCapacity = fileService.getUsedCapacity(CommonConstant.HDFS + File.separator + user.getUserName());
            request.setAttribute("filePath",filePath);
            request.setAttribute("userName",user.getUserName());
            request.setAttribute("usedCapacity",usedCapacity);
            request.setAttribute("list", list);
        }catch (Exception e){
            System.out.println("DocumentServlet Fail~~~");
            logger.error("DocumentServlet Fail~~~");
        }
        return "document";
    }
}
