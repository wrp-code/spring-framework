package com.wrp.spring.lesson003.tx_business;

import lombok.Builder;
import lombok.Data;

/**
 * 业务订单表
 * @author wrp
 * @since 2025-04-29 08:07
 **/
@Data
@Builder
public class MsgOrderModel {
	private Long id;
	//关联业务类型
	private Integer ref_type;
	//关联业务id（ref_type & ref_id 唯一）
	private String ref_id;
}
