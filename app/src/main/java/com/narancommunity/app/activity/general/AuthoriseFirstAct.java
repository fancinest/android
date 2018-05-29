package com.narancommunity.app.activity.general;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.UserInfo;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/4 15:10
 * Email：120760202@qq.com
 * FileName :
 */

public class AuthoriseFirstAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_card_type)
    TextView tvCardType;
    @BindView(R.id.tv_idCard)
    TextView tvIdCard;
    @BindView(R.id.tv_verify_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;

//    String mProvince = "", mCounty = "", mCity = "";
//    RelativeLayout selectDate, selectTime;
//    CustomDatePicker customDatePicker1, customDatePicker2;
    boolean isMustAuthorise;
    String code = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_authorise_first);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("实名认证");
//        initDatePicker();
        isMustAuthorise = getIntent().getBooleanExtra("isMustAuthorise", false);
        if (isMustAuthorise) {
            toolbar.setNavigationIcon(R.color.transparent);
            toolbar.setClickable(false);
        }
        MApplication.putActivity("authorisefirst", getContext());
        UserInfo mInfo = MApplication.getUserInfo(getContext());
        String mobile = Utils.getValue(mInfo.getPhone());
        if (!mobile.equals("")) {
            tvMobile.setText(mobile);
        }
    }

    @OnClick({R.id.tv_sex, R.id.tv_verify_mobile,
            R.id.tv_idCard, R.id.btn_sure, R.id.tv_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sex:
                selectGender();
                break;
            case R.id.tv_verify_mobile:
                startActivityForResult(new Intent(getContext(), VerifyMobileAct.class), 10);
                break;
            case R.id.tv_idCard:
//                showAreaPop();
                startActivityForResult(new Intent(getContext(), AddIDNumAct.class).putExtra("type", 1), 11);
                break;
            case R.id.tv_name:
                startActivityForResult(new Intent(getContext(), AddIDNumAct.class).putExtra("type", 0), 11);
                break;
            case R.id.btn_sure:
                if (tvName.getText().toString().equals("")) {
                    Toaster.toast(getContext(), "请先填写姓名");
                    return;
                }
                if (tvSex.getText().toString().equals("")) {
                    Toaster.toast(getContext(), "请先选择性别");
                    return;
                }
                if (tvIdCard.getText().toString().equals("")) {
                    Toaster.toast(getContext(), "请先填写身份证号码");
                    return;
                } else if (tvIdCard.getText().toString().length() != 18) {
                    Toaster.toastLong(getContext(), "身份证号码长度为18位哦");
                    return;
                }
                if (tvMobile.getText().toString().equals("")) {
                    Toaster.toast(getContext(), "请先验证手机号");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", MApplication.getAccessToken(getContext()));
                map.put("name", tvName.getText().toString());
                map.put("sex", tvSex.getText().toString());
                map.put("identityCard", tvIdCard.getText().toString());
                map.put("mobile", tvMobile.getText().toString());
                map.put("verifyCode", code);
                startActivity(new Intent(getContext(), AuthoriseSecondAct.class)
                        .putExtra("data", (Serializable) map));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && data != null) {
            String mobile = data.getStringExtra("mobile");
            code = data.getStringExtra("code");
            tvMobile.setText(mobile);
        } else if (requestCode == 11 && data != null) {
            if (resultCode == 11) {
                String idNum = data.getStringExtra("idNum");
                tvIdCard.setText("" + idNum);
            } else if (resultCode == 12) {
                String name = data.getStringExtra("name");
                tvName.setText("" + name);
            }
        }
    }

    //    private void initDatePicker() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//        tvUsefulDate.setText(now.split(" ")[0]);
//
//        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
//            @Override
//            public void handle(String time) { // 回调接口，获得选中的时间
//                tvUsefulDate.setText(time.split(" ")[0]);
//            }
//        }, now, "2100-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
//        customDatePicker1.showSpecificTime(false); // 不显示时和分
//        customDatePicker1.setIsLoop(false); // 不允许循环滚动
//
////        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
////            @Override
////            public void handle(String time) { // 回调接口，获得选中的时间
////                currentTime.setText(time);
////            }
////        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
////        customDatePicker2.showSpecificTime(true); // 显示时和分
////        customDatePicker2.setIsLoop(true); // 允许循环滚动
//    }

    /**
     * 选择性别的
     */
    Dialog mWindowDialog;

    public void selectGender() {

        mWindowDialog = new Dialog(this, R.style.more_dialog);
        View view = LinearLayout.inflate(this, R.layout.dialog_select_gender, null);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWindowDialog != null) {
                    mWindowDialog.dismiss();
                }
            }
        });
        mWindowDialog.setContentView(view);
        mWindowDialog.setCanceledOnTouchOutside(true);
        view.getLayoutParams().width = getWindow().getWindowManager().getDefaultDisplay().getWidth();

        TextView boyTv = view.findViewById(R.id.boy_text);
        TextView girlTv = view.findViewById(R.id.girl_text);
        TextView baomi_text = view.findViewById(R.id.baomi_text);

        boyTv.setOnClickListener(new View.OnClickListener() { // 男生
            @Override
            public void onClick(View v) {
                tvSex.setText("男");
                mWindowDialog.dismiss();
            }
        });
        girlTv.setOnClickListener(new View.OnClickListener() { // 女生

            @Override
            public void onClick(View arg0) {
                tvSex.setText("女");
                mWindowDialog.dismiss();
            }
        });
        baomi_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowDialog.dismiss();
            }
        });

        mWindowDialog.show();

    }

//    private void showAreaPop() {
//        //添加默认的配置，不需要自己定义
//        setCityConfig();
//        //监听选择点击事件及返回结果
//        CityPickerView.getInstance().setOnCityItemClickListener(new OnCityItemClickListener() {
//            @Override
//            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
//                StringBuilder sb = new StringBuilder();
//                //省份
//                if (province != null) {
//                    sb.append(province.getName());
//                    mProvince = province.getName();
//                }
//                //城市
//                if (city != null) {
//                    sb.append(city.getName());
//                    mCity = city.getName();
//                }
//                //地区
//                if (district != null) {
//                    sb.append(district.getName());
//                    mCounty = district.getName();
//                }
//                tvArea.setText("" + sb.toString());
//            }
//
//            @Override
//            public void onCancel() {
//                Toaster.toast(getContext(), "已取消");
//            }
//        });
//
//        //显示
//        CityPickerView.getInstance().showCityPicker(this);
//    }

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

//    @Override
//    protected void onResume() {
//        super.onResume();
////        /**
////         * 预先加载仿iOS滚轮实现的全部数据
////         */
////        CityPickerView.getInstance().init(this);
////
////        /**
////         * 预先加载一级列表所有城市的数据
////         */
////        CityListLoader.getInstance().loadCityData(this);
////
////        /**
////         * 预先加载三级列表显示省市区的数据
////         */
////        CityListLoader.getInstance().loadProData(this);
//    }

//    private void setCityConfig() {
//        CityConfig cityConfig = new CityConfig.Builder(this).title("请选择地区")//标题
//                .titleTextSize(18)//标题文字大小
//                .titleTextColor("#585858")//标题文字颜色
//                .titleBackgroundColor("#E9E9E9")//标题栏背景色
//                .confirTextColor("#585858")//确认按钮文字颜色
//                .confirmText("完成")//确认按钮文字
//                .confirTextColor("#E3CD99")
//                .confirmTextSize(18)//确认按钮文字大小
//                .cancelTextColor("#585858")//取消按钮文字颜色
//                .cancelText("  ")//取消按钮文字
//                .cancelTextSize(16)//取消按钮文字大小
//                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
//                .showBackground(true)//是否显示半透明背景
//                .visibleItemsCount(7)//显示item的数量
//                .province("浙江省")//默认显示的省份
//                .city("杭州市")//默认显示省份下面的城市
//                .district("滨江区")//默认显示省市下面的区县数据
//                .provinceCyclic(false)//省份滚轮是否可以循环滚动
//                .cityCyclic(false)//城市滚轮是否可以循环滚动
//                .districtCyclic(false)//区县滚轮是否循环滚动
////                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
////                .setCustomItemTextViewId(R.id.item_city_name_tv)//自定义item布局里面的textViewid
//                .build();
//
//        //设置自定义的属性配置
//        CityPickerView.getInstance().setConfig(cityConfig);
//    }
}
