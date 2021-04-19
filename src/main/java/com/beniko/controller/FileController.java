package com.beniko.controller;

import com.beniko.common.lang.Result;
import com.beniko.util.FileUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.url-prefix}")
    private String urlPrefix;

    @Resource
    HttpServletRequest request;

    @RequiresAuthentication
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();

        try {
            String url = FileUtil.upload(file, uploadPath, fileName);
            return Result.success(urlPrefix + url);
        } catch (Exception e) {
            return Result.fail("上传失败");
        }
    }
}
