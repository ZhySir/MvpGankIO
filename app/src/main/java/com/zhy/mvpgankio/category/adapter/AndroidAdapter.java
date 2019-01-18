package com.zhy.mvpgankio.category.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.utils.DateUtil;

import java.util.List;

/**
 * Android知识模块Adapter
 * Created by zhy on 2019/1/18.
 */

public class AndroidAdapter extends RecyclerView.Adapter<AndroidAdapter.AndroidViewHolder> {

    private Context mContext;
    private List<AllCategoryBean.ResultsBean> mList;

    public AndroidAdapter(Context mContext, List<AllCategoryBean.ResultsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public AndroidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_android, parent, false);
        return new AndroidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AndroidViewHolder holder, final int position) {
        AllCategoryBean.ResultsBean resultsBean = mList.get(position);
        holder.tvTitle.setText(resultsBean.getDesc());
        holder.tvTime.setText(DateUtil.getUTCYMD(resultsBean.getCreatedAt()));
        if (resultsBean.getImages() != null && resultsBean.getImages().size() != 0) {
            holder.ivIMG.setVisibility(View.VISIBLE);
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.color.red_d72d2b)
                    .error(R.color.red_d72d2b);
            Glide.with(mContext)
                    .load(resultsBean.getImages().get(0))
                    .apply(requestOptions)
                    .into(holder.ivIMG);
        } else {
            holder.ivIMG.setVisibility(View.GONE);
        }

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

    public class AndroidViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;
        ImageView ivIMG;

        public AndroidViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_Title);
            tvTime = itemView.findViewById(R.id.tv_Time);
            ivIMG = itemView.findViewById(R.id.iv_IMG);
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
