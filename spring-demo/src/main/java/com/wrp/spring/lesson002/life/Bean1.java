package com.wrp.spring.lesson002.life;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringValueResolver;

/**
 * @author wrp
 * @since 2025年04月23日 12:03
 **/
public class Bean1  implements EnvironmentAware, EmbeddedValueResolverAware, ResourceLoaderAware, ApplicationEventPublisherAware, MessageSourceAware, ApplicationContextAware {

	@PostConstruct
	public void postConstruct1() { //@1
		System.out.println("postConstruct1()");
	}

	@PostConstruct
	public void postConstruct2() { //@2
		System.out.println("postConstruct2()");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("setApplicationContext:" + applicationContext);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		System.out.println("setApplicationEventPublisher:" + applicationEventPublisher);
	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		System.out.println("setEmbeddedValueResolver:" + resolver);
	}

	@Override
	public void setEnvironment(Environment environment) {
		System.out.println("setEnvironment:" + environment.getClass());
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		System.out.println("setMessageSource:" + messageSource);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		System.out.println("setResourceLoader:" + resourceLoader);
	}
}