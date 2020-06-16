
package com.ginage.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ginage.integral.mapper.entity.IntegralEntity;



/**
*@date:2020年5月15日
*@description:
*@Copyright: ginage.com
*
*/
@Mapper
public interface IntegralMapper {
	
	int addIntegral(IntegralEntity integralEntity);
}
