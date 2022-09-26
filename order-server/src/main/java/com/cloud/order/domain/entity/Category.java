package com.cloud.order.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cloud.order.enums.CategoryTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("category")
@AllArgsConstructor
@NoArgsConstructor
public class Category extends Model<Category> {

    private static final long serialVersionUID = 9072858780333387154L;

    @TableId(type = IdType.AUTO)
    private Long id;

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


    @TableField("category_type")
    private CategoryTypeEnum categoryType;

    @Version //乐观锁版本号配置
    @TableField(value = "version")
    private Integer version;

    /**
     * 租户ID--》多租户测试
     */
    @TableField("tenant_id")
    @JsonIgnore //不显示某个字段给前端
    private String tenantId;


    @TableLogic //标注为逻辑删除字段
    @JsonIgnore //不显示某个字段给前端
    private Boolean isDeleted;

}
