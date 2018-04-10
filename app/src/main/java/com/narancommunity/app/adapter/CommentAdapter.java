package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.MoreCommentAct;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.AnswerComment;
import com.narancommunity.app.entity.CommentEntity;
import com.narancommunity.app.interfaces.CommentInterfaces;

import java.util.List;

import butterknife.BindView;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName :
 */

public class CommentAdapter extends EasyRecyclerAdapter<CommentEntity> {
    boolean isLimited = false;
    CommentInterfaces meItemInterface;
    int lastPosition = 0;

    public CommentAdapter(Context context, List<CommentEntity> list) {
        super(context, list);
    }

    public void setListener(CommentInterfaces meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final CommentEntity item = getList().get(position);
        hold.tvComment.setText(Utils.getValue(item.getCommentContent()) + "");
        hold.tvName.setText(Utils.getValue(item.getInitiatorNike()) + "");
        hold.tvTimes.setText(Utils.dateDiff(Utils.stringTimeToMillion(item.getCreateTime())) + "");
        if (item.getRecords() != null) {
            if (item.getRecords().getTotalCount() > 3) {
                hold.tvMore.setVisibility(View.VISIBLE);
                hold.tvMore.setText("查看完整" + item.getRecords().getTotalCount() + "条评论");
            } else if (item.getRecords().getTotalCount() <= 3) {
                hold.tvMore.setVisibility(View.GONE);
            }
            setSonComment(hold.recyclerView, item.getRecords());
        } else if (item.getRecords() == null) {
            hold.tvMore.setVisibility(View.GONE);
        }
        String url = item.getInitiatorImg();
        if (!url.equals(""))
            Utils.setImgF(mContext, url, hold.ivIcon);
        hold.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), MoreCommentAct.class));
            }
        });
        hold.lnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, SortSearchAct.class)
//                        .putExtra("type", Utils.getValue(item.getClassifyName())));
                meItemInterface.OnItemClick(position);
            }
        });
    }

    private void setSonComment(RecyclerView recyclerView, final AnswerComment records) {
        CommentSonAdapter adapter = new CommentSonAdapter(getContext(), records.getComments());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter.setListener(new CommentInterfaces() {
            @Override
            public void OnItemClick(int position) {
//                showInputDialog(list.get(position).getCommentId(), list.get(position).getInitiatorNike());
            }

            @Override
            public void OnAnswer(int id, String name) {
                meItemInterface.OnAnswer(id, name);
            }
        });
        adapter.setLimited(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lnComment;
        SelectableRoundedImageView ivIcon;
        TextView tvName;
        TextView tvTimes;
        TextView tvComment;
        RecyclerView recyclerView;
        TextView tvMore;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTimes = itemView.findViewById(R.id.tv_times);
            tvComment = itemView.findViewById(R.id.tv_comment);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            tvMore = itemView.findViewById(R.id.tv_more);
            lnComment = itemView.findViewById(R.id.ln_comment);
        }
    }
}
