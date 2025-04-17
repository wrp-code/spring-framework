package com.wrp.spring.lesson001.beanfactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wrp
 * @since 2025年04月15日 15:28
 **/
public class AutowireCandidateTest {

	@Test
	public void candidateBean() {
		String beanXml = "classpath:lesson001/autowireCandidateBean.xml";
		ClassPathXmlApplicationContext context = IocUtil.context(beanXml);
		CandidateBean.ServiceA serviceA = context.getBean(CandidateBean.ServiceA.class); //@2
		Assertions.assertNotNull(serviceA);
		CandidateBean.ServiceB serviceB = context.getBean("serviceB",CandidateBean.ServiceB.class); //@2
		Assertions.assertNotNull(serviceB);
		CandidateBean.ServiceB serviceB2 = context.getBean(CandidateBean.ServiceB.class); //@2
		Assertions.assertNotNull(serviceB2);
		Assertions.assertNotEquals(serviceB, serviceB2);

		CandidateBean candidateBean = context.getBean(CandidateBean.class);
		Assertions.assertEquals(serviceB2, candidateBean.service);
		Assertions.assertEquals(1, candidateBean.services.size());
	}
}
