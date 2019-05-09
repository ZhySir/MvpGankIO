package com.zhy.mvpgankio.home;

import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.base.rx.BaseObserver;
import com.zhy.mvpgankio.common.http.HttpUtils;
import com.zhy.mvpgankio.common.http.utils.RetrofitHelper;
import com.zhy.mvpgankio.welfare.bean.AllWelfareBean;

/**
 * Created by zhy on 2019/5/8.
 */
public class HomeModel implements HomeContract.Model {

    @Override
    public void getWelfareData(String category, int pageNum, int pageSize, BaseObserver<AllWelfareBean> baseObserver) {
        HttpUtils.loadData(RetrofitHelper.getHttpService().getWelfareData(category, pageNum, pageSize), baseObserver);
    }

    @Override
    public void getCategoryData(String category, int pageNum, int pageSize, BaseObserver<AllCategoryBean> baseObserver) {
        HttpUtils.loadData(RetrofitHelper.getHttpService().getCategoryData(category, pageNum, pageSize), baseObserver);
    }

}
