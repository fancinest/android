package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.BookDetailAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.WishEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class MyWishAdapter extends ListBaseAdapter<WishEntity> {
    OnItemClickListener listener;
    int type;

    public MyWishAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_my_wish;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        final WishEntity entity = mDataList.get(position);
        if (entity == null)
            return;

        LinearLayout lnParent = holder.getView(R.id.ln_parent);
        LinearLayout lnDonator = holder.getView(R.id.ln_donator);

        ImageView ivBook = holder.getView(R.id.iv_book);
        ImageView ivDonator = holder.getView(R.id.iv_img);

        TextView tvDonator = holder.getView(R.id.tv_donator);
        TextView tvTitle = holder.getView(R.id.tv_title);
        final TextView tvOption = holder.getView(R.id.tv_option);
        TextView tvContent = holder.getView(R.id.tv_content);

        tvContent.setText("" + Utils.getValue(entity.getOrderContent()));
        tvDonator.setText("" + Utils.getValue(entity.getInitiatorNike()));
        tvTitle.setText("" + Utils.getValue(entity.getOrderTitle()));

        String bookImg = Utils.getValue(entity.getOrderImgs());
        String donatorImg = Utils.getValue(entity.getInitiatorImg());
        if (!bookImg.equals("")) {
            Utils.setImgF(mContext, bookImg, ivBook);
            ivBook.setVisibility(View.VISIBLE);
        } else {
            ivBook.setVisibility(View.GONE);
            Utils.setImgF(mContext, R.drawable.ic_launcher_foreground, ivBook);
        }
        if (!donatorImg.equals(""))
            Utils.setImgF(mContext, donatorImg, ivDonator);
        else
            Utils.setImgF(mContext, R.drawable.ic_launcher_foreground, ivDonator);
        if (type == 0) {
            lnDonator.setVisibility(View.VISIBLE);
            tvOption.setVisibility(View.GONE);
        } else if (type == 1) {
            lnDonator.setVisibility(View.GONE);
            tvOption.setVisibility(View.VISIBLE);
            tvOption.setText("确认收到");
        } else if (type == 2) {
            lnDonator.setVisibility(View.VISIBLE);
            tvOption.setVisibility(View.VISIBLE);
            tvOption.setText("预约");
        }

        lnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
        tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    showPopView(tvOption, entity.getOrderId(), position);
//                    mContext.startActivity(new Intent(mContext, LookBookStateAct.class)
//                            .putExtra("bookId", entity.getOrderId()));
                } else mContext.startActivity(new Intent(mContext, BookDetailAct.class)
                        .putExtra("bookId", entity.getOrderId()));
            }
        });
    }

    void confirmGetBook(int orderId, final int position) {
        LoadDialog.show(mContext);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("orderId", orderId);
        NRClient.confirmGetBook(map, new ResultCallback<Result<String>>() {
            @Override
            public void onSuccess(Result<String> result) {
                LoadDialog.dismiss(mContext);
                Toaster.toast(mContext, "确认成功！");
                remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(mContext);
                Utils.showErrorToast(mContext, throwable);
            }
        });
    }

    PopupWindow mPop;

    private void showPopView(View view, final int orderId, final int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.normal_pop, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());
            TextView tv_dial = (TextView) v.findViewById(R.id.dial);
            tv_dial.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv_dial.setText("确认图书已经收到了吗？");
            v.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                    confirmGetBook(orderId, position);
                }
            });
            v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
