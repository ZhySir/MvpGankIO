package com.zhy.mvpgankio.common.base.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.libutils.ClickUtil;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.base.BaseFuncIml;
import com.zhy.mvpgankio.common.view.fragment.FragmentUserVisibleController;

import io.reactivex.disposables.CompositeDisposable;

/**
 * BaseFragment
 * Created by zhy on 2016/12/31.
 */
public class BaseFragment extends Fragment implements BaseFuncIml, View.OnClickListener,
        FragmentUserVisibleController.UserVisibleCallback {

    private View mContentView;
    private ViewGroup container;
    private Dialog loadNetDialog;

    private CompositeDisposable mRxManager;
    /*
     * 处理Fragmnet窗口是否可见
     * 使用方法：直接实现onVisibleToUserChanged(boolean, boolean)根据isVisibleToUser判断即可
     * 如有更好的方法，可以直接替换
     */
    private final FragmentUserVisibleController userVisibleController;

    public BaseFragment() {
        userVisibleController = new FragmentUserVisibleController(this, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        return this.mContentView = getActivity().getLayoutInflater().inflate(getContentView(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userVisibleController.activityCreated();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initParam();
        initView();
        initData();
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public int getContentView() {
        return 0;
    }

    public CompositeDisposable getRxManager() {
        if (mRxManager == null)
            mRxManager = new CompositeDisposable(); // 管理订阅者
        return mRxManager;
    }

    @Override
    public void initParam() {
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
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
        Intent intent = new Intent(getContext(), toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> cls, Bundle bundle, int requestCode) {
        if (ClickUtil.isFastDoubleClick()) return;
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void showNetDialog() {
        if (loadNetDialog == null) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_loading_net, null);
            loadNetDialog = new Dialog(getActivity(), R.style.LoadRequestDialog);
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
    public void onResume() {
        super.onResume();
        userVisibleController.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        userVisibleController.pause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        userVisibleController.setUserVisibleHint(!hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        userVisibleController.setUserVisibleHint(isVisibleToUser);
    }

    public void setWaitingShowToUser(boolean waitingShowToUser) {
        userVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    public boolean isWaitingShowToUser() {
        return userVisibleController.isWaitingShowToUser();
    }

    @Override
    public boolean isVisibleToUser() {
        return userVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRxManager != null) mRxManager.clear();
    }

}
