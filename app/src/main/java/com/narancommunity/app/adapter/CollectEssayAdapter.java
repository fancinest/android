package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.book.BookYSHYDetailAct;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.YSHYEntity;

import java.util.List;

/**
 * Writer：fancy on 2018/3/12 17:12
 * Email：120760202@qq.com
 * FileName :
 */

public class CollectEssayAdapter extends EasyRecyclerAdapter<YSHYEntity> {
    MeItemInterface meItemInterface;

    public CollectEssayAdapter(Context context, List<YSHYEntity> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(getLayout(), parent, false);
        return new MyViewHolder(view);
    }

    private int getLayout() {
        return R.layout.item_my_collect_essay;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) holder;

        final YSHYEntity item = getList().get(position);
        hold.tvTitle.setText("" + Utils.getValue(item.getContentTitle()));
        hold.tvContent.setText("" + Utils.getValue(item.getContentBody()));
        hold.tvCollect.setText("" + Utils.getValue(item.getCollectTimes()));
        hold.tvComment.setText("" + Utils.getValue(item.getCommentTimes()));
        hold.tvLike.setText("" + Utils.getValue(item.getLikeTimes()));
        hold.tvDate.setText(Utils.dateDiff(Utils.stringTimeToMillion(item.getCreateTime())) + "");
        String pic = Utils.getValue(item.getContentImg());
        if (pic.contains(",")) {
            String[] arrPic = pic.split(",", -1);
            setImgs(arrPic, hold.recyclerViewPic);
            hold.ivOnePic.setVisibility(View.GONE);
            hold.recyclerViewPic.setVisibility(View.VISIBLE);
        } else {
            Utils.setImgF(mContext, pic, hold.ivOnePic);
            hold.ivOnePic.setVisibility(View.VISIBLE);
            hold.recyclerViewPic.setVisibility(View.GONE);
        }

        hold.lnTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BookYSHYDetailAct.class)
                        .putExtra("data", item));
            }
        });
    }

    private void setImgs(String[] arrPic, RecyclerView recyclerViewPic) {
        EssayPicAdapter picAdapter;
        GridLayoutManager linearLayout = new GridLayoutManager(getContext(), 3);
        linearLayout.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewPic.setLayoutManager(linearLayout);

        picAdapter = new EssayPicAdapter(getContext());
        picAdapter.setDataList(Utils.arrayToList(arrPic));
        picAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO 查看大图
            }
        });
        recyclerViewPic.setAdapter(picAdapter);
        picAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvContent;
        RecyclerView recyclerViewPic;
        ImageView ivOnePic;
        LinearLayout lnPic;
        TextView tvDate;
        LinearLayout lnDate;
        TextView tvLike;
        TextView tvComment;
        TextView tvCollect;
        LinearLayout lnTool;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCollect = itemView.findViewById(R.id.tv_collect);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvContent = itemView.findViewById(R.id.tv_content);
            recyclerViewPic = itemView.findViewById(R.id.recyclerView_pic);
            ivOnePic = itemView.findViewById(R.id.iv_one_pic);
            lnPic = itemView.findViewById(R.id.ln_pic);
            tvDate = itemView.findViewById(R.id.tv_date);
            lnDate = itemView.findViewById(R.id.ln_date);
            tvLike = itemView.findViewById(R.id.tv_like);
            lnTool = itemView.findViewById(R.id.ln_tool);
        }
    }
}
