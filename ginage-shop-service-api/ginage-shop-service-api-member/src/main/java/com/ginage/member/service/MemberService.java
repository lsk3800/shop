
package com.ginage.member.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.output.dto.UserOutputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @date:2020年3月27日
 * @description:会员服务接口
 * @Copyright: ginage.com
 *
 */
@Api(tags = "会员服务接口")

public interface MemberService {

	@ApiOperation(value = "检查手机号是否已经注册")
	@PostMapping("/existMobile")
	public BaseResponse<UserOutputDTO> existMobile(@RequestParam("phoneNumber") String phoneNumber);
	@ApiOperation(value = "检查手机号是否已经注册")
	@GetMapping("/getInfo")
	public BaseResponse<UserOutputDTO> getInfo();
	
}
