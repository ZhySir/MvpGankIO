package com.zhy.mvpgankio.common.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhy on .
 */
public class BasePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFrag;

    public BasePagerAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.listFrag = mList;
    }

    @Override
    public Fragment getItem(int position) {
        return listFrag.get(position);
    }

    @Override
    public int getCount() {
        return listFrag.size();
    }

}
