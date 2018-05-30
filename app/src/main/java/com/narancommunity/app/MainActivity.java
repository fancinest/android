package com.narancommunity.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.narancommunity.app.activity.general.AuthoriseFirstAct;
import com.narancommunity.app.activity.index.DonateBookAct;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.UpdateInfo;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

public class MainActivity extends AppCompatActivity {

    /**
     * 首页片段
     */
//    private IndexFragment mIndexFragment = IndexFragment.newInstance();
    private IndexFragment mIndexFragment = IndexFragment.newInstance();
    /**
     * 种树片段
     */
    private PetFragment mPlantFragment = PetFragment.newInstance();
    /**
     * 慈善家片段
     */
    private LoveFragment mPhilFragment = LoveFragment.newInstance();
    /**
     * 我的片段
     */
    private MeFragment mMeFragment = MeFragment.newInstance();
    /**
     * 片段数组
     */
    private final Fragment[] TABS_FRAGMENT = new Fragment[]{
            mIndexFragment,
            mPlantFragment,
            mPhilFragment,
            mPhilFragment,
            mMeFragment
    };
    ImageView iv_release;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        RadioGroup tabRdoGrp = findViewById(R.id.tab_rdo_grp);
        RadioButton home_tab = findViewById(R.id.home_tab);

        iv_release = findViewById(R.id.iv_release);
        iv_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MApplication.isAuthorisedSuccess(MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, DonateBookAct.class));
                } else showPopView(iv_release);
            }
        });
        home_tab.setChecked(true);
        tabRdoGrp.setOnCheckedChangeListener(mOnTabCheckedChangeListenernew);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content_frame, mIndexFragment)
                .add(R.id.content_frame, mPlantFragment)
                .add(R.id.content_frame, mPhilFragment)
                .add(R.id.content_frame, mMeFragment)
                .hide(mPlantFragment)
                .hide(mPhilFragment)
                .hide(mMeFragment)
                .commit();
        registerMessageReceiver();  // used for receive msg
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
            TextView tv_prompt = v.findViewById(R.id.tv_prompt);
            tv_prompt.setText("分享赠送陌生人\n实名认证更安全");
            Button go = v.findViewById(R.id.btn_go);
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    startActivity(new Intent(MainActivity.this, AuthoriseFirstAct.class));
                }
            });
            v.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                }
            });
        }
        mPop.setClippingEnabled(true);
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

//    PopupWindow mPop;
//
//    private void showPopView(View view) {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View v = inflater.inflate(R.layout.normal_pop, null);
//
//        if (mPop == null) {
//            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            mPop.setFocusable(true);
//            mPop.setOutsideTouchable(true);
//            mPop.setBackgroundDrawable(new BitmapDrawable());
//            TextView tv_dial = (TextView) v.findViewById(R.id.dial);
//            tv_dial.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//            tv_dial.setText("您尚实名认证，无法操作，是否去实名认证？");
//            tv_dial.setTextColor(getResources().getColor(R.color.black));
//            Button ok = v.findViewById(R.id.ok);
//            ok.setText("去认证");
//            ok.setTextColor(getResources().getColor(R.color.appBlue));
//            ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    startActivity(new Intent(getApplicationContext(), AuthoriseFirstAct.class));
//                    mPop.dismiss();
//                }
//            });
//            v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View arg0) {
//                    mPop.dismiss();
//                }
//            });
//        }
//        if (mPop != null && !mPop.isShowing())
//            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
//    }

    /**
     * 底部Tab选择监听
     */
    private RadioGroup.OnCheckedChangeListener mOnTabCheckedChangeListenernew
            = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

            RadioButton checkedRdoBtn = (RadioButton) radioGroup.findViewById(checkedId);
            checkedRdoBtn.playSoundEffect(SoundEffectConstants.CLICK);
            int checkedPosition = radioGroup.indexOfChild(checkedRdoBtn);
            if (0 <= checkedPosition && checkedPosition < TABS_FRAGMENT.length) {
                if (checkedPosition != 2)
                    showFragment(checkedPosition);
            }
        }
    };

    private void showFragment(int checkedPosition) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0, len = TABS_FRAGMENT.length; i < len; i++) {
            if (checkedPosition != i) {
                ft.hide(TABS_FRAGMENT[i]);
            }
        }
        ft.show(TABS_FRAGMENT[checkedPosition]).commit();
    }

    private static final int TIME_INTERVAL = 2000;
    private long mLastBackPressedTime = 0L;
    boolean isMustUpdate = false;

    @Override
    public void onBackPressed() {
        if (isMustUpdate) {
            finish();
            return;
        }
        if (System.currentTimeMillis() - mLastBackPressedTime < TIME_INTERVAL) {
            finish();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.please_press_back_again_to_exit,
                    Toast.LENGTH_SHORT)
                    .show();
            mLastBackPressedTime = System.currentTimeMillis();
        }
    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            if (isMustUpdate) {
                finish();
                return true;
            }
            if (System.currentTimeMillis() - mLastBackPressedTime < TIME_INTERVAL) {
                finish();
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.please_press_back_again_to_exit,
                        Toast.LENGTH_SHORT)
                        .show();
                mLastBackPressedTime = System.currentTimeMillis();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }


    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!Utils.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        Toaster.toast(getApplicationContext(), "要显示用户自定义推送");
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    public static boolean isForeground = false;

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getUpdateInfo();
            }
        }, 500);
    }

    private void getUpdateInfo() {
        NRClient.update(new ResultCallback<Result<UpdateInfo>>() {
            @Override
            public void onSuccess(Result<UpdateInfo> result) {
                Log.i("fancy", " 更新信息:" + result.toString());
                compareVersion(result.getData());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.i("fancy", " 更新失败信息:" + throwable.getMessage());
            }
        });
    }

    private void compareVersion(UpdateInfo data) {
        int versionCode = Utils.getValue(data.getVersionCode());
        int code = Utils.getAPPVersionCode(this);
        int MustUpdate = Utils.getValue(data.getMustUpdate());//0-不用更新，1-必须更新
        isMustUpdate = MustUpdate == 0 ? false : true;
        if (code < versionCode) {//本地code小于网络code，则弹出更新，让用户选择是否更新
            if (isShowPrompt) {
                showUpdatePop(data);
            }
        }
    }

    PopupWindow mUpdatePop;
    boolean isShowPrompt = true;

    private void showUpdatePop(final UpdateInfo data) {
        final String downloadUrl = Utils.getValue(data.getDownloadUrl());
        String desc = Utils.getValue(data.getVersionDesc());

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.update_pop, null);

        if (mUpdatePop == null) {
            mUpdatePop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mUpdatePop.setFocusable(true);
            mUpdatePop.setOutsideTouchable(true);
            TextView tv_dial = v.findViewById(R.id.dial);
            tv_dial.setText(desc);
            tv_dial.setTextColor(getResources().getColor(R.color.black));
            Button ok = v.findViewById(R.id.ok);
            Button cancel = v.findViewById(R.id.cancel);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url;
                    if (!downloadUrl.contains("http://") && !downloadUrl.contains("https://"))
                        content_url = Uri.parse("http://" + downloadUrl);
                    else content_url = Uri.parse(downloadUrl);
                    intent.setData(content_url);
                    MainActivity.this.startActivity(intent);
                }
            });
            v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (isMustUpdate) {
                        finish();
                    } else {
                        isShowPrompt = false;
                        mUpdatePop.dismiss();
                    }
                }
            });
            if (isMustUpdate)
                cancel.setVisibility(View.GONE);
            else cancel.setText("本次忽略");
        }
        if (!isMustUpdate) {
            mUpdatePop.setBackgroundDrawable(new BitmapDrawable());
        } else {
            mUpdatePop.setFocusable(false);
        }
        if (mUpdatePop != null && !mUpdatePop.isShowing())
            mUpdatePop.showAtLocation(iv_release, Gravity.CENTER, 0, 0);

    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }
}
