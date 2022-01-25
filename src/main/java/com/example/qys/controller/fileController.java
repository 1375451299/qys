package com.example.qys.controller;

import com.example.qys.config.GlobalResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 *  @author: Zhong Linfeng
 *  @Date: 2022/1/25 22:57
 *  @Description:
 */

@Api(tags = "文件")
@RestController
@RequestMapping("/file")
@CrossOrigin
public class fileController {


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
    public GlobalResult uploadFile() {

        GlobalResult result = GlobalResult.build(200, "初始化成功",null);
        return result;
    }

    @GetMapping()
    @ApiOperation(value = "下载文件")
    public GlobalResult downloadFile(@RequestParam int uuid) {

        GlobalResult result = GlobalResult.build(200, "初始化成功",null);
        return result;
    }

    @GetMapping("/msg")
    @ApiOperation(value = "获取文件元数据")
    public GlobalResult getFlileData() {

        GlobalResult result = GlobalResult.build(200, "初始化成功",null);
        return result;
    }
}
