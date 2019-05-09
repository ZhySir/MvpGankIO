package com.zhy.mvpgankio.home;

import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.base.mvp.BaseModel;
import com.zhy.mvpgankio.common.base.mvp.BasePresenter;
import com.zhy.mvpgankio.common.base.mvp.BaseView;
import com.zhy.mvpgankio.common.base.rx.BaseObserver;
import com.zhy.mvpgankio.welfare.bean.AllWelfareBean;

import java.util.List;

/**
 * Created by zhy on 2019/5/8.
 */
public interface HomeContract {

    interface Model extends BaseModel {
        void getWelfareData(String category, int pageNum, int pageSize,
                            BaseObserver<AllWelfareBean> baseObserver);

        //获取首页所有分类
        void getCategoryData(String category, int pageNum, int pageSize,
                             BaseObserver<AllCategoryBean> baseObserver);
    }

    interface View extends BaseView {
        void onWelfareData(List<AllWelfareBean.ResultsBean> list);

        void onRefreshPage(List<AllCategoryBean.ResultsBean> list,
                           int type);

        void onLoadMorePage(List<AllCategoryBean.ResultsBean> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getWelfareData(String category, int pageNum, int pageSize);

        public abstract void getCategoryData(String category, int pageNum, int pageSize,
                                             int type);
    }

}
