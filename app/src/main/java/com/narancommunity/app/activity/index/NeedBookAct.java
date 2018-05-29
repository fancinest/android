package com.narancommunity.app.activity.index;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.PicUploadAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.ExpandableHeightGridView;
import com.narancommunity.app.common.ImageUtils;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.SDCardUtils;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.UpdateFilesEntity;
import com.narancommunity.app.net.AppConstants;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Writer：fancy on 2018/4/13 14:11
 * Email：120760202@qq.com
 * FileName : 书荒求助发帖页面
 */

public class NeedBookAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.gridview)
    ExpandableHeightGridView mGridView;
    @BindView(R.id.ln_pic)
    LinearLayout lnPic;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    PicUploadAdapter mAdapter;
    int tag = 0;
    String fileName = "";//上传图片的地址

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_need_book);
        ButterKnife.bind(this);
        setBar(toolbar);

        tag = getIntent().getIntExtra("tag", 0);
        if (tag == 0) {
            etTitle.setHint("许下心愿");
            etContent.setHint("请说出你的故事");
            toolbar.setTitle("发布心愿");
        } else if (tag == 1) {
            toolbar.setTitle("书荒互助");
        } else if (tag == 2) {
            toolbar.setTitle("以书会友");
        }

        setView();
        setGrid();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        Utils.hideSoftInput(this);
        MobclickAgent.onPause(this);
    }

    private void setView() {
    }

    @OnClick(R.id.btn_complete)
    public void onViewClicked() {
        String title = etTitle.getText().toString();
        if ("".equals(title)) {
            Toaster.toast(getContext(), "请填写标题！");
            return;
        }
        String content = etContent.getText().toString();
        if ("".equals(content)) {
            Toaster.toast(getContext(), "请填写内容！");
            return;
        }

        if (isUploaded)
            releaseTopic();
        else {
            Toaster.toast(getContext(), "图片上传中，请稍后！");
        }
    }

    private void setGrid() {
        mGridView.setExpanded(true);
        mAdapter = new PicUploadAdapter(this, null);
        mAdapter.setMaxPicCount(9);
        mGridView.setAdapter(mAdapter);
        mAdapter.setMyOnClickListener(new PicUploadAdapter.MyOnClickListener() {

            @Override
            public void onLastClick(int position) {
                //  进入相册和图库	dialog
                showPhotoDialog();
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
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, /*9 - (mAdapter.getCount() - 1)*/1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
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

    private Uri getNewPath() {
        Uri mDestinationUri;
        String tempPath = "";
        tempPath = Environment.getExternalStorageDirectory() + "/NRCommunity/" + System.currentTimeMillis() + ".jpeg";
        File file = new File(tempPath);
        mDestinationUri = Uri.fromFile(file);
        return mDestinationUri;
    }

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
                    mAdapter.add(picture.getAbsolutePath());
//                    Utils.setImgF(getContext(), picture, ivCover);
                    updateFiles(tempFile);
                }
                isUploaded = true;
            }
        }, 100);
    }


    boolean isUploaded = false;

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
                            mAdapter.add(newFile.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mAdapter.add(file.getAbsolutePath());
                    }
                    updateFiles(file);
                }
                isUploaded = true;
            }
        }, 100);
    }

    StringBuffer sb = new StringBuffer();

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
                if (result.getData() != null && result.getData().getData() != null) {
                    if (result.getData().getData().size() > 0) {
                        sb.append(result.getData().getData().get(0) + ",");
                        fileName = result.getData().getData().get(0) + "";
                        Log.i("fancy", "filename = " + fileName);
//                        deleteTempFile();
                    }
                } else {
                    Toaster.toast(getContext(), "数据为空！");
                }
            }
        });
    }

    private void releaseTopic() {
        String pic_url = getPics();
        LoadDialog.show(getContext());
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("contentTitle", etTitle.getText().toString());
        map.put("contentBody", etContent.getText().toString());
        map.put("contentImg", pic_url);
        if (tag == 1) {
            //shhz
            NRClient.addSHHZ(map, new ResultCallback<Result<Void>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }

                @Override
                public void onSuccess(Result<Void> result) {
                    Toaster.toast(getContext(), "发布成功!");
                    LoadDialog.dismiss(getContext());
//                    setResult(2001, null);
//                    finish();
                }
            });
        } else if (tag == 2) {
            //YSHY
            NRClient.addYSHY(map, new ResultCallback<Result<Void>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }

                @Override
                public void onSuccess(Result<Void> result) {
                    Toaster.toast(getContext(), "发布成功!");
                    LoadDialog.dismiss(getContext());
//                    setResult(2001, null);
//                    finish();
                }
            });
        }

    }

    private String getPics() {
        String pics = sb.toString();
        if (pics.endsWith(",")) {
            pics = pics.substring(0, pics.length() - 1);
        }
        return pics;
    }

}
