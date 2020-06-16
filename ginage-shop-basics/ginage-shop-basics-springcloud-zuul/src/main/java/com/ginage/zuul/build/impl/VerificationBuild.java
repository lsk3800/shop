package com.ginage.zuul.build.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ginage.common.core.SignUtil;
import com.ginage.zuul.build.GatewayBuild;
import com.ginage.zuul.mapper.BlacklistMapper;
import com.ginage.zuul.mapper.entity.IPBlacklist;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;


/**
 * 参数验证
 * 
 * 
 */
@Slf4j
@Component
public class VerificationBuild implements GatewayBuild {
	@Autowired
	private BlacklistMapper blacklistMapper;
	
	private RateLimiter rateLimiter=RateLimiter.create(1);
/**
 * 黑名单
 * 
 */
	@Override
	public Boolean blackBlock(RequestContext ctx, String ipAddres, HttpServletResponse response) {
		// 2.查询数据库黑名单
		IPBlacklist ipBlacklist = blacklistMapper.findBlacklist(ipAddres);
		if (ipBlacklist != null) {
			resultError(ctx, "ip:" + ipAddres + ",Insufficient access rights");
			return false;
		}
		log.info(">>>>>>ip:{},验证通过>>>>>>>", ipAddres);
		// 3.将ip地址传递到转发服务中
		response.addHeader("ipAddres", ipAddres);
		log.info(">>>>>>ip:{},验证通过>>>>>>>", ipAddres);
		return true;
	}

	/**
	 * 
	 * 
	 */
	@Override
	public Boolean toVerifyMap(RequestContext ctx, String ipAddres, HttpServletRequest request) {
		// 4.外网传递参数验证
		Map<String, String> verifyMap = SignUtil.toVerifyMap(request.getParameterMap(), false);
		if (!SignUtil.verify(verifyMap)) {
			resultError(ctx, "ip:" + ipAddres + ",Sign fail");
			return false;
		}
		return true;
	}

	private void resultError(RequestContext ctx, String errorMsg) {
		ctx.setResponseStatusCode(401);
		ctx.setSendZuulResponse(false);
		ctx.getResponse().setContentType("text/html;charset=UTF-8");
		ctx.setResponseBody(errorMsg);

	}
/**
 * 
 * 
 */
	@Override
	public Boolean rateLimiter(RequestContext ctx) {
		// TODO Auto-generated method stub
		boolean tryAcquire = rateLimiter.tryAcquire(0, TimeUnit.SECONDS);
		if(!tryAcquire) {
			resultError(ctx,"当前排队人数过多,请稍后重试....");
			return false;
		}
		return true;
	}
}
