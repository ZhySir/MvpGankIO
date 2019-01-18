package com.zhy.mvpgankio.common.http;

import com.zhy.mvpgankio.category.bean.AllCategoryBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * service
 * Created by zhy on 2018/5/26.
 */
public interface HttpService {

    //    @Path ：所有在网址中的参数（URL的问号前面），如：
    //    @Query ：URL问号后面的参数，如：get参数
    //    @QueryMap ：相当于多个@Query
    //    @Field：用于POST请求，提交单个数据
    //    @Body：相当于多个@Field，以对象的形式提交

    //分类数据
    @GET("data/{category}/{size}/{num}")
    Observable<AllCategoryBean> getCategoryData(@Path("category") String category,
                                                @Path("num") int pageNum,
                                                @Path("size") int pageSize);
}
