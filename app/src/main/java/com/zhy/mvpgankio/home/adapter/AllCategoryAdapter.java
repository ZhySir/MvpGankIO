package com.zhy.mvpgankio.home.adapter;

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
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.utils.DateUtil;

import java.util.List;

/**
 * 首页所有分类Adapter
 * Created by zhy on 2019/5/9.
 */
public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder> {

    private Context mContext;
    private List<AllCategoryBean.ResultsBean> mList;

    public AllCategoryAdapter(Context mContext, List<AllCategoryBean.ResultsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public AllCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_all_category, parent, false);
        return new AllCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllCategoryViewHolder holder, final int position) {
        AllCategoryBean.ResultsBean resultsBean = mList.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.color.red_d72d2b)
                .error(R.color.red_d72d2b);

        if ("福利".equals(resultsBean.getType())) {
            holder.llx1.setVisibility(View.GONE);
            holder.ivWelfareIMG.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(resultsBean.getUrl())
                    .apply(requestOptions)
                    .into(holder.ivWelfareIMG);
        } else {
            holder.ivWelfareIMG.setVisibility(View.GONE);
            holder.llx1.setVisibility(View.VISIBLE);

            holder.tvTitle.setText(resultsBean.getDesc());
            holder.tvCategory.setText(resultsBean.getType());
            holder.tvTime.setText(DateUtil.getUTCYMD(resultsBean.getCreatedAt()));
            if (resultsBean.getImages() != null && resultsBean.getImages().size() != 0) {
                holder.ivIMG.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(resultsBean.getImages().get(0))
                        .apply(requestOptions)
                        .into(holder.ivIMG);
            } else {
                holder.ivIMG.setVisibility(View.GONE);
            }
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

    public class AllCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;//标题
        TextView tvCategory;//分类名
        TextView tvTime;//时间
        ImageView ivIMG;
        LinearLayout llx1;//样式1
        ImageView ivWelfareIMG;//福利大图

        public AllCategoryViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_Title);
            tvCategory = itemView.findViewById(R.id.tv_Category);
            tvTime = itemView.findViewById(R.id.tv_Time);
            ivIMG = itemView.findViewById(R.id.iv_IMG);
            llx1 = itemView.findViewById(R.id.ll_x1);
            ivWelfareIMG = itemView.findViewById(R.id.iv_Welfare_IMG);
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
