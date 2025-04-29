package com.wrp.spring.lesson003.tx_business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 订单服务
 * @author wrp
 * @since 2025-04-29 08:07
 **/
@Component
public class MsgOrderService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 插入消息订单
	 *
	 */
	@Transactional
	public MsgOrderModel insert(Integer ref_type, String ref_id) {
		MsgOrderModel msgOrderModel = MsgOrderModel.builder()
				.ref_type(ref_type).ref_id(ref_id).build();
		//插入消息
		this.jdbcTemplate.update("insert into public.t_msg_order (ref_type,ref_id) values (?,?)",
				ref_type,
				ref_id
		);
		//获取消息订单id
		msgOrderModel.setId(this.jdbcTemplate
				.queryForObject("SELECT LAST_INSERT_ID()", Long.class));
		return msgOrderModel;
	}

	/**
	 * 根据消息id获取消息
	 *
	 */
	public MsgOrderModel getById(Long id) {
		List<MsgOrderModel> list = this.jdbcTemplate
				.query("select * from public.t_msg_order where id = ? limit 1",
						new BeanPropertyRowMapper<MsgOrderModel>(MsgOrderModel.class), id);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

}
