package com.cloud.order.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.cloud.order.domain.dto.MinIoUploadResDTO;
import com.cloud.platform.web.utils.CommonUtil;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/6/24 11:18
 * @version: v1
 */
@Component
@Slf4j
public class MinIoUtil {

    @Resource
    private MinioClient minioClient;

    /**
     * bucketName
     */
    @Value("${spring.application.name:APP}")
    private String BUCKET_NAME;

    private static final String SEPARATOR_DOT = ".";

    /**
     * 文件分隔符
     */
    private static final String FILE_SEPARATOR = "/";

    /**
     * 默认过期时间 24小时 (单位: 秒)
     */
    private final static Integer DEFAULT_EXPIRY = 24 * 60 * 60;

    /***************************************操作存储桶*********************************************************/

    /**
     * @description: 判断 bucketName是否存在
     * @author: zhou shuai
     * @date: 2022/9/16 11:13
     * @return: boolean
     */
    @SneakyThrows
    public boolean bucketExists() {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
    }


    /**
     * @description: 创建 bucket
     * @author: zhou shuai
     * @date: 2022/9/16 11:18
     */
    @SneakyThrows
    public void createBucket() {
        if (!bucketExists()) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
        }
    }


    /**
     * @description: 获取全部bucket
     * @author: zhou shuai
     * @date: 2022/9/16 11:19
     * @return: java.util.List<io.minio.messages.Bucket>
     */
    @SneakyThrows
    private List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }


    /**
     * @description: 根据bucketName获取Bucket信息
     * @author: zhou shuai
     * @date: 2022/9/16 11:25
     * @param: bucketName
     * @return: java.util.Optional<io.minio.messages.Bucket>
     */
    public Optional<Bucket> getBucketDetail(String bucketName) {
        if (CommonUtil.isNotEmpty(getAllBuckets())) {
            return getAllBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
        }
        return Optional.empty();
    }

    /**
     * @description: 根据bucketName删除Bucket信息
     * @author: zhou shuai
     * @date: 2022/9/16 11:27
     * @param: bucketName
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /***************************************操作文件对象*********************************************************/

    /**
     * @description: 判断文件是否存在
     * @author: zhou shuai
     * @date: 2022/9/16 11:39
     * @param: objectName
     * @return: boolean
     */
    public boolean objectIsExist(String objectName) {
        boolean exist = true;
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }


    /**
     * @description: 获取文件流
     * @author: zhou shuai
     * @date: 2022/9/16 11:47
     * @param: objectName
     * @return: java.io.InputStream
     */
    @SneakyThrows
    public InputStream getObjectInputStream(String objectName) {
        return minioClient.getObject(GetObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
    }


    /**
     * @description: 获取对象文件名称列表
     * @author: zhou shuai
     * @date: 2022/9/16 13:49
     * @param: prefix  对象名称前缀(文件夹 /xx/xx/xxx.jpg 中的 /xx/xx/)
     * @return: java.util.List<java.lang.String>
     */
    public List<String> listObjectNames(String prefix) {
        List<Item> objectList = listObjectItems(prefix);
        List<String> objectNameList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objectList)) {
            objectNameList = objectList.stream().map(item -> item.objectName()).collect(Collectors.toList());
        }
        return objectNameList;
    }


    /**
     * @description: 获取对象文件名称列表
     * @author: zhou shuai
     * @date: 2022/9/16 11:52
     * @param: prefix 对象名称前缀(文件夹 /xx/xx/xxx.jpg 中的 /xx/xx/)
     * @param: sort 是否排序(升序)
     * @return: java.util.List<java.lang.String>
     */
    @SneakyThrows
    public List<Item> listObjectItems(String prefix) {
        boolean flag = bucketExists();
        List<Item> chunkPaths = new ArrayList<>();
        if (flag) {
            ListObjectsArgs.Builder builder = ListObjectsArgs.builder();
            builder.bucket(BUCKET_NAME).recursive(true);
            if (StringUtils.isNotBlank(prefix)) {
                builder.prefix(prefix);
            }
            ListObjectsArgs listObjectsArgs = builder.build();
            Iterable<Result<Item>> chunks = minioClient.listObjects(listObjectsArgs);
            if (chunks != null) {
                for (Result<Item> o : chunks) {
                    Item item = o.get();
                    chunkPaths.add(item);
                }
            }
            return chunkPaths;
        }
        return chunkPaths;
    }

    /**
     * @description: 创建文件夹或目录
     * @author: zhou shuai
     * @date: 2022/9/16 13:51
     * @param: dirPath  格式为 xxx/xxx/xxx/
     * @return: java.lang.String
     */
    @SneakyThrows
    public String createDirectory(String dirPath) {
        if (!this.bucketExists()) {
            return null;
        }
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(dirPath)
                .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                .build());
        return dirPath;
    }


    /**
     * @description: 上传本地文件
     * @author: zhou shuai
     * @date: 2022/9/16 13:56
     * @param: objectName 对象名称
     * @param: fileName 本地文件路径
     * @return: io.minio.ObjectWriteResponse
     */
    @SneakyThrows
    public ObjectWriteResponse putObject(String objectName, String fileName) {
        return minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .filename(fileName)
                        .build());
    }


    /**
     * @description: 获取文件信息, 如果抛出异常则说明文件不存在
     * @author: zhou shuai
     * @date: 2022/9/16 14:00
     * @param: bucketName
     * @param: objectName
     * @return: io.minio.StatObjectResponse
     */
    @SneakyThrows
    public StatObjectResponse statObject(String objectName) {
        return minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .build());
    }


    /**
     * @description: 删除文件
     * @author: zhou shuai
     * @date: 2022/9/16 14:02
     * @param: bucketName
     * @param: objectName
     * @return: boolean
     */
    @SneakyThrows
    public boolean removeObject(String objectName) {
        if (!bucketExists()) {
            return false;
        }
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .build());
        return true;
    }


    /**
     * @description: 删除指定桶的多个文件对象, 返回删除错误的对象列表，全部删除成功，返回空列表
     * @author: zhou shuai
     * @date: 2022/9/16 14:05
     * @param: bucketName
     * @param: objectNames
     * @return: java.util.List<java.lang.String>
     */
    @SneakyThrows
    public List<String> removeObjects(List<String> objectNames) {
        if (!bucketExists()) {
            return new ArrayList<>();
        }
        List<DeleteObject> deleteObjects = new ArrayList<>(objectNames.size());
        for (String objectName : objectNames) {
            deleteObjects.add(new DeleteObject(objectName));
        }
        List<String> deleteErrorNames = new ArrayList<>();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                RemoveObjectsArgs.builder()
                        .bucket(BUCKET_NAME)
                        .objects(deleteObjects)
                        .build());
        for (Result<DeleteError> result : results) {
            DeleteError error = result.get();
            deleteErrorNames.add(error.objectName());
        }
        return deleteErrorNames;
    }


    /***************************************操作Presigned*********************************************************/

    /**
     * @description: 获取访问对象的外链地址
     * @author: zhou shuai
     * @date: 2022/9/16 14:07
     * @param: bucketName
     * @param: objectName
     * @param: expiry 过期时间(秒)
     * @return: java.lang.String
     */
    @SneakyThrows
    public String getObjectUrl(String objectName, Integer expiry) {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .expiry(expiry)
                        .build()
        );
    }


    /**
     * @description: 创建上传文件对象的外链
     * @author: zhou shuai
     * @date: 2022/9/16 14:12
     * @param: bucketName
     * @param: objectName
     * @return: java.lang.String
     */
    public String createUploadUrl(String objectName) {
        return getObjectUrl(objectName, DEFAULT_EXPIRY);
    }


    /**
     * @description: 下载文件
     * @author: zhou shuai
     * @date: 2022/9/16 14:13
     * @param: response
     * @param: fileName
     */
    public void downLoadFile(HttpServletResponse response, String fileName) {
        if (objectIsExist(fileName)) {
            try (InputStream ism = new BufferedInputStream(getObjectInputStream(fileName))) {
                // 否则则代表对象存在
                byte buf[] = new byte[1024];
                int length = 0;
                response.reset();
                //Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
                // Content-disposition其实可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，
                // 文件直接在浏览器上显示或者在访问时弹出文件下载对话框。
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/x-msdownload");
                response.setCharacterEncoding("utf-8");
                OutputStream osm = new BufferedOutputStream(response.getOutputStream());
                while ((length = ism.read(buf)) > 0) {
                    osm.write(buf, 0, length);
                }
                osm.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * @description: 批量下载
     * @author: zhou shuai
     * @date: 2022/9/16 14:17
     * @param: bucket
     * @param: directory
     * @return: java.util.List<java.lang.String>
     */
    @SneakyThrows
    public List<String> downLoadMore(String directory) {
        Iterable<Result<Item>> objs = minioClient.listObjects(ListObjectsArgs.builder().bucket(BUCKET_NAME).prefix(directory).useUrlEncodingType(false).build());
        List<String> list = new ArrayList<>();
        for (Result<Item> result : objs) {
            String objectName = null;
            objectName = result.get().objectName();
            StatObjectResponse statObject = minioClient.statObject(StatObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
            if (statObject != null && statObject.size() > 0) {
                String fileurl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(BUCKET_NAME).object(statObject.object()).method(Method.GET).build());
                list.add(fileurl);
            }
        }
        return list;
    }


    /**
     * @description: 文件上传
     * @author: zhou shuai
     * @date: 2022/9/16 14:20
     * @param: multipartFile
     * @param: bucketName
     * @param: directory
     * @return: com.cloud.entity.MinIoUploadResDTO
     */
    public MinIoUploadResDTO upload(MultipartFile multipartFile, String directory) throws Exception {
        if (!this.bucketExists()) {
            this.createBucket();
        }
        String minFileName = minFileName(multipartFile.getOriginalFilename());
        InputStream inputStream = multipartFile.getInputStream();
        if (StringUtils.isNotBlank(directory)) {
            minFileName = directory + FILE_SEPARATOR + minFileName;
        }
        //上传文件到指定目录
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(minFileName)
                .contentType(multipartFile.getContentType())
                .stream(inputStream, inputStream.available(), -1)
                .build());
        inputStream.close();
        // 返回生成文件名、访问路径
        return new MinIoUploadResDTO(minFileName, createUploadUrl(minFileName));
    }

    /**
     * @description: 文件下载
     * @author: zhou shuai
     * @date: 2022/9/16 14:32
     * @param: response
     * @param: bucketName
     * @param: minFileName
     */
    @SneakyThrows
    public void download(HttpServletResponse response, String minFileName) {
        try (InputStream fileInputStream = getObjectInputStream(minFileName)) {
            response.setHeader("Content-Disposition", "attachment;filename=" + minFileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        }
    }


    /**
     * @description: 生成上传文件名
     * @author: zhou shuai
     * @date: 2022/9/16 14:27
     * @param: originalFileName 原始文件名称
     * @return: java.lang.String
     */
    private String minFileName(String originalFileName) {
        String suffix = originalFileName;
        if (originalFileName.contains(SEPARATOR_DOT)) {
            suffix = originalFileName.substring(originalFileName.lastIndexOf(SEPARATOR_DOT));
        }
        return UUID.randomUUID().toString().replace("-", "").toUpperCase() + suffix;
    }

}
