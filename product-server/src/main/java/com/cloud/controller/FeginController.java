package com.cloud.controller;

import com.cloud.entity.Product;
import com.cloud.service.DictService;
import com.cloud.service.NettySendServiceImpl;
import com.cloud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ Author： Zhou Shuai
 * @ Date： 14:13 2018/12/28
 * @ Description：
 * @ Version:  01
 */
@RestController
public class FeginController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private ProductService userTestService;

    @Autowired
    private DictService dictService;

    @Autowired
    private NettySendServiceImpl sendService;


    @GetMapping("/getSession")
    public String getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println("访问端口:" + port + "sessionId" + session.getId());
        return port + "****" + session.getId();
    }

    @GetMapping("/saveProduct")
    public void saveProduct() {
        userTestService.saveProduct();
    }

    @GetMapping("/getProduct")
    public Product getProduct(Long productId) {
        return userTestService.getProduct(productId);
    }

    @GetMapping("/saveDict")
    public void saveDict() {
        dictService.saveDict();
    }

    @PostMapping("/send")
    public void sendMessage(String content) {
        dictService.sendMessage(content);
    }

}
