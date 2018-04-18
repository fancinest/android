package com.narancommunity.app.activity;

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
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/16 20:00
 * Email：120760202@qq.com
 * FileName :
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
    @BindView(R.id.ctv_yes_anonymous)
    CheckedTextView ctvYesAnonymous;
    @BindView(R.id.ctv_no_anonymous)
    CheckedTextView ctvNoAnonymous;
    @BindView(R.id.ln_check_view)
    LinearLayout lnCheckView;
    @BindView(R.id.et_memo)
    EditText etMemo;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.btn_want)
    Button btnWant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_book);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("预约/想要");

        setView();
    }

    private void setView() {
        cardParent.setCardElevation(0);
        cardParent.setBackgroundColor(getResources().getColor(R.color.white));
        lnScore.setVisibility(View.GONE);
    }

    @OnClick({R.id.ln_address, R.id.btn_want})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ln_address:
                break;
            case R.id.btn_want:
                showPopView(btnWant);
                break;
        }
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
            tv_content.setText("预约成功！");

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
