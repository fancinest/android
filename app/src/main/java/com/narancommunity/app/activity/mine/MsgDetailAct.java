package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.MsgEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/5/30 09:41
 * Email：120760202@qq.com
 * FileName :
 */
public class MsgDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.btn_go)
    Button btnGo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_detail);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("消息详情");
        MsgEntity entity = (MsgEntity) getIntent().getSerializableExtra("data");
        setView(entity);
    }

    private void setView(MsgEntity entity) {
        tvContent.setText(Utils.getValue(entity.getNewsContent()) + "");
        tvTime.setText(Utils.dateDiff(Utils.stringTimeToMillion(entity.getCreateTime())) + "");
    }

    @OnClick(R.id.btn_go)
    public void onViewClicked() {
        Toaster.toast(getContext(), "打算去哪？");
    }
}
