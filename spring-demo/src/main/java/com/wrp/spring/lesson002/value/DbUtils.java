package com.wrp.spring.lesson002.value;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wrp
 * @since 2025年04月23日 15:34
 **/
public class DbUtils {
	/**
	 * 模拟从db中获取邮件配置信息
	 *
	 */
	public static Map<String, Object> getMailInfoFromDb() {
		Map<String, Object> result = new HashMap<>();
		result.put("mail.host", "smtp.qq.com");
		result.put("mail.username", UUID.randomUUID().toString());
		result.put("mail.password", "123");
		return result;
	}
}
