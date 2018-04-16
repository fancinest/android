package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.BookOrdererListAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookOrderer;

import okhttp3.internal.Util;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class BookOrderListAdapter extends ListBaseAdapter<BookOrderer> {
    OnItemClickListener listener;

    public BookOrderListAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_order_list;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        BookOrderer entity = getDataList().get(position);
        View ivFirst = holder.getView(R.id.iv_first);
        if (position == 0)
            ivFirst.setVisibility(View.VISIBLE);
        else
            ivFirst.setVisibility(View.GONE);
        TextView tvDate = holder.getView(R.id.tv_date);
        tvDate.setText("借阅时间：" + Utils.getValue(entity.getDate()));
        ImageView iv_img = holder.getView(R.id.iv_icon);
        if (null != entity.getUrl() && !"".equals(entity.getUrl())) {
            Utils.setImgF(mContext, entity.getUrl(), iv_img);
        } else
            Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, iv_img);
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, BookOrdererListAct.class));
            }
        });
    }
}
