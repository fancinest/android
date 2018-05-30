package com.narancommunity.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narancommunity.app.common.CenteredToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2017/12/19 14:41
 * Email：120760202@qq.com
 * FileName : 首页片段
 */

public class PetFragment extends Fragment {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;

    public static PetFragment newInstance() {

        PetFragment fragment = new PetFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_pet, container, false);
            ButterKnife.bind(this, rootView);

            toolbar.setTitle("敬请期待");
            toolbar.setNavigationIcon(R.drawable.transparent);
            return rootView;
        } else {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("PetFragment");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("petFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
