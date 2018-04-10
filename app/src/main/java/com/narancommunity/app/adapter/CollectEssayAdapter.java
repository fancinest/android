package com.narancommunity.app.adapter;

import android.content.Context;
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
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.CollectEssayItem;

import java.util.List;

/**
 * Writer：fancy on 2018/3/12 17:12
 * Email：120760202@qq.com
 * FileName :
 */

public class CollectEssayAdapter extends EasyRecyclerAdapter<CollectEssayItem> {
    MeItemInterface meItemInterface;
    boolean isShowBigImg = false;

    public CollectEssayAdapter(Context context, List<CollectEssayItem> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    public void setIsShowBigImg(boolean isShowBigImg) {
        this.isShowBigImg = isShowBigImg;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(getLayout(), parent, false);
        return new CollectEssayAdapter.MyViewHolder(view);
    }

    private int getLayout() {
        return R.layout.item_wish_wall;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final WallListAdapter.MyViewHolder hold = (WallListAdapter.MyViewHolder) (holder);

        final CollectEssayItem item = getList().get(position);
//        hold.tvName.setText(Utils.getValue(item.getInitiatorNike()) + "");
//        hold.tvTimes.setText(Utils.dateDiff(Utils.stringTimeToMillion(item.getCreateTime())) + "");
//        String url = item.getInitiatorImg();
//        Utils.setImgF(mContext, url, hold.ivIcon);
//        hold.tvShare.setText(Utils.getValue(item.getShareTimes()) + "");
//        hold.tvCollect.setText(Utils.getValue(item.getCollectionTimes()) + " ");
//        hold.tvComment.setText(Utils.getValue(item.getCommentTimes()) + " ");
//        String bgUrl = item.getOrderImgs();
//        String[] bgArr = new String[]{};
//        if (bgUrl != null) {
//            bgArr = bgUrl.split(",", -1);
//        }
//
//        if (tag == 0) {
//            if (bgUrl != null) {
//                Utils.setImgF(mContext, bgArr[0], hold.ivContent);
//            }
//        } else if (tag == 1) {
//            hold.tvArea.setText("杭州市");
//            hold.tvTitle.setText(Utils.getValue(item.getOrderTitle()) + "");
//            hold.tvContent.setText(Utils.getValue(item.getOrderContent()) + "");
//
//            List<String> listPic = new ArrayList<>();
//            listPic.addAll(Utils.arrayToList(bgArr));
//            GridImageAdapter mAdapter = new GridImageAdapter(getContext(), listPic);
//            mAdapter.setLimited(true);
//            mAdapter.setIsShowBigImg(isShowBigImg);
//
//            GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
//            hold.recycleViewSon.setLayoutManager(gridlayout);
//            GridItemDecoration divider = new GridItemDecoration.Builder(getContext())
//                    .setVertical(R.dimen.three_divider)
//                    .setHorizontal(R.dimen.three_divider)
//                    .setColorResource(R.color.transparent)
//                    .build();
//            hold.recycleViewSon.addItemDecoration(divider);
//
//            hold.recycleViewSon.setAdapter(mAdapter);
//            hold.recycleViewSon.setNestedScrollingEnabled(false);
//        }
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

        SelectableRoundedImageView ivIcon;
        TextView tvName;
        TextView tvTimes;
        ImageView ivContent;
        TextView tvCollect;
        TextView tvShare;
        TextView tvComment;
        LinearLayout lnParent;

        TextView tvArea;
        TextView tvTitle;
        TextView tvContent;
        RecyclerView recycleViewSon;

        public MyViewHolder(View itemView) {
            super(itemView);
//            tvShare = itemView.findViewById(R.id.tv_share);
//            tvCollect = itemView.findViewById(R.id.tv_collect);
//            tvComment = itemView.findViewById(R.id.tv_comment);
//            ivIcon = itemView.findViewById(R.id.iv_icon);
//            tvTimes = itemView.findViewById(R.id.tv_times);
//            tvName = itemView.findViewById(R.id.tv_name);
//            lnParent = itemView.findViewById(R.id.ln_parent);
//            if (tag == 0) {
//                ivContent = itemView.findViewById(R.id.iv_content);
//            } else {
//                recycleViewSon = itemView.findViewById(R.id.recycleViewSon);
//                tvContent = itemView.findViewById(R.id.tv_content);
//                tvTitle = itemView.findViewById(R.id.tv_title);
//                tvArea = itemView.findViewById(R.id.tv_area);
//            }
        }
    }
}
