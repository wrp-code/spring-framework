package com.wrp.spring.lesson001.autowired.type;

import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025-04-21 20:00
 **/
@Component
public class UserDao implements IDao<UserModel> { //@1
}