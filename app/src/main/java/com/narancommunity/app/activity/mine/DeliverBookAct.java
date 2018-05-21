package com.narancommunity.app.activity.mine;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.SDCardUtils;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AddressEntity;
import com.narancommunity.app.entity.WishEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import simplezxing.activity.CaptureActivity;

/**
 * Writer：fancy on 2018/5/21 20:18
 * Email：120760202@qq.com
 * FileName : 快递单号填写页面
 */
public class DeliverBookAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.ln_name)
    LinearLayout lnName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ln_address)
    LinearLayout lnAddress;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_writer)
    TextView tvWriter;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.card_parent)
    LinearLayout cardParent;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.btn_go)
    Button btnGo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_deliver_book);
        ButterKnife.bind(this);
        toolbar.setTitle("传给TA");
        setBar(toolbar);
        WishEntity entity = (WishEntity) getIntent().getSerializableExtra("data");
        setBook(entity);

        tvSelectAddress.setVisibility(View.VISIBLE);
        lnAddress.setVisibility(View.GONE);
    }

    private void setBook(WishEntity entity) {
        String url = Utils.getValue(entity.getOrderImgs());
        if (!"".equals(url))
            Utils.setImgF(getContext(), url, ivImg);
        tvWriter.setText("" + Utils.getValue(entity.getOrderAuthor()));
        tvBookName.setText("" + Utils.getValue(entity.getOrderTitle()));
        tvDesc.setText("" + Utils.getValue(entity.getOrderContent()));

    }

    @OnClick({R.id.ln_address, R.id.iv_scan, R.id.btn_go, R.id.tv_select_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_address:
            case R.id.ln_address:
                Intent its = new Intent(getContext(), AddressAct.class);
                its.putExtra("isForResult", true);
                startActivityForResult(its, 2000);
                break;
            case R.id.iv_scan:
                checkCamera(null);
                break;
            case R.id.btn_go:
                break;
        }
    }

    private void checkCamera(Dialog dialog) {
        //第二个参数是需要申请的权限
        //权限已经被授予，在这里直接写要执行的相应方法即可
        if (checkPermissions()) {
            //启动严格模式，执行老方法
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
            startCaptureActivityForResult();
        }
    }

    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_DATA = 2;
    private static final int REQ_CODE_PERMISSION = 11;

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")) {
                showMessageOKCancel("请允许使用相机！",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getContext(),
                                        new String[]{"android.permission.CAMERA"}, MY_PERMISSIONS_REQUEST_USE_CAMERA);
                            }
                        });
                return false;
            }
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, MY_PERMISSIONS_REQUEST_USE_CAMERA);
            return false;
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
                showMessageOKCancel("请允许读取相册！",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getContext(),
                                        new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, MY_PERMISSIONS_REQUEST_READ_DATA);
                            }
                        });
                return false;
            }
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, MY_PERMISSIONS_REQUEST_READ_DATA);
            return false;
        } else
            return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("好的", okListener)
                .setNegativeButton("不行", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_USE_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PermissionsResult", "GRANTED WRITE_DATA");
                    // permission allowed
//                    checkPermissions();
                } else {
                    Log.d("PermissionsResult", "DENIED WRITE_DATA");
                    // permission denied
                    Toaster.toastLong(getContext(), "软件权限不足无法使用相机！");
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_DATA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PermissionsResult", "GRANTED READ_DATA");
                    // permission allowed
//                    checkPermissions();
                } else {
                    Log.d("PermissionsResult", "DENIED READ_DATA");
                    // permission denied
                    Toaster.toastLong(getContext(), "软件权限不足无法读取相册！");
                }
            }
            break;
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //权限开启成功 跳转至扫描二维码页面
                    startCaptureActivityForResult();
                } else {
                    //权限开启失败 显示提示信息
                    showMissingPermissionDialog();
                }
            }
            break;
        }
    }

    /**
     * 跳转至扫描页面
     */
    private void startCaptureActivityForResult() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }


    /**
     * 显示提示信息
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转至当前应用的权限设置页面
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    AddressEntity mAddress = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && data != null) {
            AddressEntity address = (AddressEntity) data.getSerializableExtra("address");
            mAddress = address;
            setAddress(address);
        }
        if (requestCode == CaptureActivity.REQ_CODE)
            switch (resultCode) {
                case RESULT_OK:
                    etNum.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                    break;
                case RESULT_CANCELED:
                    if (data != null) {
                        etNum.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                    }
                    break;
            }
    }

    private void setAddress(AddressEntity address) {
        tvSelectAddress.setVisibility(View.GONE);
        lnAddress.setVisibility(View.VISIBLE);
        tvName.setText("收货人：" + Utils.getValue(address.getMailName()) + "");
        tvTel.setText(Utils.getValue(address.getMailPhone()) + "");
        tvAddress.setText("收货地址：" + Utils.getValue(address.getProvince() + address.getCity() + address.getCounty()
                + address.getMailAddress()) + "");
    }
}
