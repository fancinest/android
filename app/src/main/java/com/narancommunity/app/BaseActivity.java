package com.narancommunity.app;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.narancommunity.app.common.CenteredToolbar;

/**
 * Writer：fancy on 2017/4/14 13:33
 * Email：120760202@qq.com
 * FileName :
 */

public class BaseActivity extends AppCompatActivity {

    public Activity getContext() {
        return this;
    }

    public void setBar(CenteredToolbar toolbar){
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
//            linearParams.height = Utils.dip2px(getContext(), 48);
//            toolbar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
//            toolbar.setPadding(0, 0, 0, 0);
//        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setElevation(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
