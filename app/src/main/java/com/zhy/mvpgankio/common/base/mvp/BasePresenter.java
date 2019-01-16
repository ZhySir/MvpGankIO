package com.zhy.mvpgankio.common.base.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zhy.mvpgankio.common.base.rx.RxManager;

/**
 * tmvp -- presenter
 * Created by zhy on 2016/12/31.
 */
public abstract class BasePresenter<M, V> {

    public M mModel;
    public V mView;

    public RxManager mRxManager = new RxManager();

    public void attachVM(V v, M m) {
        this.mModel = m;
        this.mView = v;
    }

    public Activity getContext() {
        if (mView != null) {
            if (mView instanceof Activity) return (Activity) mView;
            else return ((Fragment) mView).getActivity();
        }
        return null;
    }

    public void detachVM() {
        mRxManager.clear();
        mView = null;
        mModel = null;
    }

    public void openActivity(Class<? extends Activity> toActivity) {
        openActivity(toActivity, null);
    }

    public void openActivity(Class<? extends Activity> toActivity, Bundle parameter) {
        if (getContext() == null) return;
        Intent intent = new Intent(getContext(), toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        getContext().startActivity(intent);
    }

}
