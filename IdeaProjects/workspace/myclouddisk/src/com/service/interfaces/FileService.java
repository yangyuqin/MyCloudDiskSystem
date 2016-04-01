package com.service.interfaces;

import org.apache.hadoop.fs.FileStatus;

import java.io.IOException;

/**
 * Created by yyq on 11/23/15.
 */
public interface FileService {
    public void uploadFile(String localPath, String remotePath) throws Exception;
    public void downloadFile(String remotePath, String localPath);
    public FileStatus[] listFiles(String path);
    public void deleteFile(String path);
    public boolean deleteLocalDirectory(String sPath) ;
    public boolean deleteLocalFile(String sPath);
    public boolean fileIsExists(String folder);
    public String getUsedCapacity(String path);
    public void createFolder(String path);
}
