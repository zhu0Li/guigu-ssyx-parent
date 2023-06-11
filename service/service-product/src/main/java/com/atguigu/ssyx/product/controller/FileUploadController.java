package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.product.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：zhuo
 * @description：文件上传接口
 * @date ：2023/6/11 11:26
 */

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("admin/product")
@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    //文件上传
    @ApiOperation("图片上传")
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file){
        String url = fileUploadService.fileUpload(file);
        return Result.ok(url);
    }

}
