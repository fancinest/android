package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.AddBookCommentAct;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.BookComment;

import java.util.List;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName : 书评 item
 */

public class BookReviewAdapter extends EasyRecyclerAdapter<BookComment> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;

    public BookReviewAdapter(Context context, List<BookComment> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_book_review, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final BookComment item = getList().get(position);
        String url = item.getAuthorImg();
        if (!"".equals(url)) {
            Utils.setImgF(mContext, url, hold.ivIcon);
        } else {
            Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, hold.ivIcon);
        }
        hold.view_top.setVisibility(View.VISIBLE);
        hold.tvName.setText(Utils.getValue(item.getAuthor()) + "");
        hold.tvContent.setText(Utils.getValue(item.getContent()) + "");
        hold.tvDate.setText(Utils.dateDiff(Utils.stringTimeToMillion(Utils.getValue(item.getCreateTime()))) + "");
        hold.tvLike.setText(Utils.getValue(item.getLikeTimes()) + "");
        hold.lnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meItemInterface.OnItemClick(position);
            }
        });
        hold.tvComment.setText(Utils.getValue(item.getCommentTimes()) + "");
        hold.lnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AddBookCommentAct.class)
                        .putExtra("tag", 2)
                        .putExtra("bookId", item.getBookId()));
            }
        });
//        if (position == 0) {
//            hold.tvDel.setVisibility(View.VISIBLE);
//        } else hold.tvDel.setVisibility(View.GONE);
        hold.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meItemInterface.OnDelClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        SelectableRoundedImageView ivIcon;
        TextView tvName;
        TextView tvContent;
        TextView tvDate;
        LinearLayout lnDate;
        TextView tvComment;
        LinearLayout lnComment;
        TextView tvLike;
        LinearLayout lnLike;
        LinearLayout lnTool;
        View view_top;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_date);
            lnDate = itemView.findViewById(R.id.ln_date);

            tvComment = itemView.findViewById(R.id.tv_comment);
            lnComment = itemView.findViewById(R.id.ln_comment);
            tvLike = itemView.findViewById(R.id.tv_like);
            lnLike = itemView.findViewById(R.id.ln_like);
            lnTool = itemView.findViewById(R.id.ln_tool);
            view_top = itemView.findViewById(R.id.view_top);
        }
    }
}