package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.BookEntity;
import com.narancommunity.app.entity.MeFunctionEntity;
import com.narancommunity.app.entity.RecEntity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName : 公益活动
 */

public class BookListAdapter extends EasyRecyclerAdapter<RecEntity> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;

    public BookListAdapter(Context context, List<RecEntity> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_book_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final RecEntity item = getList().get(position);
        String url = Utils.getValue(item.getOrderImgs());
        if (!url.equals("")) {
            Utils.setImgF(getContext(), url, hold.ivPic);
        } else {
            Utils.setImgF(getContext(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523510281803&di=d1eaf4c7d74e69730bf5889169cae5e7&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3994969733%2C336727888%26fm%3D214%26gp%3D0.jpg", hold.ivPic);
        }
        hold.tvName.setText(Utils.getValue(item.getOrderTitle()));

//        hold.iv_pic.setImageResource(url);
        hold.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, SortSearchAct.class)
//                        .putExtra("type", Utils.getValue(item.getClassifyName())));
                meItemInterface.OnItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPic;
        TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_img);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
