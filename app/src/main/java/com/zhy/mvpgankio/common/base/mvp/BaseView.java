package com.zhy.mvpgankio.common.base.mvp;

import android.app.Activity;

/**
 * TMvp -- view test
 * Created by zhy on 2016/12/31.
 */
public interface BaseView {

    void showNetDialog();

    void hideNetDialog();

    Activity getAc();
}
