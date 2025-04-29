package com.wrp.spring.lesson003.tx_business;

import lombok.Builder;
import lombok.Data;

/**
 * 预发消息表
 * @author wrp
 * @since 2025-04-29 08:06
 **/
@Data
@Builder
public class MsgModel {

	private Long id;
	//消息内容
	private String msg;
	//消息订单id
	private Long msg_order_id;
	//消息状态,0:待投递，1：已发送，2：取消发送
	private Integer status;
}
