package com.zhy.mvpgankio.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.base.fragment.BaseFragment;
import com.zhy.mvpgankio.common.base.fragment.BaseFrameFragment;
import com.zhy.mvpgankio.common.utils.GlideImageLoader;
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

    private boolean isViewCreated = false;
    private boolean isUIVisible = false;

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

}
