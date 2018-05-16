package com.narancommunity.app.activity.love;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.mine.AddressAct;
import com.narancommunity.app.adapter.PicUploadAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.ExpandableHeightGridView;
import com.narancommunity.app.common.ImageUtils;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.SDCardUtils;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AddressEntity;
import com.narancommunity.app.entity.UpdateFilesEntity;
import com.narancommunity.app.net.AppConstants;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Writer：fancy on 2018/5/14 17:58
 * Email：120760202@qq.com
 * FileName :  爱心-入驻
 */
public class SettleDownAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.gridview)
    ExpandableHeightGridView gridview;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_man)
    EditText etMan;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tagFlow_type)
    TagFlowLayout tagFlowType;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_do)
    Button btnDo;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.tv_logo_prompt)
    TextView tvLogoPrompt;

    PicUploadAdapter mAdapter;
    String fileLogo = "";//logo的地址
    String[] sortArr = new String[]{"慈善机构", "慈善家", "爱心协会", "公益使者"};
    boolean isLogo = true;//默认点击是logo

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_settle_down);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("入驻");

        setSort();
        setGrid();
    }

    private void setSort() {
        tagFlowType.setAdapter(new TagAdapter<String>(sortArr) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tag_wrap,
                        tagFlowType, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    @OnClick({R.id.tv_create_time, R.id.tv_address, R.id.btn_do, R.id.iv_cover})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_create_time:
                showCreateDay();
                break;
            case R.id.tv_address:
                startActivityForResult(new Intent(getContext(), AddressAct.class).putExtra("isForResult", true), 2001);
                break;
            case R.id.btn_do:
                if ("".equals(fileLogo)) {
                    Toaster.toast(getContext(), "请先上传机构logo");
                    return;
                }
                if (listUpdate.size() <= 0) {
                    Toaster.toast(getContext(), "请先上传等级证书");
                    return;
                }
                String name = etName.getText().toString();
                String man = etMan.getText().toString();
                String tel = etTel.getText().toString();
                String date = tvCreateTime.getText().toString();
                String address = tvAddress.getText().toString();
                String remark = etRemark.getText().toString();
                String type = getSortType();
                if ("".equals(name)) {
                    Toaster.toast(getContext(), "请先填写机构名称");
                    etName.requestFocus();
                    return;
                }
                if ("".equals(man)) {
                    Toaster.toast(getContext(), "请先填写负责人");
                    etMan.requestFocus();
                    return;
                }
                if ("".equals(tel)) {
                    Toaster.toast(getContext(), "请先填写联系电话");
                    etTel.requestFocus();
                    return;
                }
                if ("".equals(date)) {
                    Toaster.toast(getContext(), "请先选择成立日期");
                    return;
                }
                if ("".equals(address)) {
                    Toaster.toast(getContext(), "请先选择机构地址");
                    return;
                }
                if ("".equals(type)) {
                    Toaster.toast(getContext(), "请先上传等级证书");
                    return;
                }
                if ("".equals(remark)) {
                    Toaster.toast(getContext(), "请先填写简介");
                    etRemark.requestFocus();
                    return;
                } else if (remark.length() < 20) {
                    Toaster.toast(getContext(), "简介不得少于20个字且不能大于3000个字");
                    etRemark.requestFocus();
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", MApplication.getAccessToken(getContext()));
                map.put("companyId", "");
                map.put("companyName", name);
                map.put("charger", man);
                map.put("establishTime", date);
                map.put("phone", tel);
                map.put("companyType", type);
                map.put("companyImg", fileLogo);
                map.put("companyContent", remark);
                map.put("certificate", getPics());
                map.put("province", mAddress.getProvince());
                map.put("city", mAddress.getCity());
                map.put("county", mAddress.getCounty());
                map.put("address", mAddress.getProvince() + mAddress.getCity() + mAddress.getCounty());
                map.put("ordinate", 0);
                map.put("abscissa", 0);
                doSettleDown(map);

                break;
            case R.id.iv_cover:
                isLogo = true;
                showPhotoDialog();
                break;
        }
    }

    private void doSettleDown(Map<String, Object> map) {
        LoadDialog.show(this, "正在申请...");
        NRClient.orgSettleDown(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "申请成功!");
            }
        });
    }


    private String getSortType() {
        String condition = "";
        Set<Integer> s = tagFlowType.getSelectedList();
        if (s.size() == 0)
            return "";
        for (Integer position : s) {
            condition = sortArr[position];
        }
        return condition;
    }

    private void showCreateDay() {
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
                tvCreateTime.setText(dateS);
            }
        });
        dialog.show();
    }


    private String getPics() {
        if (listUpdate.size() <= 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (String pic : listUpdate) {
            sb.append(pic);
            sb.append(",");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private void setGrid() {
        gridview.setExpanded(true);
        mAdapter = new PicUploadAdapter(this, null);
        mAdapter.setMaxPicCount(9);
        gridview.setAdapter(mAdapter);
        mAdapter.delItem(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {

            }

            @Override
            public void OnDelClick(int position) {
                listUpdate.remove(position);
            }
        });
        mAdapter.setMyOnClickListener(new PicUploadAdapter.MyOnClickListener() {

            @Override
            public void onLastClick(int position) {
                //  进入相册和图库	dialog
                isLogo = false;
                if (!isUploaded)
                    showPhotoDialog();
                else Toaster.toast(getContext(), "图片上传中，请稍候");
            }

            @Override
            public void onItemClick(int position) {
                // 查看大图 和 删除	dialog
            }
        });
    }

    private static final int GET_IMAGE_VIA_SDCARD = 1000;
    private static final int GET_IMAGE_VIA_CAMERA = 1001;
    private static final int CROP_IMAGE = 1005;

    private void showPhotoDialog() {
        final Dialog dialog = new Dialog(this, R.style.more_dialog);
        View view = LinearLayout.inflate(this, R.layout.dialog_photo, null);

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        LinearLayout cameraLl = view.findViewById(R.id.photoLl);
        LinearLayout localLl = view.findViewById(R.id.tukuLl);
        LinearLayout ln_cancel = view.findViewById(R.id.ln_cancel);

        cameraLl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) { // 相机
                checkCamera(dialog);
            }
        });
        localLl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) { // 相册
                checkGallery(dialog);
            }
        });
        ln_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) { // 取消
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void checkGallery(Dialog dialog) {
        if (checkPermissions()) {
            //启动严格模式，执行老方法
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
            //权限已经被授予，在这里直接写要执行的相应方法即可
            Intent intent = new Intent(getContext(), MultiImageSelectorActivity.class);
//                // 是否显示拍摄图片
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
            // 最大可选择图片数量
            if (isLogo)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, /*9 - (mAdapter.getCount() - 1)*/1);
            else {
                int have = mAdapter.getUploadPic().size();
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9 - have);
            }
            // 选择模式
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
            startActivityForResult(intent, GET_IMAGE_VIA_SDCARD);
            dialog.dismiss();
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
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SDCardUtils.getTempFile()));
            startActivityForResult(intent, GET_IMAGE_VIA_CAMERA);
            dialog.dismiss();
        }
    }

    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_DATA = 2;

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
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());
    String scaleType;
    AddressEntity mAddress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2001 && data != null) {
            AddressEntity address = (AddressEntity) data.getSerializableExtra("address");
            mAddress = address;
            setAddress(address);
        } else if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case GET_IMAGE_VIA_CAMERA: // 相机
                    // 拍完照片直接返回，不用截图
                    File picture = SDCardUtils.getTempFile();
                    dealWithCamPic(picture);
                    break;
                case GET_IMAGE_VIA_SDCARD: // 相册
                    List<String> mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    List<File> listFiles = new ArrayList<>();
                    for (int i = 0; i < mSelectPath.size(); i++) {
                        File file = new File(mSelectPath.get(i));
                        listFiles.add(file);
                    }
                    dealWithMapPic(listFiles);
                    break;
            }
        }
    }

    private void setAddress(AddressEntity address) {
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText(Utils.getValue(address.getProvince() + address.getCity() + address.getCounty()
                + address.getMailAddress()) + "");
    }

    private Uri getNewPath() {
        Uri mDestinationUri;
        String tempPath = "";
        tempPath = Environment.getExternalStorageDirectory() + "/NRCommunity/" + System.currentTimeMillis() + ".jpeg";
        File file = new File(tempPath);
        mDestinationUri = Uri.fromFile(file);
        return mDestinationUri;
    }

    int updateCount = 0;//正在上传的图片的数量

    private void dealWithCamPic(final File picture) {
        updateCount = 1;
        if (isLogo) {
            tvLogoPrompt.setText("图片上传中...");
            tvLogoPrompt.setVisibility(View.VISIBLE);
        } else {
            tvPrompt.setVisibility(View.VISIBLE);
            tvPrompt.setText("图片上传中...");
        }
        isUploaded = false;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                double sizes = Utils.getFileOrFilesSize(picture.getAbsolutePath(), Utils.SIZETYPE_KB);
                String filename = System.currentTimeMillis() + "";
                File tempFile = picture;
                if (sizes > AppConstants.IMAGE_SIZE_LIMITED) {
                    try {
                        ImageUtils.compressAndGenImage(picture.getAbsolutePath()
                                , SDCardUtils.getDefineCompressFile(filename).getAbsolutePath()
                                , AppConstants.IMAGE_SIZE_LIMITED
                                , false);
                        tempFile = SDCardUtils.getDefineCompressFile(filename);//新的压缩过的图片
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (tempFile != null) {
                    if (!isLogo)
                        mAdapter.add(tempFile.getAbsolutePath());
                    else
                        Utils.setImgF(getContext(), tempFile, ivCover);
                    isUploaded = true;
                    updateFiles(tempFile);
                }
            }
        }, 100);
    }


    boolean isUploaded = false;

    private void dealWithMapPic(final List<File> listFiles) {
        updateCount = listFiles.size();
        if (isLogo) {
            tvLogoPrompt.setText("图片上传中...");
            tvLogoPrompt.setVisibility(View.VISIBLE);
        } else {
            tvPrompt.setVisibility(View.VISIBLE);
            tvPrompt.setText("图片上传中...");
        }
        if (listFiles.size() > 0)
            isUploaded = false;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (File file : listFiles) {
                    String filename = System.currentTimeMillis() + "";
                    double size = Utils.getFileOrFilesSize(file.getAbsolutePath(), Utils.SIZETYPE_KB);
                    File tempFile = file;
                    if (size > AppConstants.IMAGE_SIZE_LIMITED) {
                        try {
                            ImageUtils.compressAndGenImage(file.getAbsolutePath()
                                    , SDCardUtils.getDefineCompressFile(filename).getAbsolutePath()
                                    , AppConstants.IMAGE_SIZE_LIMITED
                                    , false);
                            tempFile = SDCardUtils.getDefineCompressFile(filename);
                            if (!isLogo)
                                mAdapter.add(tempFile.getAbsolutePath());
                            else Utils.setImgF(getContext(), tempFile, ivCover);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (tempFile != null) {
                        if (!isLogo)
                            mAdapter.add(tempFile.getAbsolutePath());
                        else
                            Utils.setImgF(getContext(), tempFile.getAbsolutePath(), ivCover);
                        isUploaded = true;
                        updateFiles(tempFile);
                    }
                }
            }
        }, 100);
    }

    List<String> listUpdate = new ArrayList<>();

    private void updateFiles(final File file) {
        NRClient.uploadOneFile(file, new ResultCallback<Result<UpdateFilesEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                isUploaded = false;
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<UpdateFilesEntity> result) {
                LoadDialog.dismiss(getContext());
                if (result.getData() != null && result.getData().getData() != null) {
                    updateCount--;
                    if (result.getData().getData().size() > 0) {
                        if (isLogo)
                            fileLogo = result.getData().getData().get(0);
                        else {
                            listUpdate.add(result.getData().getData().get(0));
                        }
                    }
                    if (updateCount <= 0) {
                        if (isLogo) {
                            tvLogoPrompt.setText("图片上传完成");
                            tvLogoPrompt.setVisibility(View.VISIBLE);
                        } else {
                            tvPrompt.setVisibility(View.VISIBLE);
                            tvPrompt.setText("图片上传完成");
                        }
                        isUploaded = false;
                    } else isUploaded = true;
                } else {
                    Toaster.toast(getContext(), "数据为空！");
                }
//                if (isLogo) {
//                    tvLogoPrompt.setText("图片上传完成");
//                    tvLogoPrompt.setVisibility(View.VISIBLE);
//                } else {
//                    tvPrompt.setVisibility(View.VISIBLE);
//                    tvPrompt.setText("图片上传完成");
//                }
            }
        });
    }
}
