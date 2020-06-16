
package com.ginage.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ginage.order.mapper.entity.OrderEntity;

/**
*@date:2020年6月14日
*@description:
*@Copyright: ginage.com
*
*/
@Mapper
public interface OrderMapper {

	public void createSpikeOrder(OrderEntity orderEntity);

	public OrderEntity querySpikeResult(@Param("phoneNum") String phoneNum,@Param("productId") Long productId);
}
