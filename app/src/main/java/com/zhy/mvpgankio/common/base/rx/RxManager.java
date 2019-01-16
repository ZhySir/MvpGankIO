package com.zhy.mvpgankio.common.base.rx;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 1
 * Created by zhy on 2016/12/31.
 */
public class RxManager {

    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();//管理订阅者

    public void add(Disposable m) {
        mCompositeSubscription.add(m);
    }

    public void clear() {
        //取消订阅
        mCompositeSubscription.clear();
    }

}
