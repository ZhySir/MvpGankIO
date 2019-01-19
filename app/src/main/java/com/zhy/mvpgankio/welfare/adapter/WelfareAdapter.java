package com.zhy.mvpgankio.welfare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.libutils.ScreenUtil;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.utils.DateUtil;
import com.zhy.mvpgankio.welfare.bean.AllWelfareBean;

import java.util.List;

/**
 * 福利模块Adapter
 * Created by zhy on 2019/1/19.
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.WelfareViewHolder> {

    private Context mContext;
    private List<AllWelfareBean.ResultsBean> mList;

    public WelfareAdapter(Context mContext, List<AllWelfareBean.ResultsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public WelfareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_welfare, parent, false);
        return new WelfareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WelfareViewHolder holder, final int position) {
        AllWelfareBean.ResultsBean resultsBean = mList.get(position);

        holder.ivIMG.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, position % 2 == 0 ? 600 : 800));

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.color.red_d72d2b)
                .error(R.color.red_d72d2b);
        Glide.with(mContext)
                .load(resultsBean.getUrl())
                .apply(requestOptions)
                .into(holder.ivIMG);
        holder.tvTime.setText(DateUtil.getUTCYMD(resultsBean.getCreatedAt()));


        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class WelfareViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIMG;
        TextView tvTime;

        public WelfareViewHolder(View itemView) {
            super(itemView);
            ivIMG = itemView.findViewById(R.id.iv_IMG);
            tvTime = itemView.findViewById(R.id.tv_Time);
        }
    }

    //============================interface============================
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
