package com.narancommunity.app.adapter;

import android.content.Context;
import android.widget.TextView;

import com.narancommunity.app.R;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.FootPrintEntity;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class CompanyFootAdapter extends ListBaseAdapter<FootPrintEntity> {
    OnItemClickListener listener;

    public CompanyFootAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_footprint;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        final FootPrintEntity entity = mDataList.get(position);
        if (entity == null)
            return;

        TextView tvTime = holder.getView(R.id.tv_title);
        TextView tvContent = holder.getView(R.id.tv_content);

        tvTime.setText(Utils.dateDiff(Utils.stringTimeToMillion(entity.getFootprintTime())) + "");
        tvContent.setText(Utils.getValue(entity.getFootprintContent()));
    }
}
