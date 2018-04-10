package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.picbroswer.ImagePagerActivity;
import com.narancommunity.app.common.DensityUtils;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.MeFunctionEntity;

import java.util.List;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName :
 */
public class GridImageAdapter extends EasyRecyclerAdapter<String> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;
    int screenWidth = 0;
    boolean isShowBigImg = false;

    public GridImageAdapter(Context context, List<String> list) {
        super(context, list);
        screenWidth = DensityUtils.getScreenWidth(context);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    public void setIsShowBigImg(boolean isShowBigImg) {
        this.isShowBigImg = isShowBigImg;
    }

    public void setLimited(boolean isLimited) {
        this.isLimited = isLimited;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_one_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final String item = getList().get(position);

        float s = Utils.dip2px(getContext(), 52);
        int width = (int) ((screenWidth - s) / 3);
        ViewGroup.LayoutParams layoutParams = hold.iv_pic.getLayoutParams();
        layoutParams.height = width;
        layoutParams.width = width;
        hold.iv_pic.setLayoutParams(layoutParams);
        Utils.setImgF(mContext, item, hold.iv_pic);
        hold.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowBigImg){
                    Intent intent = new Intent(getContext(), ImagePagerActivity.class);
//                        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, getArr(getList()));
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                    getContext().startActivity(intent);
                }else {
                    //TODO
                    Log.i("fancy","不显示大图");
                }
            }
        });
    }

    private String[] getArr(List<String> list) {
        String[] arr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    @Override
    public int getItemCount() {
        if (getList().size() >= 3)
            return 3;
        else if (getList().size() < 3)
            return getList().size();
        else
            return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_pic = itemView.findViewById(R.id.iv_img);
        }
    }
}
