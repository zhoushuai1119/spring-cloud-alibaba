package com.cloud.common.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("user")
public class TokenUser extends Model<TokenUser> {

    @TableId
    private String id;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("salt")
    private String salt;

}
