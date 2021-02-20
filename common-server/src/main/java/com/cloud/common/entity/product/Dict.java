package com.cloud.common.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


@Data
@TableName("t_dict")
public class Dict extends Model<Dict> {

    @TableId
    private Long id;

    @TableField("CODE")
    private String code;

    @TableField("NAME")
    private String name;

    @TableField("LXJP")
    private String lxjp;

}