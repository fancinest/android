package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.book.BookDetailAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.RecEntity;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :我的收藏-捐赠
 */
public class CollectDonateAdapter extends ListBaseAdapter<RecEntity> {
    OnItemClickListener listener;

    public CollectDonateAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
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
        final RecEntity entity = mDataList.get(position);
        if (entity == null)
            return;

        LinearLayout lnDonator = holder.getView(R.id.ln_donator);
        LinearLayout lnParent = holder.getView(R.id.ln_parent);
        ImageView ivBook = holder.getView(R.id.iv_book);
        SelectableRoundedImageView ivImg = holder.getView(R.id.iv_img);

        TextView tvTitle = holder.getView(R.id.tv_title);
        TextView tvAuthor = holder.getView(R.id.tv_author);
        TextView tvContent = holder.getView(R.id.tv_content);
        TextView tvDonator = holder.getView(R.id.tv_donator);
        TextView tvOption = holder.getView(R.id.tv_option);
        tvOption.setVisibility(View.GONE);

        tvTitle.setText("" + Utils.getValue(entity.getOrderTitle()));
        tvAuthor.setText("" + Utils.getValue(entity.getOrderAuthor()));
        tvContent.setText("" + Utils.getValue(entity.getOrderContent()));
        tvDonator.setText("" + Utils.getValue(entity.getInitiatorNike()));

        if (!"".equals(Utils.getValue(entity.getOrderImgs()))) {
            Utils.setImgF(mContext, entity.getOrderImgs(), ivBook);
        } else {
            Utils.setImgF(mContext, R.mipmap.bitmap_book, ivBook);
        }

        if (!"".equals(Utils.getValue(entity.getInitiatorImg()))) {
            Utils.setImgF(mContext, entity.getOrderImgs(), ivImg);
        } else {
            Utils.setImgF(mContext, R.mipmap.bitmap_shequliebiao, ivImg);
        }

        lnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onItemClick(position);
                mContext.startActivity(new Intent(mContext, BookDetailAct.class)
                        .putExtra("bookId", entity.getOrderId()));
            }
        });
    }
}
