
package com.ginage.member.hystrix;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.member.feign.WeixinServiceFeign;

/**
 * @date:2020年3月27日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Component
public class WeixinServiceHystrix implements WeixinServiceFeign {

}
