package com.cloud.common.entity.product;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_product")
public class Product extends Model<Product> {

    @TableId
    private Long id;

    @TableField("PRODUCT_NAME")
    private String productName;

    @TableField("PRICE")
    private BigDecimal price;

    @TableField("PRODUCT_TYPE")
    private Integer productType;

    @TableField("CREATE_TIME")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}