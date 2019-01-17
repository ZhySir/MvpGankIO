package com.zhy.mvpgankio.category.fragment.ios;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.libviews.recyclerView.MyRecyclerView;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.base.fragment.BaseFrameFragment;

/**
 * Ios知识模块
 * Created by zhy on 2019/1/17.
 */
public class IosFragment extends BaseFrameFragment<IosPresenter, IosModel>
        implements IosContract.View {

    private MyRecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ios, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView = getAc().findViewById(R.id.mRecyclerView_Ios);
    }
}
