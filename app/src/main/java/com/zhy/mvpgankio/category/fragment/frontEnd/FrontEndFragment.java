package com.zhy.mvpgankio.category.fragment.frontEnd;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.libviews.recyclerView.MyRecyclerView;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.category.adapter.CategoryAdapter;
import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.base.fragment.BaseFrameFragment;
import com.zhy.mvpgankio.page.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端知识模块
 * Created by zhy on 2019/1/17.
 */
public class FrontEndFragment extends BaseFrameFragment<FrontEndPresenter, FrontEndModel>
        implements FrontEndContract.View {

    private SmartRefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<AllCategoryBean.ResultsBean> mList = new ArrayList<>();

    private boolean isViewCreated = false;
    private boolean isUIVisible = false;
    private int PAGE_NUM = 1;//页数
    private int PAGE_SIZE = 20;//每页数量

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isViewCreated = true;
        lazyInitData();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_front_end, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        mRefreshLayout = getAc().findViewById(R.id.mRefreshLayout_FrontEnd);
        mRecyclerView = getAc().findViewById(R.id.mRecyclerView_FrontEnd);
    }

    /**
     * 替代父类initData()方法
     */
    private void lazyInitData() {
        if (!isUIVisible || !isViewCreated) return;
        isUIVisible = false;
        isViewCreated = false;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getAc());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(getAc(), mList);
        mRecyclerView.setAdapter(categoryAdapter);
        adapterListener();

        /* 默认进入查询 */
        mPresenter.getCategoryData("前端", PAGE_NUM, PAGE_SIZE, 0);
    }

    @Override
    public void initListener() {
        super.initListener();

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PAGE_NUM = 1;
                /* 刷新查询 */
                mPresenter.getCategoryData("前端", PAGE_NUM, PAGE_SIZE, 1);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                PAGE_NUM = PAGE_NUM + 1;
                /* 加载更多查询 */
                mPresenter.getCategoryData("前端", PAGE_NUM, PAGE_SIZE, 2);
            }
        });
    }

    @Override
    public void onRefreshPage(List<AllCategoryBean.ResultsBean> list, int type) {
        //不知无数据结构是什么样子,所以没有判断
        mList.clear();
        if (type == 0) {
            mList.addAll(list);
            categoryAdapter.notifyDataSetChanged();
        } else {
            mList.addAll(list);
            categoryAdapter.notifyDataSetChanged();
            mRefreshLayout.finishRefresh(800);
        }
    }

    @Override
    public void onLoadMorePage(List<AllCategoryBean.ResultsBean> list) {
        mList.addAll(list);
        categoryAdapter.notifyDataSetChanged();
        if (list.size() >= PAGE_SIZE) {
            mRefreshLayout.finishLoadMore(800);
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    /* Adapter点击事件 */
    private void adapterListener() {
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
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

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        super.onVisibleToUserChanged(isVisibleToUser, invokeInResumeOrPause);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyInitData();
        }
    }

}
