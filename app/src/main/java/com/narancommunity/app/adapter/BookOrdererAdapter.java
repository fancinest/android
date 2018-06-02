package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.book.BookOrdererListAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.OrderEntity;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class BookOrdererAdapter extends ListBaseAdapter<OrderEntity> {
    OnItemClickListener listener;

    public BookOrdererAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_orderer;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        final OrderEntity entity = getDataList().get(position);
        ImageView iv_img = holder.getView(R.id.iv);
        String url = Utils.getValue(entity.getAccountImg());
        if (!"".equals(url)) {
            Utils.setImgF(mContext, url, iv_img);
        } else
            Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, iv_img);
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BookOrdererListAct.class).putExtra("bookId",entity.getOrderId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (super.getItemCount() < 5)
            return super.getItemCount();
        else
            return 5;
    }
}
