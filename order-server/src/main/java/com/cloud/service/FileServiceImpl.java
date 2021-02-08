package com.cloud.service;

import com.cloud.common.service.order.IFileService;
import com.cloud.common.utils.LogUtil;
import com.cloud.config.fastdfs.FastDFSClient;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FastDFSClient client;

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        return client.uploadFile(file);
    }

    /**
     * 上传文件
     * @param bytes
     * @return
     */
    @Override
    public String uploadFile(byte[] bytes, long fileSize, String fileName) {
        InputStream inputStream = null;
        try {
            //拿到上传文件的二进制数组inputStream
            inputStream = new ByteArrayInputStream(bytes);
            //图片缩率图地址
            //String thumbPath = client.uploadAndCreateThum(inputStream, fileSize, fileName);
            String path = client.uploadFile(inputStream, fileSize, fileName);
            return path;
        } catch (Exception e) {
            LogUtil.logger(e.getMessage(), LogUtil.ERROR_LEVEL, e);
            return null;
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     * @param fileUrl
     */
    @Override
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            LogUtil.logger("fileUrl == >>文件路径为空...",LogUtil.INFO_LEVEL,null);
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            LogUtil.logger("文件删除失败:"+e.getMessage(),LogUtil.ERROR_LEVEL,e);
        }
    }

    /**
     * 下载文件
     * @param fileUrl
     * @return
     */
    @Override
    public byte[] downloadFile(String fileUrl) {
        return client.downloadFile(fileUrl);
    }

}
