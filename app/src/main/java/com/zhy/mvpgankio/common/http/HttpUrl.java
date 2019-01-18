package com.zhy.mvpgankio.common.http;

/**
 * url
 * Created by zhy on 2019/1/16.
 */
public class HttpUrl {

    //环境开关
    public static boolean official = true;

    //GankIO API
    public static String BaseUrl = official ? "https://gank.io/api/" : "";

}
