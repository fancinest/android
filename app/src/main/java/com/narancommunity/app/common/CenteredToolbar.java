package com.narancommunity.app.common;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CenteredToolbar extends Toolbar {

    public static final String TAG = CenteredToolbar.class.getSimpleName();

    private LinearLayout mContainerLayout;
    private TextView mCustomTitleTextView;
    private TextView mCustomSubtitleTextView;

    public CenteredToolbar(Context context) {
        this(context, null);
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCustomTitleTextView = new TextView(context);
        mCustomSubtitleTextView = new TextView(context);
        mCustomTitleTextView.setSingleLine();
        mCustomSubtitleTextView.setSingleLine();
        mCustomTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        mCustomSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);

        int titleTextAppearanceStyleResId;
        int subtitleTextAppearanceStyleResId;
        ColorStateList titleColorStateList;
        ColorStateList subtitleColorStateList;
        CharSequence title;
        CharSequence subtitle;

        TypedArray titleTextAppearanceTypedArray = context.getTheme().obtainStyledAttributes(
                attrs, new int[]{android.support.v7.appcompat.R.attr.titleTextAppearance},
                defStyleAttr, 0);
        TypedArray subtitleTextAppearanceTypedArray = context.getTheme().obtainStyledAttributes(
                attrs, new int[]{android.support.v7.appcompat.R.attr.subtitleTextAppearance},
                defStyleAttr, 0);
        TypedArray toolbarTypedArray = context.obtainStyledAttributes(
                attrs, android.support.v7.appcompat.R.styleable.Toolbar,
                defStyleAttr, 0);
        try {
            titleTextAppearanceStyleResId = titleTextAppearanceTypedArray.getResourceId(0, 0);
            subtitleTextAppearanceStyleResId = subtitleTextAppearanceTypedArray.getResourceId(0, 0);
            titleColorStateList = toolbarTypedArray.getColorStateList(
                    android.support.v7.appcompat.R.styleable.Toolbar_titleTextColor);
            subtitleColorStateList = toolbarTypedArray.getColorStateList(
                    android.support.v7.appcompat.R.styleable.Toolbar_subtitleTextColor);
            title = toolbarTypedArray.getText(
                    android.support.v7.appcompat.R.styleable.Toolbar_title);
            subtitle = toolbarTypedArray.getText(
                    android.support.v7.appcompat.R.styleable.Toolbar_subtitle);
        } finally {
            titleTextAppearanceTypedArray.recycle();
            subtitleTextAppearanceTypedArray.recycle();
            toolbarTypedArray.recycle();
        }
        if (titleTextAppearanceStyleResId > 0) {
            mCustomTitleTextView.setTextAppearance(context, titleTextAppearanceStyleResId);
        }
        if (subtitleTextAppearanceStyleResId > 0) {
            mCustomSubtitleTextView.setTextAppearance(context, subtitleTextAppearanceStyleResId);
        }
        if (titleColorStateList != null) {
            mCustomTitleTextView.setTextColor(titleColorStateList);
        }
        if (subtitleColorStateList != null) {
            mCustomSubtitleTextView.setTextColor(subtitleColorStateList);
        }
        mCustomTitleTextView.setText(title);
        mCustomSubtitleTextView.setText(subtitle);
        mCustomTitleTextView.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        mCustomSubtitleTextView.setVisibility(TextUtils.isEmpty(subtitle) ? View.GONE : View.VISIBLE);

        mContainerLayout = new LinearLayout(context);
        Toolbar.LayoutParams linearLayoutLayoutParams = new Toolbar.LayoutParams(
                LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT);
        linearLayoutLayoutParams.gravity = Gravity.CENTER;
        mContainerLayout.setLayoutParams(linearLayoutLayoutParams);
        mContainerLayout.setGravity(Gravity.CENTER);
        mContainerLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams subtitleLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mContainerLayout.addView(mCustomTitleTextView, titleLayoutParams);
        mContainerLayout.addView(mCustomSubtitleTextView, subtitleLayoutParams);

        addView(mContainerLayout, linearLayoutLayoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mContainerLayout != null) {
            mContainerLayout.setX((getWidth() - mContainerLayout.getWidth()) / 2);
        }
    }

    @Override
    public void setTitleTextAppearance(Context context, @StyleRes int resId) {
        super.setTitleTextAppearance(context, resId);
        if (mCustomTitleTextView != null) {
            mCustomTitleTextView.setTextAppearance(context, resId);
        }
    }

    @Override
    public void setSubtitleTextAppearance(Context context, @StyleRes int resId) {
        super.setSubtitleTextAppearance(context, resId);
        if (mCustomSubtitleTextView != null) {
            mCustomSubtitleTextView.setTextAppearance(context, resId);
        }
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mCustomTitleTextView != null) {
            mCustomTitleTextView.setText(title);
            int visibility = TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE;
            mCustomTitleTextView.setVisibility(visibility);
        }
    }

    @Override
    public void setSubtitle(@StringRes int resId) {
        setSubtitle(getContext().getText(resId));
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        super.setSubtitle(subtitle);
        if (mCustomSubtitleTextView != null) {
            mCustomSubtitleTextView.setText(subtitle);
            int visibility = TextUtils.isEmpty(subtitle) ? View.GONE : View.VISIBLE;
            mCustomSubtitleTextView.setVisibility(visibility);
        }
    }

    @Override
    public void setTitleTextColor(@ColorInt int color) {
        super.setTitleTextColor(color);
        if (mCustomTitleTextView != null) {
            mCustomTitleTextView.setTextColor(color);
        }
    }

    @Override
    public void setSubtitleTextColor(@ColorInt int color) {
        super.setSubtitleTextColor(color);
        if (mCustomSubtitleTextView != null) {
            mCustomSubtitleTextView.setTextColor(color);
        }
    }

    public TextView getTitleTextView() {
        return mCustomTitleTextView;
    }

    public TextView getSubtitleTextView() {
        return mCustomSubtitleTextView;
    }


}