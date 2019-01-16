package com.zhy.mvpgankio.common.base.fragment;

import android.app.Activity;
import android.os.Bundle;

import com.zhy.mvpgankio.common.base.mvp.BaseModel;
import com.zhy.mvpgankio.common.base.mvp.BasePresenter;
import com.zhy.mvpgankio.common.base.mvp.BaseView;
import com.zhy.mvpgankio.common.base.rx.TUtil;

/**
 * 1
 * Created by zhy on 2016/12/28.
 */
public class BaseFrameFragment<P extends BasePresenter, M extends BaseModel>
        extends BaseFragment
        implements BaseView {

    public P mPresenter;
    public M mModel;

    // 获取p和m泛型  并将v与m绑定
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        mPresenter.attachVM(this, mModel);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) mPresenter.detachVM();
        super.onDestroy();
    }

    @Override
    public Activity getAc() {
        return getActivity();
    }
}
