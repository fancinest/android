package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.MeFunctionEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Writer：fancy on 2017/12/26 15:15
 * Email：120760202@qq.com
 * FileName :
 */

public class RangeListRangeAdapter extends EasyRecyclerAdapter<MeFunctionEntity> {
    int tag;
    MeItemInterface meItemInterface;

    public RangeListRangeAdapter(Context context, List<MeFunctionEntity> list, int tag) {
        super(context, list);
        this.tag = tag;
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(getLayout(), parent, false);
        return new MyViewHolder(view);
    }

    private int getLayout() {
        switch (tag) {
            case 0:
                return R.layout.item_rangelist_daren;
//            case 1:
//                return R.layout.item_assistant_mission;
//            case 2:
//                return R.layout.item_special_report;
            default:
                return 0;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

//        final MeFunctionEntity item = getList().get(position);
//        hold.tv_name.setText(Utils.getValue(item.getName()) + "");
//        int url = item.getId();
//        hold.iv_pic.setImageResource(url);
//        Utils.setImgF(mContext, url, hold.iv_pic);
        if (tag == 0) {
            if (position < 3) {
                if (position == 0)
                    hold.ivLevel.setImageResource(R.mipmap.gyb_jinpai);
                else if (position == 1)
                    hold.ivLevel.setImageResource(R.mipmap.gyb_yinpai);
                else if (position == 2)
                    hold.ivLevel.setImageResource(R.mipmap.gyb_tongpai);
                hold.tvLevel.setVisibility(View.INVISIBLE);
                hold.ivLevel.setVisibility(View.VISIBLE);
            } else {
                hold.ivLevel.setVisibility(View.INVISIBLE);
                hold.tvLevel.setVisibility(View.VISIBLE);
                hold.tvLevel.setText("" + (position + 1));
            }
        }
//        hold.lnParent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mContext.startActivity(new Intent(mContext, SortSearchAct.class)
////                        .putExtra("type", Utils.getValue(item.getClassifyName())));
//                meItemInterface.OnItemClick(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLevel;
        TextView tvLevel;
        SelectableRoundedImageView ivIcon;
        TextView tvName;
        TextView tvTimes;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvLevel = itemView.findViewById(R.id.tv_level);
            ivLevel = itemView.findViewById(R.id.iv_level);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTimes = itemView.findViewById(R.id.tv_times);
        }
    }
}
