package com.narancommunity.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.narancommunity.app.activity.general.LoginAct;
import com.narancommunity.app.common.DBHelper;
import com.narancommunity.app.net.AppConstants;
import com.snappydb.DB;
import com.snappydb.SnappydbException;
import com.umeng.analytics.MobclickAgent;

/**
 * Wirter：fancy on 2017/3/1 10:44
 * Mail：120760202@qq.com
 * FileName：欢迎页
 */
public class SplashAct extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
        }
        setContentView(R.layout.act_welcome);
        try {
            DB snappyDb =
                    DBHelper.getDB(this);
            boolean isFirst = snappyDb.exists(AppConstants.IS_FIRST_INSTALL);
            if (isFirst) {
                boolean isLogin = MApplication.getAccessToken().equals("") ? false : true;
                if (isLogin)
                    mHandler.postDelayed(runnableMain, 1000);
                else
                    mHandler.postDelayed(runnableLogin, 1000);
            } else {
                snappyDb.put(AppConstants.IS_FIRST_INSTALL, false);
                mHandler.postDelayed(runnableStart, 1000);
            }
            snappyDb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        jumpScheme();
    }

    String TAG = "fancy";

    private void jumpScheme() {
        Intent intent = getIntent();
        Log.e(TAG, "scheme:" + intent.getScheme());
        Uri uri = intent.getData();
        if (null == uri) {
            //不是从外面跳进来的
        } else {
            Log.e(TAG, "scheme: " + uri.getScheme());
            Log.e(TAG, "host: " + uri.getHost());
            Log.e(TAG, "port: " + uri.getPort());
            Log.e(TAG, "path: " + uri.getPath());
            Log.e(TAG, "queryString: " + uri.getQuery());
            String type = uri.getQueryParameter("type");
            Log.e(TAG, "queryParameter: " + type);

//            if (type.equals("webview")) {
//                startActivity(new Intent(this, WebViewAct.class));
//            }
        }


    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private Runnable runnableLogin = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashAct.this, LoginAct.class));
            finish();
        }
    };

    private Runnable runnableStart = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashAct.this, StartViewAct.class));
            finish();
        }
    };

    private Runnable runnableMain = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashAct.this, MainActivity.class));
            finish();
        }
    };
}
