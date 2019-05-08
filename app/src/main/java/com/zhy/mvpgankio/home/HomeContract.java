package com.zhy.mvpgankio.home;

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
    }

    interface View extends BaseView {
        void onWelfareData(List<AllWelfareBean.ResultsBean> list);

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getWelfareData(String category, int pageNum, int pageSize);
    }

}
