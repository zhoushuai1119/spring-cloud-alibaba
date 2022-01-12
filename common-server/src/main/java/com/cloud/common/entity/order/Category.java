package com.cloud.common.entity.order;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cloud.common.aop.annotation.elasticsearch.EsDocPrivateKey;
import com.cloud.common.aop.annotation.elasticsearch.EsIndex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("category")
@AllArgsConstructor
@NoArgsConstructor
@EsIndex("idx_category")
public class Category extends Model<Category> {

    @TableId(type = IdType.AUTO)
    @EsDocPrivateKey
    private Integer id;

    @TableField("category_id")
    private String categoryId;

    @TableField("category_name")
    private String categoryName;

    @TableField("category_level")
    private Integer categoryLevel;

    @TableField("parent_category_id")
    private String parentCategoryId;

    @TableField("parent_category_name")
    private String parentCategoryName;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}