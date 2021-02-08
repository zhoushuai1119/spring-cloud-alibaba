package com.cloud.config.fastdfs;

import com.cloud.common.utils.LogUtil;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FastDFSClient {

    @Value("${fdfs.urlPrefix}")
    private String urlPrefix;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 上传文件
     *
     * @param file
     *            文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return urlPrefix + storePath.getFullPath();
    }

    /**
     * 上传文件
     *
     * @param inputStream 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(InputStream inputStream, long fileSize, String fileName) throws IOException {
        StorePath storePath = storageClient.uploadFile(inputStream, fileSize,
                FilenameUtils.getExtension(fileName), null);
        return urlPrefix + storePath.getFullPath();
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
        return urlPrefix + thumbPath;
    }

    /**
     * 删除文件
     */
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
     * @throws IOException
     */
    public byte[] downloadFile(String fileUrl) {
        String subFileUrl = fileUrl.replace(urlPrefix, StringUtils.EMPTY);
        String group = subFileUrl.substring(0, subFileUrl.indexOf("/"));
        String path = subFileUrl.substring(subFileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = storageClient.downloadFile(group, path, downloadByteArray);
        return bytes;
    }
}
