package com.rh.i_mes_plus.common.controller;

import com.rh.i_mes_plus.common.service.FileManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@Api(tags = "文件服务")
public class FileController {
    @Autowired
    private FileManageService fileManageService;

    @ApiOperation(value = "上传文件以便打开")
    @PostMapping("/uploadToNginxForOpen")
    public String uploadToNginxForOpen(@RequestParam("file") MultipartFile file,String modelName) {
        if (file==null){
            return "文件上传失败，请重新选择文件";
        }
        return fileManageService.uploadToNginxForOpen(file,modelName);
    }
    @ApiOperation(value = "上传文件以便下载")
    @PostMapping("/uploadToNginxForDownload")
    public String uploadToNginxForDownload(@RequestParam("file") MultipartFile file,String modelName) {
        if (file==null){
            return "文件上传失败，请重新选择文件";
        }
        return fileManageService.uploadToNginxForDownload(file,modelName);
    }

}
