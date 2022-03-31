package com.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_user")
public class User implements Serializable {

    @TableId
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("salt")
    private String salt;

}