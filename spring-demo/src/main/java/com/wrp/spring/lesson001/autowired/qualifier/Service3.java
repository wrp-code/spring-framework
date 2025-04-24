package com.wrp.spring.lesson001.autowired.qualifier;

import com.wrp.spring.lesson001.autowired.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月21日 16:43
 **/
@Component
@Qualifier("tag2")
public class Service3 implements IService {
}
