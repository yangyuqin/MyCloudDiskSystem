package com.dao;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import com.cons.CommonConstant;
import com.dao.interfaces.HDFSDao;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.springframework.stereotype.Repository;

/**
 * Created by yyq on 11/20/15.
 */
@Repository
public class HDFSDaoImpl implements HDFSDao {
    //hdfs路径
    private String hdfsPath;
    //Hadoop系统配置
    private Configuration conf;

    public HDFSDaoImpl() {
        System.out.println("HDFSDao");
    }

    public HDFSDaoImpl(Configuration conf) {
        this(CommonConstant.HDFS, conf);
    }

    public HDFSDaoImpl(String hdfs, Configuration conf) {
        this.hdfsPath = hdfs;
        this.conf = conf;
    }

    //加载Hadoop配置文件
    public  static JobConf setConfig(){
        JobConf conf = new JobConf(HDFSDaoImpl.class);
        conf.setJobName("HDFSDao");
        conf.addResource("classpath:/hadoop/hadoop-1.0.3/conf/core-site.xml");
        conf.addResource("classpath:/hadoop/hadoop-1.0.3/conf/hdfs-site.xml");
        conf.addResource("classpath:/hadoop/hadoop-1.0.3/conf/mapred-site.xml");
        return conf;
    }

    //在根目录下创建文件夹
    @Override
    public void mkdirs(String folder) throws IOException {
        Path path = new Path(folder);
        System.out.println(URI.create(hdfsPath));
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        if (!fs.exists(path)) {
            fs.mkdirs(path);
            System.out.println("Create: " + folder);
        }else{
            System.out.println(path+" file is Exists");
        }
        fs.close();
    }

    //判断需要建的文件或文件夹是否存在
    @Override
    public boolean isExsis(String folder) throws IOException{
        Path path = new Path(folder);
        System.out.println(URI.create(hdfsPath));
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        if (!fs.exists(path)) {
            fs.close();
            return false;
        }else{
            fs.close();
            return true;
        }
    }

    //某个文件夹的文件列表
    @Override
    public FileStatus[] ls(String folder) throws IOException {
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        FileStatus[] list = fs.listStatus(path);
        System.out.println("ls: " + folder);
        System.out.println("==========================================================");
        if(list != null){
            for (FileStatus f : list) {
                System.out.printf("%s, folder: %s, 大小: %d\n", f.getPath().getName(), (f.isDir()?"目录":"文件"), f.getLen());
            }
        }
        System.out.println("==========================================================");
        fs.close();
        return  list;
    }
    @Override
    public long countUsedCapacity(String folder) throws IOException {
        long usedCapacity = 0l;
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        FileStatus[] list = fs.listStatus(path);
        if (fs.exists(path)) {
            if(list != null){
                for (FileStatus f : list) {
                    if (f.isDir()){
                        usedCapacity += countUsedCapacity(folder+ File.separator+f.getPath().getName());
                    }else {
                        usedCapacity = usedCapacity + f.getLen();
                    }
                }
            }
        }
        System.out.println(folder+" usedCapacity : "+usedCapacity);
        fs.close();
        return usedCapacity;
    }

    //复制文件或文件夹
    @Override
    public void copyFile(String local, String remote) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.copyFromLocalFile(new Path(local), new Path(remote));
        System.out.println("copy from: " + local + " to " + remote);
        fs.close();
    }

    //递归删除文件或文件夹
    @Override
    public void rmr(String folder) throws IOException {
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.deleteOnExit(path);
        System.out.println("Delete: " + folder);
        fs.close();
    }

    //下载文件或文件夹到本地系统
    @Override
    public void download(String remote, String local) throws IOException {
        Path path = new Path(remote);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.copyToLocalFile(path, new Path(local));
        System.out.println("download: from " + remote + " to " + local);
        fs.close();
    }
}
