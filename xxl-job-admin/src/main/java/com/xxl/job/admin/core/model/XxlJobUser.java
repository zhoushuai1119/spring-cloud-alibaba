package com.xxl.job.admin.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author xuxueli 2019-05-04 16:43:12
 */
@Data
@TableName("xxl_job_user")
public class XxlJobUser extends Model<XxlJobUser> {

	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 账号
	 */
	@TableField("username")
	private String username;
	/**
	 * 密码
	 */
	@TableField("password")
	private String password;
	/**
	 * 角色：0-普通用户、1-管理员
	 */
	@TableField("role")
	private int role;
	/**
	 * 权限：执行器ID列表，多个逗号分割
	 */
	@TableField("permission")
	private String permission;

	// plugin
	public boolean validPermission(int jobGroup){
		if (this.role == 1) {
			return true;
		} else {
			if (StringUtils.hasText(this.permission)) {
				for (String permissionItem : this.permission.split(",")) {
					if (String.valueOf(jobGroup).equals(permissionItem)) {
						return true;
					}
				}
			}
			return false;
		}
	}

}
