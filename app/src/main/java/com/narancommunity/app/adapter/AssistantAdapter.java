package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.AssistantMissionEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Writer：fancy on 2017/12/26 15:15
 * Email：120760202@qq.com
 * FileName :
 */

public class AssistantAdapter extends EasyRecyclerAdapter<AssistantMissionEntity> {
    MeItemInterface meItemInterface;

    public AssistantAdapter(Context context, List<AssistantMissionEntity> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_assistant_mission, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final AssistantMissionEntity item = getList().get(position);
//        hold.tv_name.setText(Utils.getValue(item.getName()) + "");
//        int url = item.getId();
//        hold.iv_pic.setImageResource(url);
//        Utils.setImgF(mContext, url, hold.iv_pic);
        hold.tvTitle.setText("" + Utils.getValue(item.getActivityTitle()));
        hold.tvShare.setText("" + Utils.getValue(item.getShareTimes()));
        hold.tvTime.setText(Utils.dateDiff(Utils.stringTimeToMillion(item.getCreateTime())) + "");
        String url = Utils.getValue(item.getActivityImg());
        if (null != url && !"".equals(url)) {
            Utils.setImgF(getContext(), url, hold.ivBg);
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

        ImageView ivBg;
        TextView tvShare;
        TextView tvComment;
        TextView tvCollect;
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            lnParent = itemView.findViewById(R.id.ln_parent);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivBg = itemView.findViewById(R.id.iv_bg);
            tvShare = itemView.findViewById(R.id.tv_share);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvCollect = itemView.findViewById(R.id.tv_collect);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
