package com.service;

import com.cons.CommonConstant;
import com.dao.HDFSDaoImpl;
import com.service.interfaces.FileService;
import com.util.CapaticyUnitsUtil;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.mapred.JobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by yyq on 11/23/15.
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private HDFSDaoImpl hdfsDao;

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public void createFolder(String folder) {
        try {
            if (!hdfsDao.isExsis(folder)) {
                hdfsDao.mkdirs(folder);
            }
        } catch (Exception e) {
            System.out.println("createFolder Fail~~~");
        }
    }

    @Override
    public String getUsedCapacity(String path) {
        //???????
        JobConf jobConf = HDFSDaoImpl.setConfig();
        HDFSDaoImpl hdao = new HDFSDaoImpl(jobConf);

        String usedCapacity = null;
        if (path != null) {
            try {
                long pathsize = hdao.countUsedCapacity(path);
                usedCapacity = CapaticyUnitsUtil.convertFileSize(pathsize);
            } catch (Exception e) {
                System.out.println("getUsedCapacity Fail~~~");
            }
        }
        return usedCapacity;
    }

    @Override
    public void uploadFile(String localPath, String remotePath) {
        try {
            hdfsDao.copyFile(localPath, remotePath);
        } catch (Exception e) {
            System.out.println("Upload Fail~~~");
        }
    }

    @Override
    public void downloadFile(String remotePath, String localPath) {
        try {
            String[] str = remotePath.split("/");
            String fileName = str[str.length - 1];
            if (new File(localPath + File.separator + fileName).exists()) {
                String new_path = localPath + String.valueOf(System.currentTimeMillis());
                new File(new_path).mkdir();
                hdfsDao.download(remotePath, new_path);
                logger.info("下载成功，下载成功");
            } else {
                hdfsDao.download(remotePath, localPath);
                logger.info("下载成功，下载成功");
            }

        } catch (Exception e) {
            System.out.println("DownLoadFile Fail~~~");
            logger.error("下载出错，下载出错～～");
        }
    }

    @Override
    public FileStatus[] listFiles(String path) {
        FileStatus[] list = null;
        try {
            list = hdfsDao.ls(path);
        } catch (Exception e) {
            System.out.println("Ls File Fail~~~");
        }
        return list;
    }


    @Override
    public boolean fileIsExists(String folder) {
        boolean flag = false;
        try {
            flag = hdfsDao.isExsis(folder);
        } catch (Exception e) {
            System.out.println("FileIsExists~~~~");
        }
        return flag;
    }

    @Override
    public void deleteFile(String folder) {
        try {
            hdfsDao.rmr(folder);
        } catch (Exception e) {
            System.out.println("DeleteFile Fail~~~");

        }
    }

    /**
     * 删除本地目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteLocalDirectory(String sPath) {
        boolean flag = false;
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteLocalFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteLocalDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除本地单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteLocalFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
