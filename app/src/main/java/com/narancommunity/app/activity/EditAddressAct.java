package com.narancommunity.app.activity;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AddressEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/3 14:52
 * Email：120760202@qq.com
 * FileName :
 */

public class EditAddressAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.ctv_default)
    CheckedTextView ctvDefault;
    @BindView(R.id.btn_del)
    Button btnDel;

    AddressEntity address;
    int flag = 0;//0=新增地址，1=编辑地址

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_address);
        ButterKnife.bind(this);

        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 1) {
            address = (AddressEntity) getIntent().getSerializableExtra("address");
            etAddress.setText(address.getMailAddress());
            etTel.setText(address.getMailPhone());
            etName.setText(address.getMailName());
            tvArea.setText(address.getProvince() + address.getCity());
            mProvince = address.getProvince();
            mCity = address.getCity();
            mCounty = address.getCounty();
            ctvDefault.setChecked(address.isDefault());
        }
        setBar(toolbar);
        toolbar.setTitle(getMyTitle(flag));
        setView();
    }

    private void setView() {
        btnDel.setVisibility(flag == 0 ? View.GONE : View.VISIBLE);
    }

    private String getMyTitle(int flag) {
        return flag == 0 ? "新增地址" : "编辑地址";
    }

    @OnClick({R.id.ctv_default, R.id.btn_del, R.id.tv_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ctv_default:
                ctvDefault.setChecked(!ctvDefault.isChecked());
                break;
            case R.id.btn_del:
                delAddress();
                break;
            case R.id.tv_area:
                Utils.hideSoftInput(getContext());
                showAreaPop();
                break;
        }
    }

    private void delAddress() {
        LoadDialog.show(this, "删除中......");
        Map<String, Object> map = new HashMap<>();
        map.put("mailId", address.getMailId());
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        NRClient.delAddress(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                finish();
            }
        });
    }

    String mProvince, mCounty, mCity;

    private void addAddress() {
        LoadDialog.show(this, "新增地址...");
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("nowMail", ctvDefault.isChecked());
        map.put("mailName", etName.getText().toString());
        map.put("mailPhone", etTel.getText().toString());
        map.put("province", mProvince);
        map.put("city", mCity);
        map.put("county", mCounty);
        map.put("mailAddress", etAddress.getText().toString());
        NRClient.addAddress(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "新增成功！");
                finish();
            }
        });
    }

    private void modifyAddress() {
        LoadDialog.show(this, "修改地址...");
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("nowMail", ctvDefault.isChecked());
        map.put("mailName", etName.getText().toString());
        map.put("mailPhone", etTel.getText().toString());
        map.put("province", mProvince);
        map.put("city", mCity);
        map.put("county", mCounty);
        map.put("mailAddress", etAddress.getText().toString());
        map.put("mailId", address.getMailId());
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        NRClient.modifyAddress(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "新增成功！");
                finish();
            }
        });
    }

    private void showAreaPop() {
        //添加默认的配置，不需要自己定义
        setCityConfig();
        //监听选择点击事件及返回结果
        CityPickerView.getInstance().setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                //省份
                if (province != null) {
                    sb.append(province.getName());
                    mProvince = province.getName();
                }
                //城市
                if (city != null) {
                    sb.append(city.getName());
                    mCity = city.getName();
                }
                //地区
                if (district != null) {
                    sb.append(district.getName());
                    mCounty = district.getName();
                }
                tvArea.setText("" + sb.toString());
            }

            @Override
            public void onCancel() {
                Toaster.toast(getContext(), "已取消");
            }
        });

        //显示
        CityPickerView.getInstance().showCityPicker(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (etName.getText().toString().equals("")) {
                etName.requestFocus();
                Toaster.toast(getContext(), "请填写收货人!");
                return true;
            }
            if (etTel.getText().toString().equals("")) {
                etTel.requestFocus();
                Toaster.toast(getContext(), "请填写手机号码!");
                return true;
            }
            if (tvArea.getText().toString().equals("")) {
                etName.requestFocus();
                Toaster.toast(getContext(), "请选择地区!");
                return true;
            }
            if (etAddress.getText().toString().equals("")) {
                etAddress.requestFocus();
                Toaster.toast(getContext(), "请填写详细地址!");
                return true;
            }
            if (flag == 0)
                addAddress();
            else
                modifyAddress();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_complete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 预先加载仿iOS滚轮实现的全部数据
         */
        CityPickerView.getInstance().init(this);

        /**
         * 预先加载一级列表所有城市的数据
         */
        CityListLoader.getInstance().loadCityData(this);

        /**
         * 预先加载三级列表显示省市区的数据
         */
        CityListLoader.getInstance().loadProData(this);
    }

    private void setCityConfig() {
        CityConfig cityConfig = new CityConfig.Builder(this).title("请选择地区")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#585858")//标题文字颜色
                .titleBackgroundColor("#E9E9E9")//标题栏背景色
                .confirTextColor("#585858")//确认按钮文字颜色
                .confirmText("完成")//确认按钮文字
                .confirTextColor("#E3CD99")
                .confirmTextSize(18)//确认按钮文字大小
                .cancelTextColor("#585858")//取消按钮文字颜色
                .cancelText("  ")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(7)//显示item的数量
                .province("浙江省")//默认显示的省份
                .city("杭州市")//默认显示省份下面的城市
                .district("滨江区")//默认显示省市下面的区县数据
                .provinceCyclic(false)//省份滚轮是否可以循环滚动
                .cityCyclic(false)//城市滚轮是否可以循环滚动
                .districtCyclic(false)//区县滚轮是否循环滚动
//                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
//                .setCustomItemTextViewId(R.id.item_city_name_tv)//自定义item布局里面的textViewid
                .build();

        //设置自定义的属性配置
        CityPickerView.getInstance().setConfig(cityConfig);
    }
}
