package com.rh.i_mes_plus.common.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.rh.i_mes_plus.common.service.FileManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.InetAddress;

@Slf4j
@Service
public class FileManageServiceImpl implements FileManageService {

    @Value("${nginx.filePath}")
    private String filePath;
    @Value("${nginx.filePathForDownload}")
    private String filePathForDownload;
    @Value("${nginx.ipAndPortAndFilePrefix}")
    private String ipAndPortAndFilePrefix;

    @Override
    public String uploadToNginxForOpen(MultipartFile file, String modelName)  {
        try {
            String path=modelName+ "/"+ IdUtil.simpleUUID() +"-"+file.getOriginalFilename();
            File test = new File(filePath+path);
            if (!test.exists()){
                test.mkdirs();
            }
            file.transferTo(test);
            String finalPath="http://"+ipAndPortAndFilePrefix+path;
            log.info("上传直接打开文件{}",finalPath);
            return finalPath;
        }catch (Exception e){
            log.error(file.getOriginalFilename()+"文件上传失败", e);
            return file.getOriginalFilename()+"文件上传失败";
        }
    }

    @Override
    public String uploadToNginxForDownload(MultipartFile file, String modelName) {
        try {
            String path=modelName+ "/"+ IdUtil.simpleUUID() +"-"+file.getOriginalFilename();
            File test = new File(filePathForDownload+path);
            if (!test.exists()){
                test.mkdirs();
            }
            file.transferTo(test);
            String finalPath="http://"+ipAndPortAndFilePrefix+path;
            log.info("上传可下载文件{}",finalPath);
            return finalPath;
        }catch (Exception e){
            log.error(file.getOriginalFilename()+"文件上传失败", e);
            return file.getOriginalFilename()+"文件上传失败";
        }
    }
}
