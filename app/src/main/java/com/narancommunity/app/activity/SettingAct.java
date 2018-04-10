package com.narancommunity.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.UserInfo;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2017/12/28 16:18
 * Email：120760202@qq.com
 * FileName :
 */

public class SettingAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.mSwitch)
    Switch mSwitch;
    @BindView(R.id.ln_feedback)
    LinearLayout lnFeedback;
    @BindView(R.id.ln_invite)
    LinearLayout lnInvite;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.tr_modify)
    LinearLayout trModify;
    @BindView(R.id.ln_about)
    LinearLayout lnAbout;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tr_version)
    LinearLayout trVersion;
    @BindView(R.id.ln_logout)
    TextView lnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting);
        ButterKnife.bind(this);

        setBar(toolbar);
        toolbar.setTitle("个人设置");
    }

    @OnClick({R.id.ln_feedback, R.id.ln_invite, R.id.tr_modify, R.id.ln_about, R.id.tr_version, R.id.ln_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ln_feedback:
                startActivity(new Intent(getContext(), AppFeedbackAct.class));
                break;
            case R.id.ln_invite:
                showShareBoard();
                break;
            case R.id.tr_modify:
                startActivity(new Intent(getContext(),ModifyPsdAct.class));
                break;
            case R.id.ln_about:
                break;
            case R.id.tr_version:
                break;
            case R.id.ln_logout:
                showPopView(view);
                break;
        }
    }

    private void logout() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        NRClient.logout(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "退出成功！");
                MApplication.logout(false);
                finish();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
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
            TextView tv_dial = (TextView) v.findViewById(R.id.dial);
            tv_dial.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv_dial.setText("确定退出？");
            tv_dial.setTextColor(getResources().getColor(R.color.black));
            v.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    logout();
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Dialog mWindowDialog;

    public void showShareBoard() {
        mWindowDialog = new Dialog(this, R.style.more_dialog);
        View view = LinearLayout.inflate(this, R.layout.share_board, null);
        mWindowDialog.setContentView(view);
        mWindowDialog.setCanceledOnTouchOutside(false);
        view.getLayoutParams().width = getWindow().getWindowManager().getDefaultDisplay().getWidth();

        TextView sina = (TextView) view.findViewById(R.id.tv_sina);
        TextView weixin = (TextView) view.findViewById(R.id.tv_weixin);
        TextView weixin_circle = (TextView) view.findViewById(R.id.tv_wexin_circle);
        TextView qq = (TextView) view.findViewById(R.id.tv_qq);
        ImageView cha = (ImageView) view.findViewById(R.id.iv_close);

        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NRApplication.ItemClick(getContext(),"那然公益APP",
//                        "分享APP", "http://wx4.sinaimg.cn/square/006behBIgy1fe6d1v8mr3j3028028q2t.jpg",
//                        "", 0, platformActionListener);
            }
        });
        weixin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                NRApplication.ItemClick(getContext(),"那然公益APP",
//                        "分享APP", "http://wx4.sinaimg.cn/square/006behBIgy1fe6d1v8mr3j3028028q2t.jpg",
//                        "", 1, platformActionListener);
            }
        });
        weixin_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NRApplication.ItemClick(getContext(),"那然公益APP",
//                        "分享APP", "http://wx4.sinaimg.cn/square/006behBIgy1fe6d1v8mr3j3028028q2t.jpg",
//                        "", 2, platformActionListener);
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                NRApplication.ItemClick(getContext(),"那然公益APP",
//                        "分享APP", "http://wx4.sinaimg.cn/square/006behBIgy1fe6d1v8mr3j3028028q2t.jpg",
//                        "", 3, platformActionListener);
            }
        });
        cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowDialog.dismiss();
            }
        });
        mWindowDialog.show();
    }

//    PlatformActionListener platformActionListener = new PlatformActionListener() {
//
//        @Override
//        public void onError(Platform arg0, int arg1, Throwable arg2) {
//            //操作失败啦，打印提供的错误，方便调试
//            arg2.printStackTrace();
//            mWindowDialog.dismiss();
//        }
//
//        @Override
//        public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
//            //操作成功，在这里可以做后续的步骤
//            //这里需要说明的一个参数就是HashMap<String, Object> arg2
//            //这个参数在你进行登录操作的时候里面会保存有用户的数据，例如用户名之类的。
//            Toaster.toast(getContext(), "分享成功！");
//            mWindowDialog.dismiss();
//        }
//
//        @Override
//        public void onCancel(Platform arg0, int arg1) {
//            //用户取消操作会调用这里
//            Toaster.toast(getContext(), "取消分享");
//            mWindowDialog.dismiss();
//        }
//    };
}
