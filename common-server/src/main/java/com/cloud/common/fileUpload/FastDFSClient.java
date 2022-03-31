package com.cloud.common.fileUpload;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class FastDFSClient {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadMultipartFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return storePath.getFullPath();
    }

    /**
     * 上传文件
     *
     * @param inputStream 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(InputStream inputStream, long fileSize, String fileName) {
        StorePath storePath = storageClient.uploadFile(inputStream, fileSize,
                FilenameUtils.getExtension(fileName), null);
        return storePath.getFullPath();
    }

    /**
     * 上传文件-获取缩率图
     *
     * @param inputStream 文件对象
     * @return 文件访问地址
     */
    public String uploadAndCreateThum(InputStream inputStream, long fileSize, String fileName) {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, fileSize,
                FilenameUtils.getExtension(fileName), null);
        //获取原图片地址
        String fullPath = storePath.getFullPath();
        //获取图片缩率图地址
        String thumbPath = thumbImageConfig.getThumbImagePath(fullPath);
        return thumbPath;
    }

    /**
     * 删除文件
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            log.info("fileUrl == >>文件路径为空...");
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            log.error("文件删除失败:"+e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param fileUrl  不带前缀
     * @return
     * @throws IOException
     */
    public byte[] downloadFile(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = storageClient.downloadFile(group, path, downloadByteArray);
        return bytes;
    }

}
