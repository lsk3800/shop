
package com.ginage.output.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*@date:2020年4月5日
*@description:
*@Copyright: ginage.com
*
*/
@Data
@ApiModel(value="用户登录参数")
public class UserLoginDTO {
	@NotBlank(message = "手机号码不能为空")
	@Pattern(regexp = "1[0-9]{10}",message = "手机号码格式错误")
	
	@ApiModelProperty(value = "手机号")
	private String mobile;
	
	
	@NotBlank(message = "密码不能为空")
	@ApiModelProperty(value = "密码")
	private String password;
	
	
	@ApiModelProperty(value = "id")
	private Long userID;
	
	
	@NotBlank(message = "登录类型不能为空")
	@ApiModelProperty(value = "登录类型")
	private String loginType;
	@NotBlank(message = "设备信息不能为空")
	@ApiModelProperty(value = "设备信息")
	private String deviceInfo;
}
