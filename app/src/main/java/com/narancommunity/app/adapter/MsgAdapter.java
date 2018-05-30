package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.R;
import com.narancommunity.app.activity.mine.MsgDetailAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.MsgEntity;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class MsgAdapter extends ListBaseAdapter<MsgEntity> {
    OnItemClickListener listener;

    public MsgAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_msg;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final MsgEntity entity = mDataList.get(position);
        if (entity == null)
            return;

        TextView tvTime = holder.getView(R.id.tv_time);
        TextView tvContent = holder.getView(R.id.tv_content);
        ImageView iv = holder.getView(R.id.iv_dot);
        LinearLayout lnParent = holder.getView(R.id.ln_parent);

        tvTime.setText(Utils.dateDiff(Utils.stringTimeToMillion(entity.getCreateTime())) + "");
        tvContent.setText(Utils.getValue(entity.getNewsContent()) + "");

        lnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MsgDetailAct.class)
                        .putExtra("data", entity));
            }
        });
    }
}

