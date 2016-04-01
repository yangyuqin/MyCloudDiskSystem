package com.controller;

import com.cons.CommonConstant;
import com.entity.User;
import com.mr.AverageCount;
import com.mr.WordCount;
import com.service.FileServiceImpl;
import com.util.ParentPath;
import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by yyq on 11/27/15.
 */
@Controller
public class MRController extends BaseController {
    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping(value = "/averagecount", method = RequestMethod.GET)
    public String actest(HttpServletRequest request) throws Exception{
        User user = getSessionUser(request);
        System.out.println("User Name:" + user.getUserName() + "----------");

        String path01 = CommonConstant.HDFS + File.separator + user.getUserName() + File.separator +request.getParameter("path1");
        String path02 = CommonConstant.HDFS + File.separator + user.getUserName() + File.separator + request.getParameter("path2");

        if (path01 != null && path02 != null) {
            AverageCount.averagecountTest(path01, path02);
            System.out.println("AverageCount Success~~~");
        }
        FileStatus[] list = fileService.listFiles(ParentPath.getParentPath(path01));
        String usedCapacity = fileService.getUsedCapacity(CommonConstant.HDFS + File.separator + user.getUserName());
        request.setAttribute("usedCapacity",usedCapacity);
        request.setAttribute("list", list);
        request.setAttribute("userName",user.getUserName());
        request.setAttribute("filePath",ParentPath.getParentPath(path01));
        return "forward:/mainForm.jsp";
    }


    @RequestMapping(value = "/wordcount", method = RequestMethod.GET)
    public String wctest(HttpServletRequest request) throws Exception{
        User user = getSessionUser(request);
        System.out.println("User Name:" + user.getUserName() + "----------");

        String path01 = CommonConstant.HDFS + File.separator + user.getUserName() + File.separator +request.getParameter("path1");
        String path02 = CommonConstant.HDFS + File.separator + user.getUserName() + File.separator + request.getParameter("path2");

        if (path01 != null && path02 != null) {
            WordCount.wordcountTest(path01, path02);
            System.out.println("WordCount Success~~~");
        }
        FileStatus[] list = fileService.listFiles(ParentPath.getParentPath(path01));
        String usedCapacity = fileService.getUsedCapacity(CommonConstant.HDFS + File.separator + user.getUserName());
        request.setAttribute("usedCapacity",usedCapacity);
        request.setAttribute("list", list);
        request.setAttribute("userName",user.getUserName());
        request.setAttribute("filePath",ParentPath.getParentPath(path01));
        return "forward:/mainForm.jsp";
    }
}
