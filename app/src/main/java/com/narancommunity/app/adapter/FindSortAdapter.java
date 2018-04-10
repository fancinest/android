package com.narancommunity.app.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class FindSortAdapter extends ListBaseAdapter<String> {
    OnItemClickListener listener;

    public FindSortAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_find_sort;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        ImageView iv_img = holder.getView(R.id.iv_img);
        TextView tv_name = holder.getView(R.id.tv_name);
        switch (position) {
            case 0:
                Utils.setImgF(mContext, R.mipmap.meizhuanghuli, iv_img);
                tv_name.setText("美妆护理");
                break;
            case 1:
                Utils.setImgF(mContext, R.mipmap.zhongbiaoshoushi, iv_img);
                tv_name.setText("钟表首饰");
                break;
            case 2:
                Utils.setImgF(mContext, R.mipmap.shumadianqi, iv_img);
                tv_name.setText("数码电器");
                break;
            case 3:
                Utils.setImgF(mContext, R.mipmap.gongjuqicai, iv_img);
                tv_name.setText("工具器材");
                break;
            case 4:
                Utils.setImgF(mContext, R.mipmap.meizhuanghuli, iv_img);
                tv_name.setText("美妆护理2");
                break;
            case 5:
                Utils.setImgF(mContext, R.mipmap.meizhuanghuli, iv_img);
                tv_name.setText("美妆护理3");
                break;
            case 6:
                Utils.setImgF(mContext, R.mipmap.meizhuanghuli, iv_img);
                tv_name.setText("美妆护理4");
                break;
        }

        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }
}
