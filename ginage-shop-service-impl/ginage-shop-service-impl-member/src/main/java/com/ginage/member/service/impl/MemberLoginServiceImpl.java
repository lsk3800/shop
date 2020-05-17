
package com.ginage.member.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.ginage.member.service.MemberLoginService;
import com.ginage.output.dto.UserLoginDTO;
import com.ginage.output.dto.UserOutputDTO;

/**
 * @date:2020年4月5日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class MemberLoginServiceImpl extends BaseApiService<JSONObject> implements MemberLoginService {
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
	public BaseResponse<JSONObject> login(@RequestBody UserLoginDTO userLoginDTO){
		// TODO Auto-generated method stub
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

		
		JSONObject jb = new JSONObject();
		TransactionStatus transactionStatus=null;
		try {

			String token = "ginage.login." + userLoginDTO.getLoginType()+"."+tokenUtils.createToken();
			String tokenValue = "ginage.login." + userLoginDTO.getLoginType() + "." + userDO.getUser_id();
			UserTokenDO newUserTokenDO = new UserTokenDO();
			newUserTokenDO.setToken(token);
			newUserTokenDO.setLoginType(userLoginDTO.getLoginType());
			newUserTokenDO.setCreate_time(new Date());
			newUserTokenDO.setDeviceInfo(userLoginDTO.getDeviceInfo());
			newUserTokenDO.setUser_id(userDO.getUser_id());
			transactionStatus = DataSourceTransactionManager.getTransaction(TransactionDefinition);
			
			int result = userMapper.addUserToken(newUserTokenDO);

			if (result < 1) {
				throw new Exception("添加token失败");
			}
			redisUtils.mutil();
			redisUtils.setForTransaction(token, tokenValue, 6000, TimeUnit.SECONDS);
			jb.put("token", token);
			//redisUtils.exec(); 无需此步,commit适用mysql和redis
			DataSourceTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			//redisUtils.discard(); 无需此步,rollback适用mysql和redis
			DataSourceTransactionManager.rollback(transactionStatus);
		}
		return setResultSuccess(jb);
	}

	@Override
	public BaseResponse<JSONObject> checkUserToken(String token) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(token)) {
			return setResultError("token不能为空");
		}
		
		String tokenValue = redisUtils.get(token);
		if(StringUtils.isEmpty(tokenValue)) {
			return setResultError("自动登录失败");
		}
		//UserTokenDO userTokenDO=userMapper.checkUserToken(token);
		int index = tokenValue.lastIndexOf('.');
		if(index<0) {
			return setResultError("index<0");
		}
		String user_id=tokenValue.substring(index+1);
		UserOutputDTO userOutputDTO =userMapper.getUserInfoById(Long.parseLong(user_id));
		JSONObject jb=new JSONObject();
		jb.put("mobile", userOutputDTO.getMobile());
		return setResultSuccess(jb);
	}

	@Override
	public BaseResponse<JSONObject> qqLogin(UserLoginDTO userLoginDTO,String qqOpenID) {
		// TODO Auto-generated method stub
		
		if (StringUtils.isEmpty(userLoginDTO.getMobile())) {
			return setResultError("手机号不能为空");
		}
		if (StringUtils.isEmpty(userLoginDTO.getPassword())) {
			return setResultError("密码不能为空");
		}
		if (StringUtils.isEmpty(qqOpenID)) {
			return setResultError("openID不能为空");
		}

		userLoginDTO.setPassword(MD5Util.MD5(userLoginDTO.getPassword()));
		UserDO userDO = userMapper.login(userLoginDTO);
		if (userDO == null) {
			return setResultError("用户名或密码错误!");
		}
		int addQQOpenIDResult=userMapper.addQQOpenID(userDO.getUser_id(),qqOpenID);
		if(addQQOpenIDResult<0) {
			return setResultError("添加qqOpenID出错");
		}
		
		UserTokenDO userTokenDO = userMapper.isLogined(userDO.getUser_id(), userLoginDTO.getLoginType());
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

			String token = "ginage.login." + userLoginDTO.getLoginType()+"."+tokenUtils.createToken();
			String tokenValue = "ginage.login." + userLoginDTO.getLoginType() + "." + userDO.getUser_id();
			UserTokenDO newUserTokenDO = new UserTokenDO();
			newUserTokenDO.setToken(token);
			newUserTokenDO.setLoginType(userLoginDTO.getLoginType());
			newUserTokenDO.setCreate_time(new Date());
			newUserTokenDO.setDeviceInfo(userLoginDTO.getDeviceInfo());
			newUserTokenDO.setUser_id(userDO.getUser_id());
			transactionStatus = DataSourceTransactionManager.getTransaction(TransactionDefinition);
			
			int result = userMapper.addUserToken(newUserTokenDO);

			if (result < 1) {
				throw new Exception("添加token失败");
			}
			redisUtils.mutil();
			redisUtils.setForTransaction(token, tokenValue, 6000, TimeUnit.SECONDS);
			jb.put("token", token);
			//redisUtils.exec(); 无需此步,commit适用mysql和redis
			DataSourceTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			//redisUtils.discard(); 无需此步,rollback适用mysql和redis
			DataSourceTransactionManager.rollback(transactionStatus);
		}
		return setResultSuccess(jb);
	}


}
