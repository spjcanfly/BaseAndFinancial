package com.example.spj.p2p.common;

/**
 * Created by spj on 2016/9/19.
 */
public class AppNetConfig {
    //提供主机ip的地址
    public static final String HOST = "192.168.56.1";
    //提供web应用的地址
    public static final String BASE_URL = "http://"+HOST+":8080/P2PInvest/";
    //具体资源的地址
    public static final String PRODUCT = BASE_URL + "product";
    public static final String INDEX = BASE_URL + "index";
    public static final String LOGIN = BASE_URL + "login";
    public static final String TEST = BASE_URL + "test";
}
