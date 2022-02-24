package com.cloud.common.entity.order;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("category")
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "idx_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 9072858780333387154L;

    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Keyword)
    private Integer id;

    @TableField("category_id")
    @Field
    private String categoryId;

    @TableField("category_name")
    @Field(analyzer = "ik_smart")
    private String categoryName;

    @TableField("category_level")
    @Field(type = FieldType.Integer)
    private Integer categoryLevel;

    @TableField("parent_category_id")
    @Field
    private String parentCategoryId;

    @TableField("parent_category_name")
    @Field(analyzer = "ik_smart")
    private String parentCategoryName;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private LocalDateTime createTime;


    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private LocalDateTime updateTime;

}