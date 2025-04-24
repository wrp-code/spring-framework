package com.wrp.spring.lesson002.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月23日 15:34
 **/
@Component
@RefreshScope
public class MailConfig {

	@Value("${mail.host}")
	private String host;

	@Value("${mail.username}")
	private String username;

	@Value("${mail.password}")
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "MailConfig{" +
				"host='" + host + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}