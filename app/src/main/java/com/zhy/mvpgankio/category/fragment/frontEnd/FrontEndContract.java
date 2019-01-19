package com.zhy.mvpgankio.category.fragment.frontEnd;

import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.base.mvp.BaseModel;
import com.zhy.mvpgankio.common.base.mvp.BasePresenter;
import com.zhy.mvpgankio.common.base.mvp.BaseView;
import com.zhy.mvpgankio.common.base.rx.BaseObserver;

import java.util.List;

/**
 * Created by zhy on 2019/1/17.
 */

public interface FrontEndContract {

    interface Model extends BaseModel {
        void getCategoryData(String category, int pageNum, int pageSize,
                             BaseObserver<AllCategoryBean> baseObserver);
    }

    interface View extends BaseView {
        void onRefreshPage(List<AllCategoryBean.ResultsBean> list,
                           int type);

        void onLoadMorePage(List<AllCategoryBean.ResultsBean> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getCategoryData(String category, int pageNum, int pageSize,
                                      int type);
    }
}
