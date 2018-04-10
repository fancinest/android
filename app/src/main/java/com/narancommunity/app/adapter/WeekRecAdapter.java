package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.WeekRecEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Writer：fancy on 2017/12/26 15:15
 * Email：120760202@qq.com
 * FileName :每周推荐
 */

public class WeekRecAdapter extends EasyRecyclerAdapter<WeekRecEntity> {
    MeItemInterface meItemInterface;
    int tag = 0;

    public WeekRecAdapter(Context context, List<WeekRecEntity> list, int tag) {
        super(context, list);
        this.tag = tag;
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(getLayout(), parent, false);
        return new MyViewHolder(view);
    }

    int getLayout() {
        if (tag == 0) {
            return R.layout.item_week_rec;
        } else if (tag == 2)
            return R.layout.item_special_report;
        else
            return R.layout.item_special_report;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final WeekRecEntity item = getList().get(position);
        if (item == null)
            return;
        hold.tvTitle.setText(Utils.getValue(item.getContentTitle()) + "");
        hold.tvContent.setText(Utils.getValue(item.getContentBody()));
        hold.tvDate.setText(Utils.dateDiff(Utils.stringTimeToMillion(item.getCreateTime())));
        if (tag == 0) {
            String url = item.getContentImg();
            if (url != null && !url.equals("")) {
                Utils.setImgF(mContext, url, hold.ivImg);
            }
            hold.tvWeek.setText(Utils.getValue(item.getRecommendTime()) + "");
        } else if (tag == 2) {
            String url = item.getContentImg();
            if (url != null && !url.equals("")) {
                Utils.setImgF(mContext, url, hold.ivImg);
            }
        }
        hold.lnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, SortSearchAct.class)
//                        .putExtra("type", Utils.getValue(item.getClassifyName())));
                meItemInterface.OnItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvWeek;
        TextView tvTitle;
        TextView tvDate;
        TextView tvRead;
        LinearLayout lnParent;
        ImageView ivImg;
        TextView tvContent;

        RelativeLayout rlPic;
        TextView tvShare;
        TextView tvComment;
        TextView tvCollect;

        public MyViewHolder(View itemView) {
            super(itemView);
            lnParent = itemView.findViewById(R.id.ln_parent);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            if (tag == 0) {
                ivImg = itemView.findViewById(R.id.iv_img);
                tvWeek = itemView.findViewById(R.id.tv_week);
                tvRead = itemView.findViewById(R.id.tv_read);
            } else {
                ivImg = itemView.findViewById(R.id.iv_bg);
                rlPic = itemView.findViewById(R.id.rl_pic);
                tvShare = itemView.findViewById(R.id.tv_share);
                tvComment = itemView.findViewById(R.id.tv_comment);
                tvCollect = itemView.findViewById(R.id.tv_collect);
            }
        }
    }
}
