package com.zhy.mvpgankio.category.fragment.android;

import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.base.rx.BaseObserver;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhy on 2019/1/17.
 */

public class AndroidPresenter extends AndroidContract.Presenter {

    @Override
    void getCategoryData(String category, int pageNum, int pageSize, final int type) {
        mView.showNetDialog();
        mModel.getCategoryData(category, pageNum, pageSize, new BaseObserver<AllCategoryBean>() {
            @Override
            protected void onHandleSubscribe(Disposable d) {
                mRxManager.add(d);
            }

            @Override
            public void onNext(AllCategoryBean allCategoryBean) {
                mView.hideNetDialog();
                //没有code判断,直接使用
                if (type == 0) {//默认进入
                    mView.onRefreshPage(allCategoryBean.getResults(), type);
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
