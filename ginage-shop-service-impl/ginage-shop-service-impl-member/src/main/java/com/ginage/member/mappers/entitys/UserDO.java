package com.ginage.member.mappers.entitys;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDO {

	/**
	 * userid
	 */
	private Long user_id;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户名称
	 */
	private String user_name;
	/**
	 * 性别 0 男 1女
	 */
	private char sex;
	/**
	 * 年龄
	 */
	private int age;
	/**
	 * 注册时间
	 */
	private Date create_time;
	/**
	 * 修改时间
	 *
	 */
	private Date update_time;
	/**
	 * 账号是否可以用 1 正常 0冻结
	 */
	private char is_avalible;
	/**
	 * 用户头像
	 */
	private String pic_img;
	/**
	 * 用户关联 QQ 开放ID
	 */
	private String qq_openid;
	/**
	 * 用户关联 微信 开放ID
	 */
	private String wx_openid;
}
