package com.zhy.mvpgankio.home;

import com.zhy.mvpgankio.category.bean.AllCategoryBean;
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

    @Override
    public void getCategoryData(String category, int pageNum, int pageSize, final int type) {
        if (type == 0) {
            mView.showNetDialog();
        }
        mModel.getCategoryData(category, pageNum, pageSize, new BaseObserver<AllCategoryBean>() {
            @Override
            protected void onHandleSubscribe(Disposable d) {
                mRxManager.add(d);
            }

            @Override
            public void onNext(AllCategoryBean allCategoryBean) {
                mView.hideNetDialog();
                //没有code判断,直接使用
                List<AllCategoryBean.ResultsBean> resultsList = allCategoryBean.getResults();
                if (type == 0 ||
                        type == 1) {//默认进入||刷新
                    mView.onRefreshPage(resultsList, type);
                } else {//加载更多
                    mView.onLoadMorePage(resultsList);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideNetDialog();
            }
        });
    }

}
