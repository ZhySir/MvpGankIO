package com.zhy.mvpgankio.category.fragment.android;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.libviews.recyclerView.MyRecyclerView;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.base.fragment.BaseFragment;
import com.zhy.mvpgankio.common.base.fragment.BaseFrameFragment;

/**
 * Android知识模块
 * Created by zhy on 2019/1/17.
 */
public class AndroidFragment extends BaseFrameFragment<AndroidPresenter, AndroidModel>
        implements AndroidContract.View {

    private MyRecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_android, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView = getAc().findViewById(R.id.mRecyclerView_Android);
    }
}
