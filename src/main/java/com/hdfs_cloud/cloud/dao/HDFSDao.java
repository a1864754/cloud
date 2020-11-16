package com.hdfs_cloud.cloud.dao;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Repository
public class HDFSDao {
    static Configuration conf = new Configuration();
    static String hdfsPath = "hdfs://49.232.221.239:9000/user/cloud/user";

    public static void init() {
        try {
            conf.set("fs.defaultFS", "49.232.221.239:9000");
            conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        } catch (Exception e) {
            System.out.println("你似乎没有运行hadoop哦！！");
            e.printStackTrace();
        }
    }




//获取文件列表
    public static FileStatus[] ShowFiles(String username) throws Exception {
        init();
        String filePath = "/user/cloud/user/" + username;
       // FileStatus[] list1=new FileStatus;
            FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
            FileStatus[] list = fs.listStatus(new Path(filePath));
            fs.close();
            return list;

        //System.out.println(filelist);

    }

    // 根据路径删除文件
    public static void DeleteFile(String filename,String username) throws IOException {
        init();
//		Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        String filePath = "/user/cloud/user/" + username + "/" + filename;
        fs.deleteOnExit(new Path(filePath));
        fs.close();
    }

    // 创建用户目录
    public static void mkChildDir(String username) throws IOException {
        init();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        String upremote = "/user/cloud/user/" + username;
        Path a = new Path(upremote);
        fs.mkdirs(a);
        System.out.println("创建目录成功");
        fs.close();
    }


    //  根据用户和文件名判断文件是否存在
    public static boolean FileIfExist(String username, String filename) throws IOException {
        init();
        String fileName = "/user/cloud/user/" + username ; // 你的文件路径，没有就显示不存在
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        if (fs.exists(new Path(fileName))) {
            System.out.println("用户目录存在");
            return true;
        } else {
            System.out.println("用户目录不存在");
            return false;
        }
    }

    //下载
    public static void downloadFile(String filename, String username) throws Exception {
        if (StringUtils.isEmpty(filename) ) {
            return;
        }
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        // 上传路径
        String path1="/user/cloud/user/" + username+"/"+filename;
        Path HDFSPath = new Path(path1);
        // 目标路径
        Path localPath = new Path("D:\\Downloads");
        System.out.println(HDFSPath);

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fs.copyToLocalFile(false, HDFSPath,localPath);
        fs.close();
    }


}
