package com.rh.i_mes_plus.common.controller;

import com.rh.i_mes_plus.util.MinIoUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 测试MinIO文件系统
 * */
@Slf4j
@CrossOrigin
@Api(tags = "测试minIO存储")
@RestController
@RequestMapping("/test")
public class TestMinIOController {

    @PostMapping("/createBucket")
    public void createBucket(String bucketName)  {
        MinIoUtil.createBucket(bucketName);
    }

    @PostMapping("/upload")
    public Object upload (String bucketName,@RequestParam("file") MultipartFile multipartFile)  {
        return MinIoUtil.upload(bucketName,multipartFile);
    }
    @PostMapping("/download")
    public void download (String bucketName,String fileName, HttpServletResponse response)  {
        MinIoUtil.download(bucketName,fileName,response);
    }
    @PostMapping("/deleteFile")
    public void deleteFile (String bucketName,String fileName)  {
        MinIoUtil.deleteFile(bucketName,fileName);
    }
}

