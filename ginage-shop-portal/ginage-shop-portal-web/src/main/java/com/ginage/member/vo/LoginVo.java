package com.ginage.member.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class LoginVo {

	/**
	 * 手机号码
	 */
	@NotBlank(message = "手机号码不能为空")
	@Pattern(regexp = "1[0-9]{10}",message = "手机号码格式错误")
	private String mobile;
	/**
	 * 手机密码
	 */
	@NotBlank(message = "密码不能为空")
	private String password;
	/**
	 * 图形验证码
	 */
	//@NotBlank(message = "验证码不能为空")
	private String graphicCode;
	private String loginType;
	private String deviceInfo;

}
