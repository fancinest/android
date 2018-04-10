package com.narancommunity.app.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class DensityUtils {

	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
	
	public static int getStatusBarHeight(Activity activity) {
		Rect rect = new Rect();
		activity.getWindow().getDecorView()
			.getWindowVisibleDisplayFrame(rect);
		return rect.top;
	}

	public static int getTitleBarHeight(Activity activity) {
		int contentViewTop = activity.getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
		return (contentViewTop - getStatusBarHeight(activity));
	}
	
	public static int getContentViewTop(Activity activity) {
		int contentViewTop = activity.getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
		return contentViewTop;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context
				.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context
				.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context
				.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context
				.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int getMeasuredWidth(View view) {
		int widthMeasureSpec = View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int heightMeasureSpec = View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(widthMeasureSpec, heightMeasureSpec);
		return view.getMeasuredWidth();
	}

	public static int getMeasuredHeight(View view) {
		int widthMeasureSpec = View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int heightMeasureSpec = View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(widthMeasureSpec, heightMeasureSpec);
		return view.getMeasuredHeight();
	}

}
