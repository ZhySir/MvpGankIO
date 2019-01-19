package com.zhy.mvpgankio.welfare.fragment.main;

import com.zhy.mvpgankio.common.base.mvp.BaseModel;
import com.zhy.mvpgankio.common.base.mvp.BasePresenter;
import com.zhy.mvpgankio.common.base.mvp.BaseView;
import com.zhy.mvpgankio.common.base.rx.BaseObserver;
import com.zhy.mvpgankio.welfare.bean.AllWelfareBean;

import java.util.List;

/**
 * Created by zhy on 2019/1/19.
 */

public interface WelfareContract {

    interface Model extends BaseModel {
        void getWelfareData(String category, int pageNum, int pageSize,
                            BaseObserver<AllWelfareBean> baseObserver);
    }

    interface View extends BaseView {
        void onRefreshPage(List<AllWelfareBean.ResultsBean> list,
                           int type);

        void onLoadMorePage(List<AllWelfareBean.ResultsBean> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getWelfareData(String welfare, int pageNum, int pageSize,
                                            int type);
    }

}
