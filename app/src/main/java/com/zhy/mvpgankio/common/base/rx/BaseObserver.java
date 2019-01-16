package com.zhy.mvpgankio.common.base.rx;

import com.zhy.libutils.LogUtil;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.application.MyApplication;
import com.zhy.mvpgankio.common.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 1
 * Created by zhy on 2018/8/16.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private Disposable disposable;

    public Disposable getDisposable() {
        return disposable;
    }

    //可将d全部添加到CompositeDisposable中在页面销毁时将所有的网络停掉
    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        onHandleSubscribe(d);
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        ToastUtil.showToast(MyApplication.getContext().getString(R.string.could_not_connect_server));
        LogUtil.e("okhttp----retrofit_error_message=" + e.getMessage());
    }

    protected abstract void onHandleSubscribe(Disposable d);

}
