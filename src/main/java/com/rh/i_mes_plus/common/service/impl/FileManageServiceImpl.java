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

    @Value("${nginxFilePath}")
    private String nginxFilePath;
    @Value("${nginxFilePathForDownload}")
    private String nginxFilePathForDownload;

    @Override
    public String uploadToNginxForOpen(MultipartFile file, String modelName)  {
        try {
            String path=modelName+ "/"+ IdUtil.simpleUUID() +"-"+file.getOriginalFilename();
            File test = new File(nginxFilePath+path);
            if (!test.exists()){
                test.mkdirs();
            }
            file.transferTo(test);
            InetAddress address = InetAddress.getLocalHost();
            String ip=address.getHostAddress();
            String prefix = "/"+StrUtil.subSuf(nginxFilePath,3);
            String finalPath="http://"+ip+prefix+path;
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
            File test = new File(nginxFilePathForDownload+path);
            if (!test.exists()){
                test.mkdirs();
            }
            file.transferTo(test);
            InetAddress address = InetAddress.getLocalHost();
            String ip=address.getHostAddress();
            String prefix = "/"+StrUtil.subSuf(nginxFilePathForDownload,3);
            String finalPath="http://"+ip+prefix+path;
            return finalPath;
        }catch (Exception e){
            log.error(file.getOriginalFilename()+"文件上传失败", e);
            return file.getOriginalFilename()+"文件上传失败";
        }
    }
}
