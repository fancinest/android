package com.narancommunity.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.OrderEntity;

import java.math.BigDecimal;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class BookOrderListAdapter extends ListBaseAdapter<OrderEntity> {
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
        OrderEntity entity = getDataList().get(position);
        View ivFirst = holder.getView(R.id.iv_first);
        LinearLayout lnParent = holder.getView(R.id.ln_wanter);
        if (position == 0)
            ivFirst.setVisibility(View.VISIBLE);
        else
            ivFirst.setVisibility(View.GONE);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvDistance = holder.getView(R.id.tv_distance);
        tvName.setText("" + Utils.getValue(entity.getAccountNike()));
        ImageView iv_img = holder.getView(R.id.iv_icon);
        if (null != entity.getAccountImg() && !"".equals(entity.getAccountImg())) {
            Utils.setImgF(mContext, entity.getAccountImg(), iv_img);
        } else
            Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, iv_img);
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, BookOrdererListAct.class));
            }
        });
        lnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });

//        longitude = 121.19, latitude =32.23
        LatLng startLatlng = new LatLng(32.26, 121.19);
        LatLng endLatlng = new LatLng(30.43, 120.3);
        float distance = AMapUtils.calculateLineDistance(startLatlng, endLatlng);
        BigDecimal bd = new BigDecimal(distance);
        String df = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
        tvDistance.setText("" + (Float.parseFloat(df) / 1000) + "km");


//        double aaaa = getDistance(latlng1, latlng);
//        float distance1 = (float) Math.abs(aaaa);
//        distance1 = Math.round(distance1);
//        if (distance1 > 0) {
//            holder.distance.setText(Utils.getDisDsrc(distance1));
//        } else {
//            holder.distance.setText("");
//        }
    }


    public double getDistance(LatLng start, LatLng end) {

        double lon1 = (Math.PI / 180) * start.longitude;
        double lon2 = (Math.PI / 180) * end.longitude;
        double lat1 = (Math.PI / 180) * start.latitude;
        double lat2 = (Math.PI / 180) * end.latitude;

        // 地球半径
        double R = 6371;

        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))
                * R;

        return d * 1000;
    }

}
