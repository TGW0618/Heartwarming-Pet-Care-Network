package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件相关的接口
 * 提供文件上传等操作的RESTful API接口
 */
@RestController
@RequestMapping("/files")
public class FileController {

    // 文件上传的目录路径
    private static final String filePath = System.getProperty("user.dir") + "/files/";



    /**
     * 上传文件接口
     * 将上传的文件保存到服务器指定目录中，文件名使用时间戳+原始文件名的方式保证唯一性
     *
     * @param file 上传的文件对象，包含文件数据和元信息
     * @return Result 操作结果封装对象，成功时返回success，失败时返回error及错误信息
     */
    @PostMapping("/upload")
    @CrossOrigin(origins = "*") // 添加跨域支持
    public Result uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename(); // 获取原始文件名
        if (originalFilename == null || originalFilename.isEmpty()) {
            return Result.error(500, "文件名为空");
        }

        // 如果目录不存在，则创建
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 构造唯一文件名
        String fileName = System.currentTimeMillis() + "_" + originalFilename;// 生成当前时间戳 + 文件名
        File destFile = new File(directory, fileName);// 创建文件对象

        try {
            // 使用 transferTo 方法保存文件
            file.transferTo(destFile);
        } catch (IOException e) {
            return Result.error(500, "文件上传失败: " + e.getMessage());
        }
//        String url = "http://localhost:8083/files/" + fileName;
        String url = "http://192.168.1.12:8083/files/" + fileName;

        return Result.success(url);
    }
}
