package com.dao.interfaces;

import com.jcraft.jsch.UserInfo;

import java.io.File;
import java.util.List;

/**
 * Created by yyq on 11/23/15.
 */
public interface MyFileDao {
    public void save(File file);
    public void deleteFiles(UserInfo userInfo, String path);
    public void delete(UserInfo userInfo, String file);
    public long getUserDiskSize(String username);
    public List<File> getFiles(String username, String path);
}
