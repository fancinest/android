package com.narancommunity.app.activity.general;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/29 10:21
 * Email：120760202@qq.com
 * FileName :
 */
public class HtmlFiveAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_h_five);
        ButterKnife.bind(this);
        setBar(toolbar);
        String title = getIntent().getStringExtra("title");
        toolbar.setTitle(title);
        final String url = getIntent().getStringExtra("url");
        Log.i("fancy", url);
//        try {
//            if (Build.VERSION.SDK_INT >= 16) {
//                Class<?> clazz = webView.getSettings().getClass();
//                Method method = clazz.getMethod(
//                        "setAllowUniversalAccessFromFileURLs", boolean.class);//利用反射机制去修改设置对象
//                if (method != null) {
//                    method.invoke(webView.getSettings(), true);//修改设置
//                }
//            }
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(url);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefresh.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                swipeRefresh.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefresh.setRefreshing(false);
            }
        });
        //页面加载
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                //newProgress   1-100之间的整数
//                if (newProgress == 100) {
//                    //页面加载完成，关闭ProgressDialog
//                    closeDialog();
//                } else {
//                    //网页正在加载，打开ProgressDialog
//                    openDialog(newProgress);
//                }
//            }
//
//            private void openDialog(int newProgress) {
//                if (dialog == null) {
//                    dialog = new ProgressDialog(getContext());
//                    dialog.setTitle("正在加载");
//                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                    dialog.setProgress(newProgress);
//                    dialog.setCancelable(true);
//                    dialog.show();
//                } else {
//                    dialog.setProgress(newProgress);
//                }
//            }
//
//            private void closeDialog() {
//                if (dialog != null && dialog.isShowing()) {
//                    dialog.dismiss();
//                    dialog = null;
//                }
//            }
//        });

    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();   //返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
