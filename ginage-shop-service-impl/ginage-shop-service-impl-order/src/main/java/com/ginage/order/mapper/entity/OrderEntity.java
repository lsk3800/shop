
package com.ginage.order.mapper.entity;

import java.util.Date;

import lombok.Data;

/**
*@date:2020年6月14日
*@description:
*@Copyright: ginage.com
*
*/
@Data
public class OrderEntity {

	private Integer id;
	private String orderId;
	private String productId;
	private String phoneNum;
	private String channelId;
	private Date createdTime;
	private Date updatedTime;
	private char isPaid;
}
