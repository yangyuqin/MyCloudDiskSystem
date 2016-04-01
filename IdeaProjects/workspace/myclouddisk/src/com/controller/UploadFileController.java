package com.controller;

import com.cons.CommonConstant;
import com.entity.User;
import com.service.FileServiceImpl;
import com.service.interfaces.FileService;
import org.apache.hadoop.fs.FileStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by yyq on 11/20/15.
 */
@Controller
public class UploadFileController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UploadFileController.class);
    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping(value = "/uploadFile" ,method = RequestMethod.POST)
    public ModelAndView uploadFileMethod(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("forward:/mainForm.jsp");
        User user = getSessionUser(request);
        System.out.println("User Name:" + user.getUserName() + "----------");

        //建一个中转站
        long cur = System.currentTimeMillis();
        String tempPath = CommonConstant.tempPath + cur ;
        String localPath = tempPath + File.separator + file.getOriginalFilename();
        //目标路径

        String filePath1= request.getParameter("filePath");
        String remotePath = CommonConstant.HDFS + File.separator + user.getUserName();
        if (filePath1 != null && (filePath1.length()!=4) && (!filePath1.equals("null"))){
            System.out.println("===filePath1"+filePath1);
            remotePath = filePath1;
        }

        try {
            if (!file.isEmpty() && !(fileService.fileIsExists(remotePath + File.separator + file.getOriginalFilename()))) {
                File tempFile = new File(tempPath);
                if(!(tempFile.isDirectory())){
                    tempFile.mkdir();
                }
                file.transferTo(new File(localPath));
                fileService.uploadFile(localPath, remotePath);
                fileService.deleteLocalDirectory(tempPath);

                request.setAttribute("uploadInfo","文件上传成功");
                System.out.println("upload file to hadoop hdfs success!");
            }else if(file.isEmpty()){
                request.setAttribute("uploadInfo", "文件为空，请选择上传文件");
            }else if(fileService.fileIsExists(remotePath + File.separator + file.getOriginalFilename())){
                request.setAttribute("uploadInfo", "文件已存在，上传失败");
            }
            FileStatus[] list = fileService.listFiles(remotePath);
            String usedCapacity = fileService.getUsedCapacity(remotePath);
            request.setAttribute("usedCapacity",usedCapacity);
            request.setAttribute("list", list);
            request.setAttribute("userName",user.getUserName());
            request.setAttribute("filePath",remotePath);
            logger.error("DeleteFileController Success~~~");

        } catch (Exception e) {
            System.out.println("uploadFileController Fail~~~~");
            logger.error("DeleteFileController Fail~~");
        }
        return view;
    }
}
