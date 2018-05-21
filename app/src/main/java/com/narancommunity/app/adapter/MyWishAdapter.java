package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.BookDetailAct;
import com.narancommunity.app.activity.mine.LookBookStateAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.MyWishData;
import com.narancommunity.app.entity.RecEntity;
import com.narancommunity.app.entity.WishEntity;

import butterknife.BindView;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class MyWishAdapter extends ListBaseAdapter<WishEntity> {
    OnItemClickListener listener;
    int type;

    public MyWishAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_my_wish;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        final WishEntity entity = mDataList.get(position);
        if (entity == null)
            return;

        LinearLayout lnParent = holder.getView(R.id.ln_parent);
        LinearLayout lnDonator = holder.getView(R.id.ln_donator);

        ImageView ivBook = holder.getView(R.id.iv_book);
        ImageView ivDonator = holder.getView(R.id.iv_img);

        TextView tvDonator = holder.getView(R.id.tv_donator);
        TextView tvTitle = holder.getView(R.id.tv_title);
        TextView tvOption = holder.getView(R.id.tv_option);
        TextView tvContent = holder.getView(R.id.tv_content);

        tvContent.setText("" + Utils.getValue(entity.getOrderContent()));
        tvDonator.setText("" + Utils.getValue(entity.getInitiatorNike()));
        tvTitle.setText("" + Utils.getValue(entity.getOrderTitle()));

        String bookImg = Utils.getValue(entity.getOrderImgs());
        String donatorImg = Utils.getValue(entity.getInitiatorImg());
        if (!bookImg.equals("")) {
            Utils.setImgF(mContext, bookImg, ivBook);
            ivBook.setVisibility(View.VISIBLE);
        } else {
            ivBook.setVisibility(View.GONE);
            Utils.setImgF(mContext, R.drawable.ic_launcher_foreground, ivBook);
        }
        if (!donatorImg.equals(""))
            Utils.setImgF(mContext, donatorImg, ivDonator);
        else
            Utils.setImgF(mContext, R.drawable.ic_launcher_foreground, ivDonator);
        if (type == 0) {
            lnDonator.setVisibility(View.VISIBLE);
            tvOption.setVisibility(View.GONE);
        } else if (type == 1) {
            lnDonator.setVisibility(View.GONE);
            tvOption.setVisibility(View.VISIBLE);
            tvOption.setText("查看");
        } else if (type == 2) {
            lnDonator.setVisibility(View.VISIBLE);
            tvOption.setVisibility(View.VISIBLE);
            tvOption.setText("预约");
        }

        lnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
        tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1)
                    mContext.startActivity(new Intent(mContext, LookBookStateAct.class)
                            .putExtra("bookId", entity.getOrderId()));
                else mContext.startActivity(new Intent(mContext, BookDetailAct.class)
                        .putExtra("bookId", entity.getOrderId()));
            }
        });
    }
}
