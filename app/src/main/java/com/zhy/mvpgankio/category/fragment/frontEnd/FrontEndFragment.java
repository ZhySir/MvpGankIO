package com.zhy.mvpgankio.category.fragment.frontEnd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.libviews.recyclerView.MyRecyclerView;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.base.fragment.BaseFrameFragment;

/**
 * 前端知识模块
 * Created by zhy on 2019/1/17.
 */
public class FrontEndFragment extends BaseFrameFragment<FrontEndPresenter, FrontEndModel>
        implements FrontEndContract.View {

    private MyRecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_front_end, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView = getAc().findViewById(R.id.mRecyclerView_FrontEnd);
    }
}
