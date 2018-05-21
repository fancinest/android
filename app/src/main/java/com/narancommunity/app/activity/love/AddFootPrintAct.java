package com.narancommunity.app.activity.love;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/5/17 14:41
 * Email：120760202@qq.com
 * FileName :
 */
public class AddFootPrintAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_foot_print);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra("id", 0);
        toolbar.setTitle("添加足迹");
        setBar(toolbar);
    }

    private void addFoot() {
//        accessToken	是	string	登录标识
//        footprintId	是	Long	修改时传入
//        companyId	是	Long	机构ID
//        footprintTitle	是	String	标题
//        footprintContent	是	String	内容
//        footprintTime	是	String	时间
        String content = etRemark.getText().toString();
        String date = tvDate.getText().toString();
        if (Utils.isEmpty(content)) {
            Toaster.toast(getContext(), "请填写足迹内容!");
            etRemark.requestFocus();
            return;
        }
        if (Utils.isEmpty(date)) {
            Toaster.toast(getContext(), "请填写足迹时间!");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("footprintId", "");
        map.put("companyId", id);
        map.put("footprintTitle", "");
        map.put("footprintContent", content);
        map.put("footprintTime", date);
        NRClient.addFoot(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "添加成功!");
                finish();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    private void showBirthDay() {
        DatePickDialog dialog = new DatePickDialog(this);
        dialog.setStartDate(new Date());
        //设置上下年分限制
        dialog.setYearLimt(100);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMD);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                String dateS = Utils.dateToString(date, "yyyy-MM-dd");
                tvDate.setText(dateS);
            }
        });
        dialog.show();
    }

    @OnClick({R.id.tv_date, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                showBirthDay();
                break;
            case R.id.btn_commit:
                addFoot();
                break;
        }
    }
}
