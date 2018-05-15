package com.narancommunity.app.activity.mine;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.ImageUtils;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.SDCardUtils;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AddressEntity;
import com.narancommunity.app.entity.UpdateFilesEntity;
import com.narancommunity.app.entity.UserInfo;
import com.narancommunity.app.net.AppConstants;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Writer：fancy on 2018/1/4 15:45
 * Email：120760202@qq.com
 * FileName :
 */

public class MyInfoAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_head)
    SelectableRoundedImageView ivHead;
    @BindView(R.id.ln_head)
    LinearLayout lnHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tr_sex)
    LinearLayout trSex;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tr_birthday)
    LinearLayout trBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_sign)
    TextView tvSign;

    CityPicker cityPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_info);
        ButterKnife.bind(this);

        setBar(toolbar);
        toolbar.setTitle("个人资料");

        cityPicker = new CityPicker(getContext(), new CityPickerListener() {
            @Override
            public void getCity(String name) {
                tvAddress.setText(name);
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", MApplication.getAccessToken());
                map.put("city", name);
                updateInfo(map);
            }
        });
    }

    private void setInfo() {
        UserInfo info = MApplication.getUserInfo(getContext());
        String photo = Utils.getValue(info.getPhoto());
        if (!"".equals(photo))
            Utils.setImgF(getContext(), photo, ivHead);
        else
            Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivHead);
        tvName.setText(Utils.getValue(info.getNickName()));
        tvSex.setText(Utils.getValue(info.getSex()));
        tvBirthday.setText(Utils.getValue(info.getBirthday()));
        tvAddress.setText(Utils.getValue(info.getCity()));
        tvSign.setText(Utils.getValue(info.getRemark()));
    }

    @OnClick({R.id.iv_head, R.id.tv_sex, R.id.tv_birthday, R.id.tv_address, R.id.tv_name, R.id.tv_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                showPhotoDialog();
                break;
            case R.id.tv_sex:
                selectGender();
                break;
            case R.id.tv_birthday:
                showBirthDay();
                break;
            case R.id.tv_address:
                cityPicker.show();
                break;
            case R.id.tv_name:
                startActivity(new Intent(getContext(), InfoEditAct.class).putExtra("type", 0));
                break;
            case R.id.tv_sign:
                startActivity(new Intent(getContext(), InfoEditAct.class).putExtra("type", 1));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (cityPicker.isShow()) {
            cityPicker.close();
            return;
        }
        super.onBackPressed();
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
                tvBirthday.setText(dateS);
                updateBirthday(dateS);
            }
        });
        dialog.show();
    }


    private void updateBirthday(String dateS) {
        final Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("birthday", dateS);
        LoadDialog.show(getContext());
        NRClient.saveUserInfo(map, new ResultCallback<Result<UserInfo>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Utils.showErrorToast(getContext(), throwable);
                LoadDialog.dismiss(getContext());
            }

            @Override
            public void onSuccess(Result<UserInfo> result) {
                LoadDialog.dismiss(getContext());
                MApplication.setUserInfo(result.getData());
            }
        });
    }

//    private void saveInfo() {
//        String name = etName.getText().toString().trim();
//        String sex = tvSex.getText().toString().trim();
//        String birth = tvBirthday.getText().toString().trim();
//        String location = tvAddress.getText().toString().trim();
//        String remark = tvSign.getText().toString().trim();
//        Map<String, Object> map = new HashMap<>();
//        map.put("accessToken", MApplication.getAccessToken());
//        map.put("nickName", name);
//        map.put("sex", sex);
//        map.put("birthday", birth);
//        map.put("city", location);
//        map.put("remark", remark);
//        updateInfo(map);
//    }

    private void updateInfo(final Map<String, Object> map) {
        LoadDialog.show(getContext());
        NRClient.saveUserInfo(map, new ResultCallback<Result<UserInfo>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Utils.showErrorToast(getContext(), throwable);
                LoadDialog.dismiss(getContext());
            }

            @Override
            public void onSuccess(Result<UserInfo> result) {
                LoadDialog.dismiss(getContext());
                MApplication.setUserInfo(result.getData());
            }
        });
    }

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

        TextView boyTv = (TextView) view.findViewById(R.id.boy_text);
        TextView girlTv = (TextView) view.findViewById(R.id.girl_text);
        TextView baomi_text = (TextView) view.findViewById(R.id.baomi_text);

        boyTv.setOnClickListener(new View.OnClickListener() { // 男生
            @Override
            public void onClick(View v) {
                tvSex.setText("男");
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", MApplication.getAccessToken());
                map.put("sex", "男");
                updateInfo(map);
                mWindowDialog.dismiss();
            }
        });
        girlTv.setOnClickListener(new View.OnClickListener() { // 女生

            @Override
            public void onClick(View arg0) {
                tvSex.setText("女");
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", MApplication.getAccessToken());
                map.put("sex", "女");
                updateInfo(map);
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

    private void setUser(UserInfo user) {
        MApplication.setUserInfo(user);
    }

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

    private void checkCamera(Dialog dialog) {
        //第二个参数是需要申请的权限
        //权限已经被授予，在这里直接写要执行的相应方法即可
        if (checkCameraPermissions()) {
            //启动严格模式，执行老方法
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SDCardUtils.getTempFile()));
            startActivityForResult(intent, GET_IMAGE_VIA_CAMERA);
            dialog.dismiss();
        } else {
            Toaster.toast(getContext(), "您未给我们打开相机的权限，没办法愉快的玩耍了！");
        }
    }

    private void checkGallery(Dialog dialog) {
        if (checkGalleryPermissions()) {
            //启动严格模式，执行老方法
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
            //权限已经被授予，在这里直接写要执行的相应方法即可
            Intent intent = new Intent(getContext(), MultiImageSelectorActivity.class);
            // 是否显示拍摄图片
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
            // 最大可选择图片数量
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, /*9 - (mAdapter.getCount() - 1)*/1);
            // 选择模式
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
            startActivityForResult(intent, GET_IMAGE_VIA_SDCARD);
            dialog.dismiss();
        } else {
            Toaster.toast(getContext(), "您未给我们打开相册的权限，没办法愉快的玩耍了！");
        }
    }

    private boolean checkGalleryPermissions() {
        return AndPermission.hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private boolean checkCameraPermissions() {
        return AndPermission.hasPermission(this, Manifest.permission.CAMERA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInfo();
        AndPermission.with(this)
                .requestCode(300)
                .permission(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .rationale(rationaleListener).callback(this)
                .start();
    }

    RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            AlertDialog.newBuilder(getContext()).setTitle("温馨提示")
                    .setMessage("你已拒绝过定位权限，沒有定位定位权限无法为你推荐附近的妹子，你看着办！")
                    .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            rationale.resume();
                        }
                    }).setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    rationale.cancel();
                }
            }).show();
        }
    };

    private static final int GET_IMAGE_VIA_SDCARD = 1000;
    private static final int GET_IMAGE_VIA_CAMERA = 1001;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GET_IMAGE_VIA_SDCARD: // 相册
                    List<String> mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    List<File> listFiles = new ArrayList<>();
                    for (int i = 0; i < mSelectPath.size(); i++) {
                        File file = new File(mSelectPath.get(i));
                        listFiles.add(file);
                    }
                    dealWithMapPic(listFiles);
                    break;
                case GET_IMAGE_VIA_CAMERA: // 相机
                    // 拍完照片直接返回，不用截图
                    File picture = SDCardUtils.getTempFile();
                    dealWithCamPic(picture);
                    break;
            }
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());
    boolean isUploaded = false;

    private void dealWithCamPic(final File picture) {
        LoadDialog.show(getContext(), "图片上传中，请稍候！");
        isUploaded = false;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                double sizes = Utils.getFileOrFilesSize(picture.getAbsolutePath(), Utils.SIZETYPE_KB);
                String filename = System.currentTimeMillis() + "";
                File tempFile = null;
                if (sizes > AppConstants.IMAGE_SIZE_LIMITED) {
                    try {
                        ImageUtils.compressAndGenImage(picture.getAbsolutePath()
                                , SDCardUtils.getDefineCompressFile(filename).getAbsolutePath()
                                , AppConstants.IMAGE_SIZE_LIMITED
                                , false);
                        tempFile = SDCardUtils.getDefineCompressFile(filename);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else
                    tempFile = picture;
                if (tempFile != null) {
//                    Utils.setImgF(getContext(), picture.getAbsoluteFile(), ivHead);
                    updateFiles(tempFile);
                }
                isUploaded = true;
            }
        }, 100);
    }

    private void dealWithMapPic(final List<File> listFiles) {
        LoadDialog.show(getContext(), "图片上传中，请稍候！");
        if (listFiles.size() > 0)
            isUploaded = false;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (File file : listFiles) {
                    String filename = System.currentTimeMillis() + "";
                    double size = Utils.getFileOrFilesSize(file.getAbsolutePath(), Utils.SIZETYPE_KB);
                    if (size > AppConstants.IMAGE_SIZE_LIMITED) {
                        try {
                            ImageUtils.compressAndGenImage(file.getAbsolutePath()
                                    , SDCardUtils.getDefineCompressFile(filename).getAbsolutePath()
                                    , AppConstants.IMAGE_SIZE_LIMITED
                                    , false);
                            File newFile = SDCardUtils.getDefineCompressFile(filename);
                            Utils.setImgF(getContext(), newFile.getAbsoluteFile(), ivHead);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
//                        Utils.setImgF(getContext(), file.getAbsoluteFile(), ivHead);
                    }
                    updateFiles(file);
                }
                isUploaded = true;
            }
        }, 100);
    }

//    StringBuffer sb = new StringBuffer();

    private void updateFiles(final File file) {
        NRClient.uploadOneFile(file, new ResultCallback<Result<UpdateFilesEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<UpdateFilesEntity> result) {
                LoadDialog.dismiss(getContext());
                if (result.getData() != null && result.getData().getData().size() > 0) {
                    Log.i("fancy", "fdsafasfadsfs----------");
//                    if (result.getData().getData().size() > 0) {
//                        sb.append(result.getData().getData().get(0) + ",");
//                    }
                    String pic = result.getData().getData().get(0)+"";
                    updateHead(pic, file);
                } else {
                    Toaster.toast(getContext(), "数据为空！");
                }
            }
        });
    }

    private void updateHead(final String url, final File file) {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("photo", url);
        NRClient.modifyHead(map, new ResultCallback<Result<UserInfo>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<UserInfo> result) {
                setUser(result.getData());
                Glide.with(MyInfoAct.this).load(file).dontAnimate().into(ivHead);
                LoadDialog.dismiss(getContext());
            }
        });
    }
}
