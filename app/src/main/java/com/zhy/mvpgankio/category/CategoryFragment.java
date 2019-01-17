package com.zhy.mvpgankio.category;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.libviews.tabLayout.TabLayout;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.category.fragment.android.AndroidFragment;
import com.zhy.mvpgankio.category.fragment.expand.ExpandFragment;
import com.zhy.mvpgankio.category.fragment.frontEnd.FrontEndFragment;
import com.zhy.mvpgankio.category.fragment.ios.IosFragment;
import com.zhy.mvpgankio.common.base.adapter.BasePagerAdapter;
import com.zhy.mvpgankio.common.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类
 * Created by zhy on 2019/1/17.
 */
public class CategoryFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private BasePagerAdapter basePagerAdapter;

    private boolean isViewCreated = false;
    private boolean isUIVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isViewCreated = true;
        lazyInitData();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void initParam() {
        super.initParam();
        fragmentList.add(new AndroidFragment());
        fragmentList.add(new IosFragment());
        fragmentList.add(new FrontEndFragment());
        fragmentList.add(new ExpandFragment());
    }

    @Override
    public void initView() {
        super.initView();
        mTabLayout = getActivity().findViewById(R.id.tabLayout);
        mViewPager = getActivity().findViewById(R.id.viewPager);
    }

    /**
     * 替代父类initData()方法
     */
    private void lazyInitData() {
        if (!isUIVisible || !isViewCreated) return;
        isUIVisible = false;
        isViewCreated = false;

        basePagerAdapter = new BasePagerAdapter(getChildFragmentManager(), fragmentList);
        mViewPager.setAdapter(basePagerAdapter);
        mViewPager.setOffscreenPageLimit(fragmentList.size());

        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.android)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.ios)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.front_End)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.expand_Resources)));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(getResources().getString(R.string.android));
        mTabLayout.getTabAt(1).setText(getResources().getString(R.string.ios));
        mTabLayout.getTabAt(2).setText(getResources().getString(R.string.front_End));
        mTabLayout.getTabAt(3).setText(getResources().getString(R.string.expand_Resources));
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
