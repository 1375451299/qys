package com.example.qys.service;

import com.example.qys.config.GlobalResult;

import com.example.qys.dao.DocMapper;
import com.example.qys.entity.Doc;

import com.example.qys.utils.FtpUtil;
import com.example.qys.utils.UUIDGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DocService {

    @Autowired
    private DocMapper docMapper;
    @Autowired  private FtpUtil ftpUtil;
    @Autowired private UUIDGeneratorUtil generatorUtil;

    public GlobalResult uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        GlobalResult result;
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String time="/"+df.format(new Date());

        //获取文件名

        String initial_name=file.getOriginalFilename();
        //获取文件的后缀名
        String suffixName = initial_name.substring(initial_name.lastIndexOf("."));
        double size = file.getSize();
        String type = file.getContentType();
        Date c_time= new Date();
        String address=ftpUtil.getFTP_BASEPATH()+time;
        //使用时间戳、MAC地址生成唯一uuid
        String uuid= generatorUtil.generate();

        //文件重命名为uuid
        String fileName = uuid+suffixName;

        InputStream inputStream = file.getInputStream();

        //主要就是这里实现了ftp的文件上传
        Boolean flag = ftpUtil.uploadFile(fileName, inputStream);
        if (flag == true) {

            Doc doc = new Doc();
            doc.setAddress(address);
            doc.setCreateTime(c_time);
            doc.setInitialName(initial_name);
            doc.setSize(size);
            doc.setUuid(uuid);
            doc.setType(suffixName);
            docMapper.insert(doc);
            result=GlobalResult.build(200, "文件上传成功",uuid);
        }
        else{
            result=GlobalResult.build(500, "文件上传失败",null);
        }
        return result;
    }

    public GlobalResult downloadFile(String uuid) throws IOException {
        boolean flag ;
        GlobalResult result;
        //同样这里也是用ftpUtil这个工具类去下载，传入文件的存放地址和文件名，和文件在服务器上的地址
        flag = ftpUtil.downloadFile(uuid);
        if (flag==true){
            result=GlobalResult.build(200, "文件下载成功",null);
        }
        else {
            result=GlobalResult.build(410, "文件下载失败",null);
        }
        return result;
    }

    public GlobalResult getFlileData(String uuid){
        GlobalResult result;
        Doc doc=docMapper.selectByPrimaryKey(uuid);
        if (doc!=null){
            result=GlobalResult.build(200, "获取文件信息成功",doc);
        }
        else{
            result=GlobalResult.build(500, "获取文件信息失败",null);
        }

        return result;
    }
    }
