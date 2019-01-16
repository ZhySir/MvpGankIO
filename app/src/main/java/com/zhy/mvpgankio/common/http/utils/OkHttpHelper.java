package com.zhy.mvpgankio.common.http.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * okhttp
 * Created by zhy on 2017/5/26.
 */
public class OkHttpHelper {

    private OkHttpClient mClient;
    private OkHttpClient mClient2;

    private static OkHttpHelper clientHelper;

    public static OkHttpHelper getInstance() {
        if (clientHelper == null) {
            synchronized (OkHttpHelper.class) {
                if (clientHelper == null) clientHelper = new OkHttpHelper();
            }
        }
        return clientHelper;
    }

    //获取OKHttpClicent对象
    OkHttpClient getOkHttpClient() {
        if (mClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            Interceptor paramInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
//                            .header("appVersion", AppUtil.getVersionName(MyApplication.getContext()))
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            };
            mClient = configOkhttp()
                    .addInterceptor(paramInterceptor)
                    // .cache(cache)      //设置缓存
                    .build();
        }
        return mClient;
    }

    /**
     * 不用+请求头
     */
    OkHttpClient getOkHttpClient2() {
        if (mClient2 == null) {
            mClient2 = configOkhttp().build();
        }
        return mClient2;
    }

    /**
     * 获取打印请求内容的log插值器
     */
    private HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * 配置okhttp
     */
    private OkHttpClient.Builder configOkhttp() {
        return new OkHttpClient.Builder()
                .addInterceptor(getLogInterceptor())
                .retryOnConnectionFailure(true)//错误重联
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
    }

}
