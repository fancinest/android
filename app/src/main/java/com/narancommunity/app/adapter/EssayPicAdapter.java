package com.narancommunity.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.narancommunity.app.R;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class EssayPicAdapter extends ListBaseAdapter<String> {
    OnItemClickListener listener;
    boolean isSearch = false;

    public EssayPicAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_essay_pic;
    }

    @Override
    public int getItemCount() {
        if (super.getItemCount() > 9)
            return 9;
        else
            return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        final String entity = Utils.getValue(mDataList.get(position));
        ImageView pic = holder.getView(R.id.iv_img);
        if (!entity.equals("")) {
            Utils.setImgF(mContext, entity, pic);
        } else {
            Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, pic);
        }
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });

    }
}
