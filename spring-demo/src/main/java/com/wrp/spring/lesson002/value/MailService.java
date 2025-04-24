package com.wrp.spring.lesson002.value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月23日 15:51
 **/
@Component
public class MailService {
	@Autowired
	private MailConfig mailConfig;

	@Override
	public String toString() {
		return "MailService{" +
				"mailConfig=" + mailConfig +
				'}';
	}
}