package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.BookDetailAct;
import com.narancommunity.app.activity.mine.DeliverBookAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.WishEntity;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class MyLoveAdapter extends ListBaseAdapter<WishEntity> {
    MeItemInterface listener;
    int type;

    public MyLoveAdapter(Context context) {
        super(context);
    }


    public void setListener(MeItemInterface listener) {
        this.listener = listener;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_my_love;
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
        TextView tvWirter = holder.getView(R.id.tv_writer);
        TextView tvOption = holder.getView(R.id.tv_option);
        TextView tvDesc = holder.getView(R.id.tv_desc);

        tvDesc.setText("" + Utils.getValue(entity.getOrderContent()));
        tvWirter.setText("" + Utils.getValue(entity.getOrderAuthor()));
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
            tvOption.setVisibility(View.VISIBLE);
            tvOption.setText("传书给TA");
        } else if (type == 1) {
            lnDonator.setVisibility(View.GONE);
            tvOption.setVisibility(View.VISIBLE);
            tvOption.setText("等待确认");
        } else if (type == 2) {
            lnDonator.setVisibility(View.GONE);
            tvOption.setVisibility(View.GONE);
        }
        tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(position);
            }
        });
        lnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnDelClick(position);
            }
        });
    }
}
