package com.ginage.input.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户注册")
public class UserInputDTO {

	
	@ApiModelProperty(value = "注册码")
	@NotBlank(message = "注册码不能为空")
	private String registCode;
	
	@NotBlank(message = "手机号码不能为空")
	@Pattern(regexp = "1[0-9]{10}",message = "手机号码格式错误")
	@ApiModelProperty(value = "手机号码")
	private String mobile;
	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;
	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名")
	private String user_name;
	/**
	 * 性别 0 男 1女
	 */
	@ApiModelProperty(value = "用户性别")
	private char sex;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "用户年龄")
	private int age;
	

	@ApiModelProperty(value = " 用户头像")
	private String pic_img;
	/**
	 * 用户关联 QQ 开放ID
	 */
	@ApiModelProperty(value = "用户关联 QQ 开放ID")
	private String qq_openid;
	/**
	 * 用户关联 微信 开放ID
	 */
	@ApiModelProperty(value = "用户关联 微信 开放ID")
	private String wx_openid;
}
