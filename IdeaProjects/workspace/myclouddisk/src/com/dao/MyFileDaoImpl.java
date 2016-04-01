package com.dao;

import com.dao.interfaces.MyFileDao;
import com.jcraft.jsch.UserInfo;

import java.io.File;
import java.util.List;

/**
 * Created by yyq on 11/23/15.
 */
public class MyFileDaoImpl implements MyFileDao {
    @Override
    public void save(File file) {

    }

    @Override
    public void deleteFiles(UserInfo userInfo, String path) {

    }

    @Override
    public void delete(UserInfo userInfo, String file) {

    }

    @Override
    public long getUserDiskSize(String username) {
        return 0;
    }

    @Override
    public List<File> getFiles(String username, String path) {
        return null;
    }
}
