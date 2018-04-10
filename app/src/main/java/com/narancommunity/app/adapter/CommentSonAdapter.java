package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.Comments;
import com.narancommunity.app.interfaces.CommentInterfaces;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName :
 */

public class CommentSonAdapter extends EasyRecyclerAdapter<Comments> {
    boolean isLimited = false;
    CommentInterfaces meItemInterface;

    public CommentSonAdapter(Context context, List<Comments> list) {
        super(context, list);
    }

    public void setLimited(boolean isLimited) {
        this.isLimited = isLimited;
    }

    public void setListener(CommentInterfaces meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_comment_answer_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final Comments item = getList().get(position);
        if (item == null)
            return;
        if (item.getRecipientNike() != null && !item.getRecipientNike().equals("")) {
            hold.tvName.setText(Utils.getValue(item.getInitiatorNike()) + " 回复 " + Utils.getValue(item.getRecipientNike()));
        }
        hold.tvContent.setText(Utils.getValue(item.getCommentContent()) + "");
        hold.lnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = getList().get(position).getCommentId();
                String name = getList().get(position).getInitiatorNike();
                meItemInterface.OnAnswer(id, name);
            }
        });
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

        LinearLayout lnComment;
        SelectableRoundedImageView ivIcon;
        TextView tvName;
        TextView tvContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            lnComment = itemView.findViewById(R.id.ln_parent);
        }
    }
}
