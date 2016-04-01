package com.dao.interfaces;

import org.apache.hadoop.fs.FileStatus;

import java.io.IOException;

/**
 * Created by yyq on 11/23/15.
 */
public interface HDFSDao {
    //在根目录下创建文件夹
    void mkdirs(String folder) throws IOException;

    //判断需要建的文件或文件夹是否存在
    boolean isExsis(String folder) throws IOException;

    //某个文件夹的文件列表
    FileStatus[] ls(String folder) throws IOException;

    //复制文件或文件夹
    void copyFile(String local, String remote) throws IOException;

    //递归删除文件或文件夹
    void rmr(String folder) throws IOException;

    //下载文件或文件夹到本地系统
    void download(String remote, String local) throws IOException;

    //计算用户已使用的空间
    public long countUsedCapacity(String folder) throws IOException;
}
