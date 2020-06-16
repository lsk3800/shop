package com.ginage.payment.service.mapper.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class PaymentTransactionLogEntity {
	/** 主键ID */
	@Id
	private Long id;
	/** 同步回调日志 */
	private String syncLog;
	/** 异步回调日志 */
	private String asyncLog;
	/** 支付渠道ID */
	private String channelId;
	/** 支付ID */
	private String paymentId;
	/** 支付交易ID */
	private String transactionId;
	/** 乐观锁 */
	private Integer revision;
	/** 创建人 */
	private String createdBy;
	/** 创建时间 */
	private Date createdTime;
	/** 更新人 */
	private String updatedBy;
	/** 更新时间 */
	private Date updatedTime;

	/**
	 * 第三方支付id 支付宝、银联等 在第三方支付渠道完成后分配一个支付id 对账使用
	 */
	private String payId;

	/**
	 * 使用雪花算法生产 支付系统 支付id
	 */
	private String orderId;

}