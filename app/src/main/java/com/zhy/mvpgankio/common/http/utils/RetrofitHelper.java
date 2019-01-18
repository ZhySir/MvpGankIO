package com.zhy.mvpgankio.common.http.utils;

import com.zhy.mvpgankio.common.http.HttpService;
import com.zhy.mvpgankio.common.http.HttpUrl;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit
 * Created by zhy on 2017/5/26.
 */
public class RetrofitHelper {

    private static Retrofit mRetrofit;
    //..如果多个服务器..因此..
//    private static Retrofit mRetrofit2;

    //获取Retrofit对象
    public static HttpService getHttpService() {
        if (null == mRetrofit) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(HttpUrl.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(OkHttpHelper.getInstance().getOkHttpClient())
                    .build();
        }
        return mRetrofit.create(HttpService.class);
    }

    //获取Retrofit对象
//    public static HttpService getHttpService2() {
//        if (null == mRetrofit2) {
//            mRetrofit2 = new Retrofit.Builder()
//                    .baseUrl(HttpUrl.BaseTradingUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .client(OkHttpHelper.getInstance().getOkHttpClient())
//                    .build();
//        }
//        return mRetrofit2.create(HttpService.class);
//    }

}
