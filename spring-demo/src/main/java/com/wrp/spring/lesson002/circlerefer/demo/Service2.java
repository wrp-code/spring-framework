package com.wrp.spring.lesson002.circlerefer.demo;

/**
 * @author wrp
 * @since 2025年04月24日 13:55
 **/
public class Service2 {

    Service1 service1;

    public Service2() {
    }

    public Service2(Service1 service1) {
        this.service1 = service1;
    }

    public void setService1(Service1 service1) {
        this.service1 = service1;
    }

    public Service1 getService1() {
        return service1;
    }
}