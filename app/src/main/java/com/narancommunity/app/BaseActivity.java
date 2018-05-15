package com.narancommunity.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.activity.index.ReportAct;
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

    public void setBar(CenteredToolbar toolbar) {
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
        } else if (item.getItemId() == R.id.action_share) {
            showShareBoard();
        } else if (item.getItemId() == R.id.action_jubao) {
            startActivity(new Intent(getContext(), ReportAct.class));
        }
        return super.onOptionsItemSelected(item);
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


    public boolean isViewCovered(final View view) {
        View currentView = view;

        Rect currentViewRect = new Rect();
        boolean partVisible = currentView.getGlobalVisibleRect(currentViewRect);
        boolean totalHeightVisible = (currentViewRect.bottom - currentViewRect.top) >= view.getMeasuredHeight();
        boolean totalWidthVisible = (currentViewRect.right - currentViewRect.left) >= view.getMeasuredWidth();
        boolean totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible;
        if (!totalViewVisible)//if any part of the view is clipped by any of its parents,return true
            return true;

        while (currentView.getParent() instanceof ViewGroup) {
            ViewGroup currentParent = (ViewGroup) currentView.getParent();
            if (currentParent.getVisibility() != View.VISIBLE)//if the parent of view is not visible,return true
                return true;

            int start = indexOfViewInParent(currentView, currentParent);
            for (int i = start + 1; i < currentParent.getChildCount(); i++) {
                Rect viewRect = new Rect();
                view.getGlobalVisibleRect(viewRect);
                View otherView = currentParent.getChildAt(i);
                Rect otherViewRect = new Rect();
                otherView.getGlobalVisibleRect(otherViewRect);
                if (Rect.intersects(viewRect, otherViewRect))//if view intersects its older brother(covered),return true
                    return true;
            }
            currentView = currentParent;
        }
        return false;
    }

    public int indexOfViewInParent(View view, ViewGroup parent) {
        int index;
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index) == view)
                break;
        }
        return index;
    }
}
