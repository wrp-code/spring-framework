package com.wrp.spring.lesson001.beanfactory;

import java.util.List;
import java.util.Map;

/**
 * @author wrp
 * @since 2025年04月15日 13:45
 **/
public class DiAutowireByTypeExtend {

	//定义了一个接口
	public interface IService1 {
	}

	public static class BaseServie {
		private String desc;

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		@Override
		public String toString() {
			return "BaseServie{" +
					"desc='" + desc + '\'' +
					'}';
		}
	}

	//Service1实现了IService1接口
	public static class Service1 extends BaseServie implements IService1 {

	}

	//Service1实现了IService1接口
	public static class Service2 extends BaseServie implements IService1 {
	}

	private List<IService1> serviceList;//@1
	private List<BaseServie> baseServieList;//@2
	private Map<String, IService1> service1Map;//@3
	private Map<String, BaseServie> baseServieMap;//@4

	public List<IService1> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<IService1> serviceList) {//@5
		this.serviceList = serviceList;
	}

	public List<BaseServie> getBaseServieList() {
		return baseServieList;
	}

	public void setBaseServieList(List<BaseServie> baseServieList) {//@6
		this.baseServieList = baseServieList;
	}

	public Map<String, IService1> getService1Map() {
		return service1Map;
	}

	public void setService1Map(Map<String, IService1> service1Map) {//@7
		this.service1Map = service1Map;
	}

	public Map<String, BaseServie> getBaseServieMap() {
		return baseServieMap;
	}

	public void setBaseServieMap(Map<String, BaseServie> baseServieMap) {//@8
		this.baseServieMap = baseServieMap;
	}

	@Override
	public String toString() { //9
		return "DiAutowireByTypeExtend{" +
				"serviceList=" + serviceList +
				", baseServieList=" + baseServieList +
				", service1Map=" + service1Map +
				", baseServieMap=" + baseServieMap +
				'}';
	}
}