package com.zhy.mvpgankio.category.fragment.android;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.libviews.recyclerView.MyRecyclerView;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.category.adapter.AndroidAdapter;
import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.Constants;
import com.zhy.mvpgankio.common.base.fragment.BaseFragment;
import com.zhy.mvpgankio.common.base.fragment.BaseFrameFragment;
import com.zhy.mvpgankio.page.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Android知识模块
 * Created by zhy on 2019/1/17.
 */
public class AndroidFragment extends BaseFrameFragment<AndroidPresenter, AndroidModel>
        implements AndroidContract.View {

    private MyRecyclerView mRecyclerView;
    private AndroidAdapter androidAdapter;
    private List<AllCategoryBean.ResultsBean> mList = new ArrayList<>();

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

    @Override
    public void initData() {
        super.initData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getAc());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        androidAdapter = new AndroidAdapter(getAc(), mList);
        mRecyclerView.setAdapter(androidAdapter);
        adapterListener();

        /* 默认进入查询 */
        mPresenter.getCategoryData("Android", Constants.PAGE_NUM, Constants.PAGE_SIZE, 0);
    }

    @Override
    public void onRefreshPage(List<AllCategoryBean.ResultsBean> list, int type) {
        mList.clear();
        if (type == 0) {
            mList.addAll(list);
            androidAdapter.notifyDataSetChanged();
        } else {

        }
    }

    /* Adapter点击事件 */
    private void adapterListener() {
        androidAdapter.setOnItemClickListener(new AndroidAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("title", mList.get(position).getDesc());
                bundle.putString("url", mList.get(position).getUrl());
                openActivity(WebActivity.class, bundle);
            }
        });
    }

}
