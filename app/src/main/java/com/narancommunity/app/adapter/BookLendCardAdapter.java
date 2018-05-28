package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.BookLendCardEntity;
import com.narancommunity.app.entity.LendRec;
import com.narancommunity.app.entity.MeFunctionEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName : 借书卡记录适配器
 */

public class BookLendCardAdapter extends EasyRecyclerAdapter<BookLendCardEntity> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;

    public BookLendCardAdapter(Context context, List<BookLendCardEntity> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_book_lend_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);
        BookLendCardEntity rec = getList().get(position);
        if (position == 0) {
            hold.ivDotRed.setVisibility(View.GONE);
            hold.ivDotNormal.setVisibility(View.VISIBLE);
            hold.ivDotNormal.setBackgroundResource(R.drawable.oval_blue);
            hold.tvDateLeft.setTextColor(mContext.getResources().getColor(R.color.appBlue));
            hold.tvDateLeft.setText(Utils.dateDiff(Utils.stringTimeToMillion(rec.getReturnTime())) + ""+ "预计还入");
            hold.tvNameLeft.setTextColor(mContext.getResources().getColor(R.color.appBlue));
            hold.tvNameLeft.setText(rec.getAccountNike());
            hold.tvDateLeft.setVisibility(View.VISIBLE);
            hold.tvNameLeft.setVisibility(View.VISIBLE);
            hold.tvDateRight.setVisibility(View.GONE);
            hold.tvNameRight.setVisibility(View.GONE);
        } else if (position == 1) {
            hold.ivDotRed.setVisibility(View.VISIBLE);
            hold.ivDotNormal.setVisibility(View.GONE);
            hold.tvDateRight.setTextColor(mContext.getResources().getColor(R.color.appRed));
            hold.tvDateRight.setText(Utils.dateDiff(Utils.stringTimeToMillion(rec.getReturnTime())) + "正在借阅...");
            hold.tvNameRight.setTextColor(mContext.getResources().getColor(R.color.appRed));
            hold.tvNameRight.setText(rec.getAccountNike());
            hold.tvDateRight.setVisibility(View.VISIBLE);
            hold.tvNameRight.setVisibility(View.VISIBLE);
            hold.tvDateLeft.setVisibility(View.GONE);
            hold.tvNameLeft.setVisibility(View.GONE);
        } else if (position % 2 == 0) {
            hold.ivDotRed.setVisibility(View.GONE);
            hold.ivDotNormal.setVisibility(View.VISIBLE);
            hold.ivDotNormal.setBackgroundResource(R.drawable.oval_gray);
            hold.tvDateLeft.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            hold.tvDateLeft.setText(Utils.dateDiff(Utils.stringTimeToMillion(rec.getReturnTime())) + "还入");
            hold.tvNameLeft.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            hold.tvNameLeft.setText(rec.getAccountNike());
            hold.tvDateLeft.setVisibility(View.VISIBLE);
            hold.tvNameLeft.setVisibility(View.VISIBLE);
            hold.tvDateRight.setVisibility(View.GONE);
            hold.tvNameRight.setVisibility(View.GONE);
        } else if (position % 2 == 1) {
            hold.ivDotRed.setVisibility(View.GONE);
            hold.ivDotNormal.setVisibility(View.VISIBLE);
            hold.ivDotNormal.setBackgroundResource(R.drawable.oval_gray);
            hold.tvDateRight.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            hold.tvDateRight.setText(Utils.dateDiff(Utils.stringTimeToMillion(rec.getReturnTime())) + "借出");
            hold.tvNameRight.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            hold.tvNameRight.setText(rec.getAccountNike());
            hold.tvDateRight.setVisibility(View.VISIBLE);
            hold.tvNameRight.setVisibility(View.VISIBLE);
            hold.tvDateLeft.setVisibility(View.GONE);
            hold.tvNameLeft.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name_left)
        TextView tvNameLeft;
        @BindView(R.id.tv_date_left)
        TextView tvDateLeft;
        @BindView(R.id.iv_dot_normal)
        ImageView ivDotNormal;
        @BindView(R.id.iv_dot_red)
        ImageView ivDotRed;
        @BindView(R.id.tv_name_right)
        TextView tvNameRight;
        @BindView(R.id.tv_date_right)
        TextView tvDateRight;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameLeft = itemView.findViewById(R.id.tv_name_left);
            tvDateLeft = itemView.findViewById(R.id.tv_date_left);
            ivDotNormal = itemView.findViewById(R.id.iv_dot_normal);
            ivDotRed = itemView.findViewById(R.id.iv_dot_red);
            tvNameRight = itemView.findViewById(R.id.tv_name_right);
            tvDateRight = itemView.findViewById(R.id.tv_date_right);
        }
    }
}
