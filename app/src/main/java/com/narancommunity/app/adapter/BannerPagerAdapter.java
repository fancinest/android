package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.entity.Publicitys;
import com.viewpagerindicator.IconPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 */
public class BannerPagerAdapter extends PagerAdapter implements IconPagerAdapter {
    Context context;

    private List<Publicitys> mBannerList;

    public BannerPagerAdapter(Context context, List<Publicitys> bannerList) {
        this.context = context;
        setBannerList(bannerList);
    }

    public void setBannerList(List<Publicitys> bannerList) {
        if (bannerList != null) {
            this.mBannerList = bannerList;
        } else {
            this.mBannerList = new ArrayList<Publicitys>();
        }
    }

    public List<Publicitys> getBannerList() {
        return mBannerList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public void finishUpdate(View container) {

    }

    @Override
    public int getIconResId(int index) {
        return R.drawable.indicator_selector;
    }

    @Override
    public int getCount() {
        return mBannerList == null ? 0 : mBannerList.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        ImageView bannerImageView = new ImageView(container.getContext());
        bannerImageView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        bannerImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bannerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publicitys item = getBannerList().get(position);
                int type = item.getUrlType();//1 ;//H5外链 2 ;//爱心行动 3 ;//互助社区
                String url = item.getPublicityUrl();//当不为1时publicityUrl 为相应跳转的ID
                if (type == 1) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    context.startActivity(intent);
                } else if(type==2){
                    Toaster.toast(context,"这里要跳转爱心行动");
//                    context.startActivity(new Intent(context, WebViewTwoAct.class).putExtra(
//                            "url", url).putExtra("title", ""));
                }else {
//                    context.startActivity(new Intent(context, BookSHHZAct.class).putExtra(
//                            "url", url).putExtra("title", ""));
                    Toaster.toast(context,"这里要跳转互助社区");
                }
            }
        });
        String url = getBannerList().get(position).getPublicityImg();
        Glide.with(container.getContext()).load(url).placeholder(R.mipmap.bitmap_banner).into(bannerImageView);
        ((ViewPager) container).addView(bannerImageView, 0);
        return bannerImageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View container) {

    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

}
