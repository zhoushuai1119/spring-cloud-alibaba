package com.cloud.order.controller;

import com.cloud.order.domain.dto.MinIoUploadResDTO;
import com.cloud.order.utils.MinIoUtil;
import com.cloud.platform.common.domain.response.BaseResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/6/24 11:23
 * @version: v1
 */
@RestController
@RequestMapping("/minio")
public class MinIoController {

    @Resource
    private MinIoUtil minIoUtil;

    /**
     * 目录路径
     */
    private static final String MINIO_DIRECTORY = "img/test";

    /**
     * 文件上传
     */
    @ApiOperation(value = "上传文件", notes = "测试MinIO文件上传")
    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data", headers = "content-type=multipart/from-data")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "file")
    })
    public BaseResponse<MinIoUploadResDTO> uploadFile(@ApiParam(value = "文件", required = true)
                                                      @RequestPart("file") MultipartFile file) throws Exception {
        MinIoUploadResDTO path = minIoUtil.upload(file, MINIO_DIRECTORY);
        return BaseResponse.createSuccessResult(path);
    }


    @GetMapping("/download")
    public void download(@RequestParam("minFileName") String minFileName, HttpServletResponse response) {
        minIoUtil.download(response, minFileName);
    }

}
