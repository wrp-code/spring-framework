package com.wrp.spring.lesson003.tx_business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务
 * @author wrp
 * @since 2025-04-29 08:13
 **/
@Component
public class UserService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//消息投递器
	@Autowired
	private MsgSender msgSender;

	/**
	 * 模拟用户注册成功，顺便发送消息
	 */
	@Transactional
	public void register(Long user_id, String user_name) {
		//先插入用户
		this.jdbcTemplate.update("insert into public.t_user(id,name) VALUES (?,?)", user_id, user_name);
		System.out.println(String.format("用户注册：[user_id:%s,user_name:%s]", user_id, user_name));
		//发送消息
		String msg = String.format("[user_id:%s,user_name:%s]", user_id, user_name);
		//调用投递器的send方法投递消息
		this.msgSender.send(msg, 1, user_id.toString());
	}

}
