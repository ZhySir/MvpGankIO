package com.zhy.mvpgankio.welfare;

import com.zhy.mvpgankio.common.base.rx.BaseObserver;
import com.zhy.mvpgankio.welfare.bean.AllWelfareBean;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhy on 2019/1/19.
 */

public class WelfarePresenter extends WelfareContract.Presenter {

    @Override
    public void getWelfareData(String welfare, int pageNum, int pageSize, final int type) {
        if (type == 0) {
            mView.showNetDialog();
        }
        mModel.getWelfareData(welfare, pageNum, pageSize, new BaseObserver<AllWelfareBean>() {
            @Override
            protected void onHandleSubscribe(Disposable d) {
                mRxManager.add(d);
            }

            @Override
            public void onNext(AllWelfareBean allWelfareBean) {
                mView.hideNetDialog();
                //没有code判断,直接使用
                List<AllWelfareBean.ResultsBean> resultsList = allWelfareBean.getResults();
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
