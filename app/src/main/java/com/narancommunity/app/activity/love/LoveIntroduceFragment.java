package com.narancommunity.app.activity.love;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.narancommunity.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Writer：fancy on 2018/5/16 17:24
 * Email：120760202@qq.com
 * FileName : 机构简介
 */

public class LoveIntroduceFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.tv_content)
    TextView tvContent;


    public static LoveIntroduceFragment newInstance() {

        LoveIntroduceFragment fragment = new LoveIntroduceFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Activity activity;

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
            rootView = inflater.inflate(R.layout.fragment_love_introduce, container, false);
            unbinder = ButterKnife.bind(this, rootView);

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

    public void setView(String content) {
        tvContent.setText(content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
    }

    private void getData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("LoveIntroduceFragment");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("LoveIntroduceFragment");
    }

}
