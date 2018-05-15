package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.Questionnaires;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName :
 */

public class QuestionAdapter extends EasyRecyclerAdapter<Questionnaires> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;

    public QuestionAdapter(Context context, List<Questionnaires> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_question_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final Questionnaires item = getList().get(position);
        hold.tvTitle.setText((position + 1) + "、" + Utils.getValue(item.getQuestionnaireContent()) + "");
        hold.tagFlow.setAdapter(new TagAdapter(item.getOptions()) {

            @Override
            public View getView(FlowLayout parent, final int position, Object o) {
                LayoutInflater mInflater = LayoutInflater.from(getContext());
                LinearLayout view = (LinearLayout) mInflater.inflate(R.layout.item_question_answer_item,
                        hold.tagFlow, false);
                final CheckedTextView checkedTextView = view.findViewById(R.id.ctv);
                checkedTextView.setText(item.getOptions().get(position).getOptionTitle());
                checkedTextView.setChecked(item.getOptions().get(position).isChecked());
                checkedTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i=0;i<item.getOptions().size();i++){
                            item.getOptions().get(i).setChecked(false);
                        }
                        boolean now = item.getOptions().get(position).isChecked();
                        item.getOptions().get(position).setChecked(!now);
                        notifyDataSetChanged();
                    }
                });
                return view;
            }
        });
        hold.tagFlow.setMaxSelectCount(1);
    }

//    private void verifyDefault(final int position, final View view) {
//        CheckedTextView checkedTextView = (CheckedTextView) view;
//        checkedTextView.toggle();
//        Questionnaires entity = getList().get(position);
//        entity.setDefault(!entity.isDefault());
//        for (int i = 0; i < getList().size(); i++) {
//            if (i != position)
//                getList().get(i).setDefault(false);
//        }
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TagFlowLayout tagFlow;
        LinearLayout lnParent;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tagFlow = itemView.findViewById(R.id.tagFlow);
            lnParent = itemView.findViewById(R.id.ln_parent);
        }
    }
}
