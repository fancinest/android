package com.narancommunity.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.narancommunity.app.activity.index.DonateBookAct;
import com.narancommunity.app.activity.index.ReleaseAct;
import com.narancommunity.app.activity.love.LoveFragment;
import com.narancommunity.app.activity.mine.MeFragment;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;

public class MainActivity extends AppCompatActivity {

    /**
     * 首页片段
     */
//    private IndexFragment mIndexFragment = IndexFragment.newInstance();
    private IndexNewFragment mIndexFragment = IndexNewFragment.newInstance();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        RadioGroup tabRdoGrp = findViewById(R.id.tab_rdo_grp);
        RadioButton home_tab = findViewById(R.id.home_tab);

        ImageView iv_release = findViewById(R.id.iv_release);
        iv_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DonateBookAct.class));
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

    @Override
    public void onBackPressed() {
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
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }
}
