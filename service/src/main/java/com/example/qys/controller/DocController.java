package com.example.qys.controller;

import com.example.qys.config.GlobalResult;
import com.example.qys.service.DocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *  @author: Zhong Linfeng
 *  @Date: 2022/1/25 22:57
 *  @Description:
 */

@Api(tags = "文件")
@RestController
@RequestMapping("/file")
@CrossOrigin
public class DocController {

    @Autowired
    private DocService docService;
    /**
    * @Description
    * @Author  Zhong Linfeng
    * @Date   2022/1/25 22:59
    * @Param  []
    * @Return      com.example.qys.config.GlobalResult
    * @Exception
    *
    */

    @PostMapping()
    @ApiOperation(value = "上传文件")
    public GlobalResult uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        return docService.uploadFile(file,request);
    }

    @GetMapping()
    @ApiOperation(value = "下载文件")
    public GlobalResult downloadFile(@RequestParam String uuid) throws IOException {
        return docService.downloadFile(uuid);
    }

    @GetMapping("/msg")
    @ApiOperation(value = "获取文件元数据")
    public GlobalResult getFlileData(@RequestParam String uuid) {

        GlobalResult result = GlobalResult.build(200, "初始化成功",null);
        return docService.getFlileData(uuid);
    }
}
