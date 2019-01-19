package com.zhy.mvpgankio.category.fragment.frontEnd;

import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.base.rx.BaseObserver;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhy on 2019/1/17.
 */

public class FrontEndPresenter extends FrontEndContract.Presenter {

    @Override
    void getCategoryData(String category, int pageNum, int pageSize, final int type) {
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
