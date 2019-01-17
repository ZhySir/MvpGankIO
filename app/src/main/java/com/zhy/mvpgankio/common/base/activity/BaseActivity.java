package com.zhy.mvpgankio.common.base.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.zhy.libutils.ActivityUtil;
import com.zhy.libutils.ClickUtil;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.base.BaseFuncIml;

import io.reactivex.disposables.CompositeDisposable;

/**
 * BaseActivity
 * Created by zhy on 2016/12/26.
 */
public class BaseActivity extends AppCompatActivity implements BaseFuncIml, View.OnClickListener {

    public Context context;
    private CompositeDisposable mRxManager;
    private Dialog loadNetDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.getInstance().addActivity(this);
        //竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initParam();
        initView();
        initData();
        initListener();
    }

    @Override
    public void initParam() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
    }

    @Override
    public void onClick(View view) {

    }

    public void openActivity(Class<? extends Activity> toActivity) {
        openActivity(toActivity, null);
    }

    public void openActivity(Class<? extends Activity> toActivity, Bundle parameter) {
        if (ClickUtil.isFastDoubleClick()) return;
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> cls, Bundle bundle, int requestCode) {
        if (ClickUtil.isFastDoubleClick()) return;
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public CompositeDisposable getRxManager() {
        if (mRxManager == null)
            mRxManager = new CompositeDisposable(); // 管理订阅者
        return mRxManager;
    }

    public void showNetDialog() {
        if (loadNetDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_loading_net, null);
            loadNetDialog = new Dialog(this, R.style.LoadRequestDialog);
            loadNetDialog.setContentView(view);
            loadNetDialog.setCanceledOnTouchOutside(false);
        }
        if (!loadNetDialog.isShowing()) {
            loadNetDialog.show();
        }
    }

    public void hideNetDialog() {
        if (loadNetDialog != null && loadNetDialog.isShowing())
            loadNetDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRxManager != null) mRxManager.clear();
        ActivityUtil.getInstance().removeActivity(this);
    }

}
