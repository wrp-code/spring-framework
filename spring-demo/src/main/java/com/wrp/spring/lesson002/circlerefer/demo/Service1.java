package com.wrp.spring.lesson002.circlerefer.demo;

/**
 * @author wrp
 * @since 2025年04月24日 13:55
 **/
public class Service1 {

    Service2 service2;

    public Service1() {
    }

    public Service1(Service2 service2) {
        this.service2 = service2;
    }

    public void setService2(Service2 service2) {
        this.service2 = service2;
    }

    public Service2 getService2() {
        return service2;
    }
}