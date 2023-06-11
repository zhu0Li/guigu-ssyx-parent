package com.atguigu.ssyx.product.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/11 11:28
 */
public interface FileUploadService {
    String fileUpload(MultipartFile file);
}
