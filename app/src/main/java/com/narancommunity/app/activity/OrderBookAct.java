package com.narancommunity.app.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.SDCardUtils;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AddressEntity;
import com.narancommunity.app.entity.BookInfo;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Writer：fancy on 2018/4/16 20:00
 * Email：120760202@qq.com
 * FileName :  借阅活动
 */

public class OrderBookAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.ln_name)
    LinearLayout lnName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ln_address)
    LinearLayout lnAddress;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_writer)
    TextView tvWriter;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.ln_score)
    LinearLayout lnScore;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.card_parent)
    CardView cardParent;
    @BindView(R.id.calendar)
    CalendarView calendar;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.ctv_yes)
    CheckedTextView ctvYes;
    @BindView(R.id.ctv_no)
    CheckedTextView ctvNo;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.ln_check_view)
    LinearLayout lnCheckView;
    @BindView(R.id.et_memo)
    EditText etMemo;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.btn_want)
    Button btnWant;

    BookInfo mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_book);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("借阅");

        mData = (BookInfo) getIntent().getSerializableExtra("data");
        setView();
    }


    private void setView() {
        cardParent.setCardElevation(0);
        cardParent.setBackgroundColor(getResources().getColor(R.color.white));
        lnScore.setVisibility(View.GONE);

        tvSelectAddress.setVisibility(View.VISIBLE);
        lnAddress.setVisibility(View.GONE);

        tvBookName.setText("" + Utils.getValue(mData.getOrderTitle()));
        tvWriter.setText("" + Utils.getValue(mData.getOrderAuthor()));
        tvDesc.setText("" + Utils.getValue(mData.getOrderContent()));
        String url = Utils.getValue(mData.getOrderImgs());
        if (!url.equals(""))
            Utils.setImgF(getContext(), url, ivImg);
    }

    @OnClick({R.id.ln_address, R.id.btn_want, R.id.tv_select_address, R.id.ctv_yes, R.id.ctv_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_address:
            case R.id.ln_address:
                Intent its = new Intent(getContext(), AddressAct.class);
                its.putExtra("isForResult", true);
                startActivityForResult(its, 2000);
                break;
            case R.id.btn_want:
                LoadDialog.show(getContext(), "正在发送请求...");
                if (mAddress == null) {
                    Toaster.toast(getContext(), "请填写收货地址!");
                    return;
                }

                Map<String, Object> map = new HashMap<>();
                map.put("orderId", mData.getOrderId());
                map.put("accessToken", MApplication.getAccessToken());
                map.put("mailId", mAddress.getMailId());
                map.put("getTime", "2018-5-1 12:00:00");
                map.put("returnTime", "2018-5-21 12:00:00");
                map.put("mailType", getWay());
                map.put("applyContent", etMemo.getText().toString());
                NRClient.lendBook(map, new ResultCallback<Result<Void>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadDialog.dismiss(getContext());
                        Utils.showErrorToast(getContext(), throwable);
                    }

                    @Override
                    public void onSuccess(Result<Void> result) {
                        LoadDialog.dismiss(getContext());
                        showPopView(btnWant);
                    }
                });

                break;
            case R.id.ctv_yes:
                ctvYes.setChecked(true);
                ctvNo.setChecked(false);
                break;
            case R.id.ctv_no:
                ctvYes.setChecked(false);
                ctvNo.setChecked(true);
                break;
        }
    }

    private String getWay() {
        if (ctvYes.isChecked()) {
            return "TO_PAY";
        } else {
            return "PICK_UP";
        }
    }

    AddressEntity mAddress = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && data != null) {
            AddressEntity address = (AddressEntity) data.getSerializableExtra("address");
            mAddress = address;
            setAddress(address);
        }
    }

    private void setAddress(AddressEntity address) {
        tvSelectAddress.setVisibility(View.GONE);
        lnAddress.setVisibility(View.VISIBLE);
        tvName.setText("收货人：" + Utils.getValue(address.getMailName()) + "");
        tvTel.setText(Utils.getValue(address.getMailPhone()) + "");
        tvAddress.setText("收货地址：" + Utils.getValue(address.getProvince() + address.getCity() + address.getCounty()
                + address.getMailAddress()) + "");
    }

    PopupWindow mPop;

    private void showPopView(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.normal_pop, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());
            TextView tv_content = v.findViewById(R.id.dial);
            View line = v.findViewById(R.id.viewLine);
            TextView cancel = v.findViewById(R.id.cancel);
            TextView ok = v.findViewById(R.id.ok);
            cancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            tv_content.setTextColor(getResources().getColor(R.color.color_333333));
            tv_content.setText("借阅成功！");

            ok.setTextColor(getResources().getColor(R.color.appBlue));
            ok.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                    finish();
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
