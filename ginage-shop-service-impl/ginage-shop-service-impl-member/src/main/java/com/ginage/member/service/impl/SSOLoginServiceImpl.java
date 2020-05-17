
package com.ginage.member.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.core.MD5Util;
import com.ginage.common.core.RedisUtils;
import com.ginage.common.core.TokenUtils;
import com.ginage.member.mappers.UserMapper;
import com.ginage.member.mappers.entitys.UserDO;
import com.ginage.member.mappers.entitys.UserTokenDO;
import com.ginage.member.service.SSOLoginService;
import com.ginage.output.dto.UserLoginDTO;
import com.xxl.sso.core.conf.Conf;
import com.xxl.sso.core.store.SsoSessionIdHelper;
import com.xxl.sso.core.user.XxlSsoUser;
import com.xxl.sso.core.util.JedisUtil;

/**
 * @date:2020年4月29日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class SSOLoginServiceImpl extends BaseApiService<XxlSsoUser> implements SSOLoginService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisUtils redisUtils;
	

	@Override
	public BaseResponse<XxlSsoUser> ssoLogin(UserLoginDTO userLoginDTO) {
		if (StringUtils.isEmpty(userLoginDTO.getMobile())) {
			return setResultError("手机号不能为空");
		}
		if (StringUtils.isEmpty(userLoginDTO.getPassword())) {
			return setResultError("密码不能为空");
		}

		userLoginDTO.setPassword(MD5Util.MD5(userLoginDTO.getPassword()));
		UserDO userDO = userMapper.login(userLoginDTO);
		if (userDO == null) {
			return setResultError("用户名或密码错误!");
		}

		UserTokenDO userTokenDO = userMapper.isLogined(userDO.getUser_id(), userLoginDTO.getLoginType());
		if (userTokenDO != null) {
			redisUtils.del(userTokenDO.getToken());
			int result = userMapper.setTokenAvailable(userTokenDO.getId(), 0);
			if (result < 1) {
				return setResultError("未知错误");
			}
		}
		XxlSsoUser xxlSsoUser=new XxlSsoUser();
		xxlSsoUser.setUserid(userDO.getUser_id().toString());
		xxlSsoUser.setUsername(userDO.getMobile());
		return setResultSuccess(xxlSsoUser);
	}

	@Override
	public BaseResponse<XxlSsoUser> ssoCheckUserToken(String sessionId) {
		// TODO Auto-generated method stub
		XxlSsoUser xxlSsoUser=new XxlSsoUser();
		if (StringUtils.isEmpty(sessionId)) {
			return setResultError("sessionid不能为空");
		}
		String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
		String redisKey=Conf.SSO_SESSIONID.concat("#").concat(storeKey);
		xxlSsoUser = (XxlSsoUser) redisUtils.getObject(redisKey);
		if (xxlSsoUser==null) {
			setResultError("用户不存在或已过期");
		}
		return setResultSuccess(xxlSsoUser);
	}

}
