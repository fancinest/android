//package com.narancommunity.app.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.joooonho.SelectableRoundedImageView;
//import com.narancommunity.app.MeItemInterface;
//import com.narancommunity.app.R;
//import com.narancommunity.app.activity.index.book.BookReviewDetailAct;
//import com.narancommunity.app.common.Utils;
//import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
//import com.narancommunity.app.entity.BookComment;
//
//import java.util.List;
//
///**
// * Writer：fancy on 2017/5/9 10:59
// * Email：120760202@qq.com
// * FileName : 评论 item
// */
//
//public class BookCommentAdapter extends EasyRecyclerAdapter<BookComment> {
//    boolean isLimited = false;
//    MeItemInterface meItemInterface;
//
//    public BookCommentAdapter(Context context, List<BookComment> list) {
//        super(context, list);
//    }
//
//    public void setListener(MeItemInterface meItemInterface) {
//        this.meItemInterface = meItemInterface;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_book_comment, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        final MyViewHolder hold = (MyViewHolder) (holder);
//
//        final BookComment item = getList().get(position);
//        String url = item.getAuthorImg();
//        if (!"".equals(url)) {
//            Utils.setImgF(mContext, url, hold.ivIcon);
//        } else {
//            Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, hold.ivIcon);
//        }
//        hold.tvName.setText(Utils.getValue(item.getAuthor()));
//        hold.tvContent.setText(Utils.getValue(item.getContent()));
////        hold.tvDate.setText(Utils.dateDiff(Utils.stringTimeToMillion(item.get)) + "");
//        hold.tvComment.setText(Utils.getValue(item.getContent()) + "");
////        hold.tvLike.setText(Utils.getValue(item.get) + "");
//        hold.lnComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                meItemInterface.OnItemClick(position);
//            }
//        });
//        hold.lnLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                meItemInterface.OnDelClick(position);
//            }
//        });
//        hold.tvContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, BookReviewDetailAct.class));
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return getList().size();
//    }
//
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//
//        SelectableRoundedImageView ivIcon;
//        TextView tvName;
//        TextView tvContent;
//        TextView tvDate;
//        TextView tvComment;
//        LinearLayout lnComment;
//        TextView tvLike;
//        LinearLayout lnLike;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            ivIcon = itemView.findViewById(R.id.iv_icon);
//            tvName = itemView.findViewById(R.id.tv_name);
//            tvContent = itemView.findViewById(R.id.tv_content);
//            tvDate = itemView.findViewById(R.id.tv_date);
//            tvComment = itemView.findViewById(R.id.tv_comment);
//            tvLike = itemView.findViewById(R.id.tv_like);
//            lnComment = itemView.findViewById(R.id.ln_comment);
//            lnLike = itemView.findViewById(R.id.ln_like);
//        }
//    }
//}
