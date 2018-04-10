package com.narancommunity.app.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Toaster;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

/**
 * Writer：fancy on 2017/12/21 15:39
 * Email：120760202@qq.com
 * FileName :
 */

public class ReleaseAct extends BaseActivity {
    @BindView(R.id.iv_wish)
    ImageView ivWish;
    @BindView(R.id.tv_wish)
    TextView tvWish;
    @BindView(R.id.iv_donate)
    ImageView ivDonate;
    @BindView(R.id.tv_donate)
    TextView tvDonate;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_release);
        ButterKnife.bind(this);
        MApplication.putActivity("releaseAct", getContext());
    }

    @OnClick({R.id.iv_wish, R.id.iv_donate, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_wish:
                if (MApplication.getUserInfo(getContext()) == null) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(this, ReleaseSelectAct.class)
                        .putExtra("isWish", true));
                break;
            case R.id.iv_donate:
                if (MApplication.getUserInfo(getContext()) == null) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                if (!MApplication.getUserInfo(getContext()).getCertificationType().equals("SUCCESS"))
                    showPopView(view);
                else
                    startActivity(new Intent(this, ReleaseSelectAct.class)
                            .putExtra("isWish", false));
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }

    PopupWindow mPop;

    private void showPopView(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.pop_authorise, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());
            Button go = v.findViewById(R.id.btn_go);
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    startActivity(new Intent(getContext(), AuthoriseFirstAct.class));
                }
            });
            v.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

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
