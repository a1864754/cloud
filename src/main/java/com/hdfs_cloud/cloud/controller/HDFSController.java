package com.hdfs_cloud.cloud.controller;

import com.hdfs_cloud.cloud.dao.Upload;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;
import com.hdfs_cloud.cloud.dao.HDFSDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HDFSController implements HandlerInterceptor {
    //查看指定路径下的所有文件
//    1.拿到数据
//    2.放在请求域中共享
//    3.返回页面
    @RequestMapping(value = "/hdfslist")
    public String list(Model model, HttpServletRequest request) throws Exception {
        Object user = request.getSession().getAttribute("loginUser");
        // Collection<Employee> employees = employeeDao.getAll();
        try {
            FileStatus[] file_list = HDFSDao.ShowFiles((String) user);

        List filelist = new ArrayList();
        for (FileStatus f : file_list) {
            Path list_all = f.getPath();
            //System.out.println(list_all.getName());
            filelist.add(list_all.getName().toString());
        }
        //放在请求域中共享
        model.addAttribute("hdfslist", filelist);
        //System.out.println(employees);
        return "list";
    }catch (Exception e){
            return "list";
        }
    }



//    @PostMapping(value = "/FileUp")
//    public String updata(@RequestParam("file")MultipartFile uploadFile,HttpServletRequest request){
//        Object user = request.getSession().getAttribute("loginUser");
//        String a = HDFSDao.updata(uploadFile,user.toString());
//        return "hdfsweb";
//    }
    //上传文件
    @PostMapping("/FileUp")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException {
        Object user = request.getSession().getAttribute("loginUser");
        String username = (String)user;
        String filename = file.getName();
        Boolean i = HDFSDao.FileIfExist(username,filename);
        if (i){


        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();

                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(originalFilename)
                        )
                );

                out.write(file.getBytes());

                out.flush();
                out.close();

                String destFileName = "/user/cloud/user/" + user+"/";
                System.out.println(destFileName);

                System.out.println(originalFilename);
                Upload.main(new String[]{originalFilename, destFileName});

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败，" + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败, " + e.getMessage();
            }


            return "上传成功";

        } else {
            return "上传失败。文件为空。";
        }}else {
            HDFSDao.mkChildDir(username);}
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();

                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(originalFilename)
                        )
                );

                out.write(file.getBytes());

                out.flush();
                out.close();

                String destFileName = "/user/cloud/user/" + user+"/";
                System.out.println(destFileName);

                System.out.println(originalFilename);
                Upload.main(new String[]{originalFilename, destFileName});

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败，" + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传成功, " + e.getMessage();
            }


            return "上传成功";

        } else {
            return "上传失败。文件为空。";
        }}


    //删除文件
    @PostMapping(value = "/FileDelet/{filename}")
    public String updata(@PathVariable("filename") String filename,HttpServletRequest request) throws IOException {
        Object user = request.getSession().getAttribute("loginUser");
        String username = (String)user;
        HDFSDao.DeleteFile(filename,username);
        return "list";
    }





    //下载文件
    @RequestMapping("/download/{filename}")
    //@PostMapping("/download/{filename}")
    @ResponseBody
    public void downloadFile(@PathVariable("filename") String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object user = request.getSession().getAttribute("loginUser");

        String username = (String)user;
        HDFSDao.downloadFile(path,username);
        String downloadFilePath = "/cloud/data/";//被下载的文件在服务器中的路径,
        String fileName = path;//被下载文件的名称
        File file = new File(downloadFilePath+fileName);
        System.out.println("file:"+file);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


       

    }



















