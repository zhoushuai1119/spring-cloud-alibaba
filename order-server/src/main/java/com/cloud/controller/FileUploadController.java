package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.service.order.IFileService;
import com.cloud.common.utils.CommonUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/7 16:27
 * @version: V1.0
 */
@RestController
@Slf4j
public class FileUploadController {

    @Autowired
    private IFileService fileService;

    /**
     * 文件上传
     */
    @ApiOperation(value="上传文件", notes="测试FastDFS文件上传")
    @RequestMapping(value = "/uploadFile",headers="content-type=multipart/form-data", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile (@RequestParam("file") MultipartFile file){
        String result ;
        try{
            String path = fileService.uploadFile(file) ;
            if (!StringUtils.isEmpty(path)){
                result = path ;
            } else {
                result = "上传失败" ;
            }
        } catch (Exception e){
            e.printStackTrace() ;
            result = "服务异常" ;
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 文件删除
     */
    @RequestMapping(value = "/deleteByPath", method = RequestMethod.GET)
    public ResponseEntity<String> deleteByPath (){
        String filePathName = "group1/M00/00/00/wKhIgl0n4AKABxQEABhlMYw_3Lo825.png" ;
        fileService.deleteFile(filePathName);
        return ResponseEntity.ok("SUCCESS") ;
    }

    @ApiOperation(value="上传文件", notes="上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件数据", required = true, paramType="query", dataType = "MultipartFile")
    })
    @PostMapping(value = "/upload")
    public BaseResponse<String> uploadFileClient(@RequestPart("file") MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        String path = fileService.uploadFile(bytes, file.getSize(), file.getOriginalFilename());
        return BaseResponse.createSuccessResult(path);
    }

    @ApiOperation(value="下载文件", notes="下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileUrl", value = "文件路径地址", required = true, paramType="query", dataType = "String"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", required = true, paramType="query", dataType = "String")
    })
    @PostMapping(value = "/download")
    public void downloadFile(@NotBlank(message = "文件路径地址不能为空") String fileUrl,
                             @NotBlank(message = "文件名称不能为空") String fileName,
                             HttpServletResponse response, HttpServletRequest request){
        try {
            byte[] fileBytes = fileService.downloadFile(fileUrl);
            downloadFile(request, response, fileBytes, fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @ApiOperation(value="将url转为文件流", notes="将url转为文件流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileUrl", value = "文件路径地址", required = true, paramType="query", dataType = "String"),
    })
    @GetMapping(value = "/downloadUrl")
    public void downloadUrl(@NotBlank(message = "文件路径地址不能为空") String fileUrl, HttpServletResponse response, HttpServletRequest request){
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
            byte[] fileBytes = fileService.downloadFile(fileUrl);
            downloadFile(request, response, fileBytes, fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 下载文件
     * @param response
     * @param fileData
     * @param fileName
     * @throws IOException
     */
    private void downloadFile(HttpServletRequest request, HttpServletResponse response, byte[] fileData, String fileName) throws IOException {
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (CommonUtil.isNotEmpty(userAgent) && (userAgent.contains("msie") || userAgent.contains("trident"))) {
            //IE
            fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "UTF-8");
        }
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");
        response.addHeader("Content-Length", "" + fileData.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(fileData);
        outputStream.flush();
        outputStream.close();
    }

}
