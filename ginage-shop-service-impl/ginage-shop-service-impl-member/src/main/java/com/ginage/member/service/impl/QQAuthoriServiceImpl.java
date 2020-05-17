
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
import com.ginage.common.constants.Constants;
import com.ginage.common.core.RedisUtils;
import com.ginage.common.core.TokenUtils;
import com.ginage.member.mappers.UserMapper;
import com.ginage.member.mappers.entitys.UserDO;
import com.ginage.member.mappers.entitys.UserTokenDO;
import com.ginage.member.service.QQAuthoriService;

/**
 * @date:2020年4月23日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class QQAuthoriServiceImpl extends BaseApiService<JSONObject> implements QQAuthoriService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private DataSourceTransactionManager DataSourceTransactionManager;
	@Autowired
	private TransactionDefinition TransactionDefinition;
	@Autowired
	private TokenUtils tokenUtils;

	@Override
	public BaseResponse<JSONObject> checkQQOpenID(String qq_openid,String deviceInfo) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(qq_openid)) {
			return setResultError("openid不能为空");
		}
		UserDO userDO=userMapper.checkQQOpenID(qq_openid);
		if(userDO==null) {
			return setResultError(Constants.HTTP_RES_CODE_203,"没有此QQ openID");
		}
		
		
		String loginType=Constants.MEMBER_LOGIN_TYPE_PC;
		UserTokenDO userTokenDO = userMapper.isLogined(userDO.getUser_id(), loginType);
		if (userTokenDO != null) {
			redisUtils.del(userTokenDO.getToken());
			int result = userMapper.setTokenAvailable(userTokenDO.getId(), 0);
			if (result < 1) {
				return setResultError("未知错误");
			}
		}

		
		JSONObject jb = new JSONObject();
		TransactionStatus transactionStatus=null;
		try {

			String token = "ginage.login." + loginType+"."+tokenUtils.createToken();
			String tokenValue = "ginage.login." + loginType + "." + userDO.getUser_id();
			UserTokenDO newUserTokenDO = new UserTokenDO();
			newUserTokenDO.setToken(token);
			newUserTokenDO.setLoginType(loginType);
			newUserTokenDO.setCreate_time(new Date());
			newUserTokenDO.setDeviceInfo(deviceInfo);
			newUserTokenDO.setUser_id(userDO.getUser_id());
			transactionStatus = DataSourceTransactionManager.getTransaction(TransactionDefinition);
			
			int result = userMapper.addUserToken(newUserTokenDO);

			if (result < 1) {
				throw new Exception("添加token失败");
			}
			redisUtils.mutil();
			redisUtils.setForTransaction(token, tokenValue, 6000, TimeUnit.SECONDS);
			jb.put("token", token);
			jb.put("mobile", userDO.getMobile());
			//redisUtils.exec(); 无需此步,commit适用mysql和redis
			DataSourceTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			//redisUtils.discard(); 无需此步,rollback适用mysql和redis
			DataSourceTransactionManager.rollback(transactionStatus);
		}
		return setResultSuccess(jb);
		
	}


}
