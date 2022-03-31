package com.cloud.common.fileUpload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    /**
     * 上传文件
     * @param file
     * @return
     */
    String uploadMultipartFile(MultipartFile file) throws IOException;

    /**
     * 上传文件
     * @param bytes
     * @return
     */
    String uploadFile(byte[] bytes, long fileSize, String fileName);

    /**
     * 删除文件
     * @param fileUrl
     */
    void deleteFile(String fileUrl);

    /**
     * 下载文件
     * @param fileUrl
     * @return
     */
    byte[] downloadFile(String fileUrl) throws IOException;
}
