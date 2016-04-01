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
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by yyq on 11/25/15.
 */
@Controller
public class CreateFolderController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UploadFileController.class);
    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping(value = "/createFolder", method = RequestMethod.POST )
    public ModelAndView createFolerMethod(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        view.setViewName("forward:/mainForm.jsp");
        User user = getSessionUser(request);
        System.out.println("User Name:" + user.getUserName() + "----------");

        String newFoler = request.getParameter("folderName");
        String filePath1=request.getParameter("filePath");

        String remotePath = CommonConstant.HDFS + File.separator + user.getUserName();
        if (filePath1 != null && (filePath1.length()!=4) && (!filePath1.equals("null"))){
            remotePath = filePath1;
            view.setViewName("forward:/document.jsp");
        }
        if (newFoler != null){
            fileService.createFolder(remotePath + File.separator + newFoler);
            request.setAttribute("createInfo","文件夹创建成功");
            System.out.println("createFoler success!");
        }
        FileStatus[] list = fileService.listFiles(remotePath);
        String usedCapacity = fileService.getUsedCapacity(CommonConstant.HDFS + File.separator + user.getUserName());
        request.setAttribute("usedCapacity",usedCapacity);
        request.setAttribute("list", list);
        request.setAttribute("userName",user.getUserName());
        request.setAttribute("filePath",filePath1);
        return view;
    }

}
