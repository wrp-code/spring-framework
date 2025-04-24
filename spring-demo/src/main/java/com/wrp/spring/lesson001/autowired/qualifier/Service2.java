package com.wrp.spring.lesson001.autowired.qualifier;

import com.wrp.spring.lesson001.autowired.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月21日 16:43
 **/
@Component
@Qualifier("tag1")
@Primary
public class Service2 implements IService {
}
