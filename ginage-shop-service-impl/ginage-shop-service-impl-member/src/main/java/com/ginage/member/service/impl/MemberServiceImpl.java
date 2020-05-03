
package com.ginage.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.member.feign.WeixinServiceFeign;
import com.ginage.member.mappers.UserMapper;
import com.ginage.member.mappers.entitys.UserDO;
import com.ginage.output.dto.UserOutputDTO;
import com.ginage.service.MemberService;

/**
 * @date:2020年3月27日
 * @description:
 * @Copyright: ginage.com
 *
 */

@RestController
public class MemberServiceImpl extends BaseApiService<UserOutputDTO>implements MemberService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public BaseResponse<UserOutputDTO> existMobile(String phoneNumber){
		if(StringUtils.isEmpty(phoneNumber)) {
			return setResultError("手机号不能为空");
		}
		UserDO userEntity = userMapper.existMobile(phoneNumber);
		if(null==userEntity) {
			
			return setResultError(Constants.HTTP_RES_CODE_203,"此手机号还未注册");
		}
		
		
		//返回时去掉密码
		userEntity.setPassword(null);
		
		UserOutputDTO userOutpubDTO = new UserOutputDTO();
		
		return setResultSuccess(userOutpubDTO);
		
	}

	@Override
	public BaseResponse<UserOutputDTO> getInfo() {
		// TODO Auto-generated method stub
		UserOutputDTO userOutputDTO = new UserOutputDTO();
		userOutputDTO.setUser_name("这就是你要的信息");
		return setResultSuccess(userOutputDTO);
	}
	

}
