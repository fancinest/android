package com.narancommunity.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.narancommunity.app.activity.general.LoginAct;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * 首次启动图片
 *
 * @author fancy
 */
public class StartViewAct extends Activity {
    private ViewPager viewPager; // android-support-v4中的滑动组件
    private List<View> imageViews; // 滑动的图片集合
    private MyAdapter mAdapter;
    private int[] imageres = new int[]{R.drawable.xinyuan_loadingpage1,
            R.drawable.xinyuan_loadingpage2, R.drawable.xinyuan_loadingpage3};
    private int currentPosition = 0;
//    private CirclePageIndicator mBannerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.act_start_view);
        setupView();

//        mBannerIndicator = findViewById(R.id.banner_indicator);
//        mBannerIndicator.setViewPager(viewPager);

    }

    private void setupView() {
        imageViews = new ArrayList<View>();
        // 初始化图片资源
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }

        viewPager = (ViewPager) findViewById(R.id.vp);
        mAdapter = new MyAdapter(imageres);
        viewPager.setAdapter(mAdapter);// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.addOnPageChangeListener(new MyPageChangeListener());

//		btn_start= (Button)findViewById(R.id.btn_start);
//		btn_start.setVisibility(View.INVISIBLE);

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition == 2)
                    gotoNext();
            }
        });
    }

    public void myClick(View v) {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            oldPosition = position;
            currentPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 填充ViewPager页面的适配器
     *
     * @author Administrator
     */
    private class MyAdapter extends PagerAdapter {
        private int[] imageres;

        MyAdapter(int[] images) {
            this.imageres = images;
        }
//		public void setImages(int[] images) {
//			this.imageres = images;
//		}

        @Override
        public int getCount() {
            return imageres.length;
        }

        @Override
        public Object instantiateItem(View arg0, int position) {
            ((ImageView) (imageViews.get(position)))
                    .setImageResource(imageres[position]);
//				((ImageView) (imageViews.get(position))).setAdjustViewBounds(true);
            if (position == 2) {
                ((imageViews.get(position))).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoNext();
                    }
                });
            }
            ((ViewPager) arg0).addView(imageViews.get(position));

            return imageViews.get(position);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    private void gotoNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartViewAct.this, LoginAct.class));
                finish();
            }
        }, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
