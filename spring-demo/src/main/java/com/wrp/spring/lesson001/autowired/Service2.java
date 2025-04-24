package com.wrp.spring.lesson001.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2025年04月21日 16:21
 **/
@Component
public class Service2 {

	private Service1 service1;

	public Service2() { //@1
		System.out.println(this.getClass() + "无参构造器");
	}

//	@Autowired
	public Service2(Service1 service1) { //@2
		System.out.println(this.getClass() + "有参构造器");
		this.service1 = service1;
	}

//	@Autowired
	public void setService1(Service1 service1) { //@1
		System.out.println(this.getClass().getName() + ".setService1方法");
		this.service1 = service1;
	}

//	@Autowired
	public void injectService1(Service1 service1) { //@1
		System.out.println(this.getClass().getName() + ".injectService1()");
		this.service1 = service1;
	}

	//没有注入
	public void injectService2(@Autowired Service1 service1, String name) { //@1
		System.out.println(String.format("%s.injectService1(),{service1=%s,name=%s}", this.getClass().getName(), service1, name));
		this.service1 = service1;
	}

//	@Autowired
	public void injectService3(Service1 service1, @Autowired(required = false) String name) { //@1
		System.out.println(String.format("%s.injectService1(),{service1=%s,name=%s}", this.getClass().getName(), service1, name));
		this.service1 = service1;
	}

	@Override
	public String toString() { //@2
		return "Service2{" +
				"service1=" + service1 +
				'}';
	}
}
