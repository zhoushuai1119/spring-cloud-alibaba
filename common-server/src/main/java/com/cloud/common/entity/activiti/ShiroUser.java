package com.cloud.common.entity.activiti;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("user")
public class ShiroUser extends Model<ShiroUser> {

    @TableId
    private String id;

    @TableField("USER_NAME")
    private String userName;

    @TableField("PASSWORD")
    private String password;

    @TableField("SALT")
    private String salt;

    public String getCredentialsSalt() {
        return userName + salt;
    }

}
