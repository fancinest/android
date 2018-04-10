package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.MeFunctionEntity;

import java.util.List;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName :
 */

public class ReleaseSelectAdapter extends EasyRecyclerAdapter<Pair<String, Integer>> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;

    public ReleaseSelectAdapter(Context context, List<Pair<String, Integer>> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_release_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final Pair<String, Integer> item = getList().get(position);
        hold.checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold.checkedTextView.toggle();
                for (int i = 0; i < getList().size(); i++) {
//                    if (i != position)
//                        getList().get(i).setDefault(false);
                }
                notifyDataSetChanged();
            }
        });
//        hold.tv_name.setText(Utils.getValue(item.first) + "");
//        int url = item.second;
//        hold.iv_pic.setImageResource(url);
//        Utils.setImgF(mContext, url, hold.iv_pic);
//        hold.ln_parent.setOnClickListener(new View.OnClickListener() {
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

        //        TextView tv_name;
//        ImageView iv_pic;
//        LinearLayout ln_parent;
        CheckedTextView checkedTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
//            checkedTextView = itemView.findViewById(R.id.ctv_item);
//            tv_name = itemView.findViewById(R.id.tv_item);
//            ln_parent = itemView.findViewById(R.id.ln_parent);
        }
    }
}
