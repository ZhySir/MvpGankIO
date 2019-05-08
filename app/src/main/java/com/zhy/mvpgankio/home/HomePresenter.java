package com.zhy.mvpgankio.home;

import com.zhy.mvpgankio.common.base.rx.BaseObserver;
import com.zhy.mvpgankio.welfare.bean.AllWelfareBean;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhy on 2019/5/8.
 */
public class HomePresenter extends HomeContract.Presenter {

    @Override
    public void getWelfareData(String category, int pageNum, int pageSize) {
        mView.showNetDialog();
        mModel.getWelfareData(category, pageNum, pageSize, new BaseObserver<AllWelfareBean>() {
            @Override
            protected void onHandleSubscribe(Disposable d) {
                mRxManager.add(d);
            }

            @Override
            public void onNext(AllWelfareBean allWelfareBean) {
                mView.hideNetDialog();
                //没有code判断,直接使用
                List<AllWelfareBean.ResultsBean> resultsList = allWelfareBean.getResults();
                mView.onWelfareData(resultsList);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideNetDialog();
            }
        });
    }

}
