package com.zhy.mvpgankio.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.libviews.recyclerView.MyRecyclerView;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.base.fragment.BaseFrameFragment;
import com.zhy.mvpgankio.common.utils.GlideImageLoader;
import com.zhy.mvpgankio.common.utils.ToastUtil;
import com.zhy.mvpgankio.home.adapter.AllCategoryAdapter;
import com.zhy.mvpgankio.page.web.WebActivity;
import com.zhy.mvpgankio.welfare.bean.AllWelfareBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by zhy on 2019/1/17.
 */
public class HomeFragment extends BaseFrameFragment<HomePresenter, HomeModel>
        implements HomeContract.View {

    private Banner mBanner;
    private SmartRefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private AllCategoryAdapter allCategoryAdapter;
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        mBanner = getAc().findViewById(R.id.mBanner);
        mRefreshLayout = getAc().findViewById(R.id.mRefreshLayout_Home);
        mRecyclerView = getAc().findViewById(R.id.mRecyclerView_Home);
    }

    /**
     * 替代父类initData()方法
     */
    private void lazyInitData() {
        if (!isUIVisible || !isViewCreated) return;
        isUIVisible = false;
        isViewCreated = false;

        /* 获取福利图片 */
        mPresenter.getWelfareData("福利", 1, 5);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getAc());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        allCategoryAdapter = new AllCategoryAdapter(getAc(), mList);
        mRecyclerView.setAdapter(allCategoryAdapter);
        adapterListener();
        /* 默认进入查询 */
        mPresenter.getCategoryData("all", PAGE_NUM, PAGE_SIZE, 0);
    }

    @Override
    public void initListener() {
        super.initListener();

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PAGE_NUM = 1;
                /* 刷新查询 */
                mPresenter.getCategoryData("all", PAGE_NUM, PAGE_SIZE, 1);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                PAGE_NUM = PAGE_NUM + 1;
                /* 加载更多查询 */
                mPresenter.getCategoryData("all", PAGE_NUM, PAGE_SIZE, 2);
            }
        });
    }

    @Override
    public void onWelfareData(List<AllWelfareBean.ResultsBean> list) {
        if (list == null || list.size() == 0) return;
        //循环取出url
        List<String> listStr = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listStr.add(list.get(i).getUrl());
        }
        //设置banner样式 默认样式为BannerConfig.CIRCLE_INDICATOR
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(listStr);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播 默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(1500);
        //设置指示器位置
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    public void onRefreshPage(List<AllCategoryBean.ResultsBean> list, int type) {
        //不知无数据结构是什么样子,所以没有判断
        mList.clear();
        if (type == 0) {
            mList.addAll(list);
            allCategoryAdapter.notifyDataSetChanged();
        } else {
            mList.addAll(list);
            allCategoryAdapter.notifyDataSetChanged();
            mRefreshLayout.finishRefresh(800);
        }
    }

    @Override
    public void onLoadMorePage(List<AllCategoryBean.ResultsBean> list) {
        mList.addAll(list);
        allCategoryAdapter.notifyDataSetChanged();
        if (list.size() >= PAGE_SIZE) {
            mRefreshLayout.finishLoadMore(800);
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止轮播
        mBanner.stopAutoPlay();
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        super.onVisibleToUserChanged(isVisibleToUser, invokeInResumeOrPause);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyInitData();
        }
    }

    /* Adapter点击事件 */
    private void adapterListener() {
        allCategoryAdapter.setOnItemClickListener(new AllCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                if ("福利".equals(mList.get(position).getType())) {
                    ToastUtil.showToast("点中了妹纸!");
                } else {
                    bundle.putInt("type", 1);
                    bundle.putString("title", mList.get(position).getDesc());
                    bundle.putString("url", mList.get(position).getUrl());
                    openActivity(WebActivity.class, bundle);
                }
            }
        });
    }

}
