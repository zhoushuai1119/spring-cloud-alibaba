package com.cloud.common.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("category")
@AllArgsConstructor
@NoArgsConstructor
public class Category extends Model<Category> {

    @TableId(type = IdType.AUTO)
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

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDate updateTime;

}