package com.cloud.order.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cloud.order.enums.CategoryTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@TableName("category")
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "idx_category")
public class Category extends Model<Category> {

    private static final long serialVersionUID = 9072858780333387154L;

    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Keyword)
    private Long id;

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


    @TableField("category_type")
    @Field(type = FieldType.Integer)
    private CategoryTypeEnum categoryType;

    @Version //乐观锁版本号配置
    @TableField(value = "version")
    @Field(type = FieldType.Integer)
    private Integer version;

    /**
     * 租户ID--》多租户测试
     */
    @TableField("tenant_id")
    @JsonIgnore //不显示某个字段给前端
    @Field(type = FieldType.Keyword)
    private String tenantId;


    @TableLogic //标注为逻辑删除字段
    @JsonIgnore //不显示某个字段给前端
    @Field(type = FieldType.Integer)
    private Boolean isDeleted;

}