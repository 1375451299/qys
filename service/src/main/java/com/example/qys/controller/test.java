package com.example.qys.controller;




import com.example.qys.config.GlobalResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @author: Zhong Linfeng
 *  @Date: 2022/1/25 22:12
 *  @Description:
 */

@Api(tags = "测试",value = "/test")
@RestController
@RequestMapping("/test")
@CrossOrigin

public class test {

    @GetMapping("/initialize")
    @ApiOperation(value = "初始化", notes = "初始化交换机配置")
    public GlobalResult initialize() {

        GlobalResult result = GlobalResult.build(200, "初始化成功",null);
        return result;
    }

    @GetMapping("/initialize2")
    @ApiOperation(value = "初始化", notes = "初始化交换机配置")
    public GlobalResult initialize2() {

        GlobalResult result = GlobalResult.build(200, "初始化成功",null);
        return result;
    }
}
