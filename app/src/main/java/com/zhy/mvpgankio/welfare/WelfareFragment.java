package com.zhy.mvpgankio.welfare;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.libviews.recyclerView.MyRecyclerView;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.base.fragment.BaseFrameFragment;
import com.zhy.mvpgankio.common.utils.ToastUtil;
import com.zhy.mvpgankio.common.view.StaggeredDividerItemDecoration;
import com.zhy.mvpgankio.welfare.adapter.WelfareAdapter;
import com.zhy.mvpgankio.welfare.bean.AllWelfareBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 福利
 * Created by zhy on 2019/1/17.
 */
public class WelfareFragment extends BaseFrameFragment<WelfarePresenter, WelfareModel>
        implements WelfareContract.View {

    private SmartRefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private WelfareAdapter welfareAdapter;
    private List<AllWelfareBean.ResultsBean> mList = new ArrayList<>();

    private boolean isViewCreated = false;
    private boolean isUIVisible = false;
    private int PAGE_NUM = 1;//页数
    private int PAGE_SIZE = 10;//每页数量

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isViewCreated = true;
        lazyInitData();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welfare, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        mRefreshLayout = getAc().findViewById(R.id.mRefreshLayout_Welfare);
        mRecyclerView = getAc().findViewById(R.id.mRecyclerView_Welfare);
    }

    /**
     * 替代父类initData()方法
     */
    private void lazyInitData() {
        if (!isUIVisible || !isViewCreated) return;
        isUIVisible = false;
        isViewCreated = false;

        final StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        //解决item跳动
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        //ItemDecoration
        mRecyclerView.addItemDecoration(new StaggeredDividerItemDecoration(getAc(), 5));
        //addOnScrollListener
        final int spanCount = 2;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
                int[] first = new int[spanCount];
                staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    staggeredGridLayoutManager.invalidateSpanAssignments();
                }
            }
        });
        welfareAdapter = new WelfareAdapter(getAc(), mList);
        mRecyclerView.setAdapter(welfareAdapter);
        adapterListener();
        /* 默认进入查询 */
        mPresenter.getWelfareData("福利", PAGE_NUM, PAGE_SIZE, 0);
    }

    @Override
    public void initListener() {
        super.initListener();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PAGE_NUM = 1;
                /* 刷新查询 */
                mPresenter.getWelfareData("福利", PAGE_NUM, PAGE_SIZE, 1);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                PAGE_NUM = PAGE_NUM + 1;
                /* 加载更多查询 */
                mPresenter.getWelfareData("福利", PAGE_NUM, PAGE_SIZE, 2);
            }
        });
    }

    @Override
    public void onRefreshPage(List<AllWelfareBean.ResultsBean> list, int type) {
        //不知无数据结构是什么样子,所以没有判断
        mList.clear();
        if (type == 0) {
            mList.addAll(list);
            welfareAdapter.notifyItemRangeChanged(0, mList.size());
        } else {
            mList.addAll(list);
            welfareAdapter.notifyItemRangeChanged(0, mList.size());
            mRefreshLayout.finishRefresh(800);
        }
    }

    @Override
    public void onLoadMorePage(List<AllWelfareBean.ResultsBean> list) {
        int start = mList.size();
        mList.addAll(list);
        welfareAdapter.notifyItemRangeInserted(start, mList.size());
        if (list.size() >= PAGE_SIZE) {
            mRefreshLayout.finishLoadMore(800);
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    /* Adapter点击事件 */
    private void adapterListener() {
        welfareAdapter.setOnItemClickListener(new WelfareAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast("挑中了" + position + "号妹纸!");
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
