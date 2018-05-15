package com.narancommunity.app.activity.index;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/12 19:34
 * Email：120760202@qq.com
 * FileName : 老版是{@link JubaoAct}
 */

public class ReportAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout flowlayout;
    @BindView(R.id.et_memo)
    EditText etMemo;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    String[] reasonArr = new String[]{"垃圾营销", "不实信息", "有害信息", "违法信息", "淫秽色情", "人身攻击", "内容抄袭", "封面缺失", "内页缺失", "乱涂乱画", "受潮损坏"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_report);
        ButterKnife.bind(this);
        toolbar.setTitle("举报");
        setBar(toolbar);

        setView();
    }

    private void setView() {
        flowlayout.setAdapter(new TagAdapter<String>(reasonArr) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tag_item,
                        flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        Set<Integer> s = flowlayout.getSelectedList();
        List<String> reasons = new ArrayList<>();
        if (s.size() == 0) {
            Toaster.toast(getContext(), "请至少选择一个理由");
            return;
        } else {
            for (Integer position : s) {
                reasons.add(reasonArr[position]);
            }
            //TODO
            showPopView(btnCommit);
        }
    }

    PopupWindow mPop;

    private void showPopView(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.normal_pop, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());
            TextView tv_content = (TextView) v.findViewById(R.id.dial);
            View line = v.findViewById(R.id.viewLine);
            TextView cancel = v.findViewById(R.id.cancel);
            TextView ok = v.findViewById(R.id.ok);
            cancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            tv_content.setTextColor(getResources().getColor(R.color.color_333333));
            tv_content.setText("举报已受理");

            ok.setTextColor(getResources().getColor(R.color.appBlue));
            ok.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                    finish();
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
