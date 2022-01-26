package com.example.qys.utils;


import com.example.qys.dao.DocMapper;
import com.example.qys.entity.Doc;
import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Component
public class FtpUtil {

    @Autowired
    DocMapper docMapper;
    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "192.168.0.106";
    //端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String FTP_USERNAME = "user";
    //密码
    private static final String FTP_PASSWORD = "123456";
    //路径都是/home/加上用户名
    public final String FTP_BASEPATH = "/home/ftp-docs";
    //本地下载路径
    public final String LOCALPATH = "C:\\Users\\13754\\Desktop\\";
    //参数传过来了文件和文件的输入流
    public boolean uploadFile(String originFileName, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();//这是最开始引入的依赖里的方法
        ftp.setControlEncoding("utf-8");
        String tempPath="";
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();//连接成功会的到一个返回状态码
            System.out.println(reply);//可以输出看一下是否连接成功
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);//设置文件类型

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String filePath="/"+df.format(new Date());
            ftp.changeWorkingDirectory(FTP_BASEPATH+filePath);//修改操作空间
            if(!ftp.changeWorkingDirectory(FTP_BASEPATH+filePath)){
                             //9、截取filePath:2019/09/02-->String[]:2019,09,02
                            String[] dirs=filePath.split("/");
                             //10、把basePath(/home/ftp/www)-->tempPath
                             tempPath=FTP_BASEPATH;
                             for (String dir:dirs){
                    //11、循环数组(第一次循环-->2019)
                                if(null==dir||"".equals(dir))
                                         continue;//跳出本地循环，进入下一次循环
                                     //12、更换临时路径：/home/ftp/www/2019
                                     tempPath += "/" + dir;
                                     //13、再次检测路径是否存在(/home/ftp/www/2019)-->返回false，说明路径不存在
                                     if(!ftp.changeWorkingDirectory(tempPath)){
                                             //14、makeDirectory():创建目录  返回Boolean雷类型，成功返回true
                                             if(!ftp.makeDirectory(tempPath)){
                                                    return false;
                                                 }else {
                                                     //15、严谨判断（重新检测路径是否真的存在(检测是否创建成功)）
                                                     ftp.changeWorkingDirectory(tempPath);
                                                }
                                         }
                                 }
                         }
//对了这里说明一下你所操作的文件夹必须要有可读权限，chomd 777 文件夹名//这里我就是用的我的home文件夹
            ftp.storeFile(originFileName, input);//这里开始上传文件
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.out.println("连接失败");
                return success;
            }
            System.out.println("连接成功！");

            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    public boolean downloadFile(String uuid) {
        boolean flag = false;
        FTPClient ftp = new FTPClient();
        OutputStream os = null;

        try {
            System.out.println("开始下载文件");
            Doc doc=docMapper.selectByPrimaryKey(uuid);
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            ftp.changeWorkingDirectory(doc.getAddress());
            int reply = ftp.getReplyCode();//得到连接成功的返回状态码
            System.out.println(reply);
            ftp.enterLocalActiveMode();//主动，一定要加上这几句设置为主动
//下面是将这个文件夹的所有文件都取出来放在ftpFiles这个文件数组里面
            FTPFile[] ftpFiles = ftp.listFiles();

            String filename=uuid+doc.getType();

//然后便利这个数组找出和我们要下载的文件的文件名一样的文件
            for (FTPFile file : ftpFiles) {
                byte[] bytes = file.getName().getBytes("ISO-8859-1");
                file.setName(new String(bytes, "utf-8"));
                System.out.println("name: " + file.getName());//
                if (filename.equalsIgnoreCase(file.getName())) {//判断找到所下载的文件，file.getName就是服务器上对应的文件

                    //设置下载路径
                    File localFile = new File(LOCALPATH+doc.getInitialName());
                    os = new FileOutputStream(localFile);//得到文件的输出流
                    ftp.retrieveFile(file.getName(), os);//开始下载文件
                    os.close();
                }
            }
            ftp.logout();
            flag = true;
            System.out.println("下载文件成功");
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


}