package com.narancommunity.app.adapter.card;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.ShuzhaiItem;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter {

    private List<View> mViews = new ArrayList<>();
    private List<ShuzhaiItem> mData;
    private Context mContext;
    MeItemInterface mInterface;

    public CardPagerAdapter(Context mContext) {
        mViews = new ArrayList<>();
        this.mContext = mContext;
    }

    public void setData(List<ShuzhaiItem> listData) {
        mData = listData;
        for (int i = 0; i < listData.size(); i++) {
            mViews.add(null);
        }
    }

    public View getViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_shuzhai, container, false);
        container.addView(view);
        if (mData.get(position) == null)
            return view;
        bind(mData.get(position), view, position);
        mViews.set(position, view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(ShuzhaiItem item, View view, final int position) {
        ImageView img = view.findViewById(R.id.iv_content);
        ImageView share = view.findViewById(R.id.iv_share);
        ImageView zan = view.findViewById(R.id.iv_content);
        String url = Utils.getValue(item.getContentImg());
        if (!url.equals(""))
            Utils.setImgSZ(mContext, url, img);
        else Utils.setImgSZ(mContext, R.mipmap.pic_shuzhai_zhanweitu, img);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                mInterface.OnDelClick(position);
            }
        });
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.OnItemClick(position);
            }
        });
    }

    public void setListener(MeItemInterface mInterface) {
        this.mInterface = mInterface;
    }
}
