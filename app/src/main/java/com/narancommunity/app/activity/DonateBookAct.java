package com.narancommunity.app.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.narancommunity.app.entity.BookDetail;
import com.narancommunity.app.entity.BookListData;
import com.narancommunity.app.entity.UpdateFilesEntity;
import com.narancommunity.app.interfaces.PicLoadCallBack;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import simplezxing.activity.CaptureActivity;

/**
 * Writer：fancy on 2018/4/12 09:46
 * Email：120760202@qq.com
 * FileName : 赠书页面
 */

public class DonateBookAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    //    @BindView(R.id.gridview)
//    ExpandableHeightGridView mGridView;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.ln_pic)
    LinearLayout lnPic;
    @BindView(R.id.et_book_name)
    EditText etBookName;
    @BindView(R.id.et_writer)
    EditText etWriter;
    @BindView(R.id.et_pages)
    EditText etPages;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_publisher)
    EditText etPublisher;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.tagFlow_condition)
    TagFlowLayout tagFlowCondition;
    @BindView(R.id.tagFlow_type)
    TagFlowLayout tagFlowType;
    @BindView(R.id.et_evaluate)
    EditText etEvaluate;
    @BindView(R.id.btn_want)
    Button btnDonate;

    String fileName = "";//上传图片的地址
    Integer bookId;//书籍ID，有ID就不用传图片了
    String isbnCode = "";
    String average = "";//平均分

    String[] bookCondition = new String[]{"全新", "九成新 ", "七成新", "六成新以下"};
    String[] bookType = new String[]{"教育教科", "文学小说", "人文社科", "童书绘本", "成功励志", "生活艺术", "金融经管", "其他书籍"};
    String[] realType = new String[]{"BOOK_EDUCATION", "BOOK_NOVEL", "BOOK_HUMANITY", "BOOK_CHILD", "BOOK_SUCCESS", "BOOK_LIFE", "BOOK_FINANCE", "BOOK_OTHER"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_donate_book);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("赠书");

        setView();
    }

    private void setView() {
        tagFlowCondition.setAdapter(new TagAdapter<String>(bookCondition) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tag_wrap,
                        tagFlowCondition, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowType.setAdapter(new TagAdapter<String>(bookType) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tag_wrap,
                        tagFlowType, false);
                tv.setText(s);
                return tv;
            }
        });
        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_scan) {
            startCaptureActivityForResult();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.ln_pic, R.id.btn_want})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ln_pic:
                showPhotoDialog();
                break;
            case R.id.btn_want:
                if (fileName.equals("")) {
                    Toaster.toast(getContext(), "请上传一张封面图片！");
                    return;
                }
                String bookName = etBookName.getText().toString();
                String author = etWriter.getText().toString();
                String pages = etPages.getText().toString();
                String price = etPrice.getText().toString();
                String publisher = etPublisher.getText().toString();
                String desc = etDesc.getText().toString();
                String memo = etEvaluate.getText().toString();
                if (bookName.equals("")) {
                    etBookName.requestFocus();
                    Toaster.toast(getContext(), "请填写书名！");
                    return;
                }
                if (author.equals("")) {
                    etWriter.requestFocus();
                    Toaster.toast(getContext(), "请填写作者！");
                    return;
                }
                if (pages.equals("")) {
                    etPages.requestFocus();
                    Toaster.toast(getContext(), "请填写页数！");
                    return;
                }
                if (price.equals("")) {
                    etPrice.requestFocus();
                    Toaster.toast(getContext(), "请填写价格！");
                    return;
                }
                if (publisher.equals("")) {
                    etPublisher.requestFocus();
                    Toaster.toast(getContext(), "请填写出版社！");
                    return;
                }
                if (desc.equals("")) {
                    etDesc.requestFocus();
                    Toaster.toast(getContext(), "请填写描述！");
                    return;
                }
                String condition = getCondition();
                String sort = getSort();
                if (condition.equals("")) {
                    Toaster.toast(getContext(), "请选择书籍的新旧程度！");
                    return;
                }
                if (sort.equals("")) {
                    Toaster.toast(getContext(), "请选择书籍分类！");
                    return;
                }

                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", MApplication.getAccessToken(getContext()));
                map.put("bookId", bookId == 0 ? "" : bookId);
                map.put("isbn", isbnCode);
                map.put("title", bookName);
                map.put("pages", pages);
                map.put("author", author);
                map.put("publisher", publisher);
                map.put("price", price);
                map.put("average", average);
                map.put("bookImg", fileName);
                map.put("tags", sort);
                map.put("agingDegree", condition);
                map.put("bookReview", memo);
                map.put("summary", desc);
                Log.i("fancy", "data :" + map.toString());
                donateBook(map);
                break;
        }
    }

    private String getCondition() {
        String condition = "";
        Set<Integer> s = tagFlowCondition.getSelectedList();
        if (s.size() == 0)
            return "";
        for (Integer position : s) {
            condition = bookCondition[position];
        }
        return condition;
    }

    private String getSort() {
        String sort = "";
        Set<Integer> s = tagFlowType.getSelectedList();
        if (s.size() == 0)
            return "";
        for (Integer position : s) {
            sort = realType[position];
        }
        return sort;
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
            tv_content.setText("你捐赠的书已经上架了，快去提醒求书人来借阅吧！");

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CaptureActivity.REQ_CODE)
            switch (resultCode) {
                case RESULT_OK:
//                    etBookName.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));  //or do sth
                    getBookDetail(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                    break;
                case RESULT_CANCELED:
                    if (data != null) {
//                        etBookName.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        getBookDetail(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                    }
                    break;
            }
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

    private void getBookDetail(final String code) {
        //TODO 如果有条形码的规则就可以在这里进行判断
//        if ()
        Log.i("fancy", " code =" + code);
        isbnCode = code;
        LoadDialog.show(getContext(), "数据请求中,稍候！");
        Map<String, Object> map = new HashMap<>();
        map.put("isbn", code);
        NRClient.getBookDetail(map, new ResultCallback<Result<BookDetail>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<BookDetail> result) {
                LoadDialog.dismiss(getContext());
                setBookDetail(result.getData());
                bookId = Utils.getValue(result.getData().getBookId());
                average = Utils.getValue(result.getData().getAverage());
                if (null == bookId || bookId == 0) {
                    if (result.getData() != null && result.getData().getBookImg() != null)
                        Utils.savePicture(getContext(), code + ".jpg", result.getData().getBookImg()
                                , new PicLoadCallBack() {
                                    @Override
                                    public void OnComplete(String filename) {
                                        updateFiles(new File(filename));
                                    }
                                });
                } else {
                    fileName = result.getData().getBookImg();
                    Log.i("fancy", "这本书已经有了");
                }
            }
        });
    }

    private void setBookDetail(BookDetail data) {
        if (data != null && data.getAuthor() != null) {
            String bookname = Utils.getValue(data.getTitle());
            if (bookname.equals(""))
                etBookName.setClickable(true);
            else {
                etBookName.setClickable(false);
                etBookName.setText(bookname);
            }
            String writer = Utils.getValue(data.getAuthor());
            if (writer.equals("")) {
                etWriter.setClickable(true);
            } else {
                etWriter.setText(writer);
                etWriter.setClickable(false);
            }
            String pages = Utils.getValue(data.getPages()) + "";
            if (pages.equals(""))
                etPages.setClickable(true);
            else {
                etPages.setClickable(false);
                etPages.setText(pages);
            }
            String price = Utils.getValue(data.getPrice()) + "";
            if (price.equals(""))
                etPrice.setClickable(true);
            else {
                etPrice.setClickable(false);
                etPrice.setText(price);
            }
            String publisher = Utils.getValue(data.getPublisher());
            if (publisher.equals(""))
                etPublisher.setClickable(true);
            else {
                etPublisher.setClickable(false);
                etPublisher.setText(publisher);
            }
            String desc = Utils.getValue(data.getSummary());
            if (desc.equals(""))
                etDesc.setClickable(true);
            else {
                etDesc.setClickable(false);
                etDesc.setText(desc);
            }
            if (null != data.getBookImg() && !data.getBookImg().equals("")) {
                Utils.setImgF(getContext(), data.getBookImg(), ivCover);
                ivCover.setClickable(false);
            } else ivCover.setClickable(true);
        }
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
//                    mAdapter.add(picture.getAbsolutePath());
                    Utils.setImgF(getContext(), picture, ivCover);
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
//                            mAdapter.add(newFile.getAbsolutePath());
                            Utils.setImgF(getContext(), newFile, ivCover);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Utils.setImgF(getContext(), file, ivCover);
//                        mAdapter.add(file.getAbsolutePath());
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
//                        sb.append(result.getData().getData().get(0) + ",");
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

    public static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }


    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        Utils.hideSoftInput(this);
//        MobclickAgent.onPause(this);
    }

    private void donateBook(Map<String, Object> map) {
        LoadDialog.show(getContext());
        NRClient.donateBook(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                showPopView(btnDonate);
            }
        });
    }

    private static final int GET_IMAGE_VIA_SDCARD = 1000;
    private static final int GET_IMAGE_VIA_CAMERA = 1001;

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

    private Handler mHandler = new Handler(Looper.getMainLooper());
    String scaleType;

    private String getPics() {
        String pics = sb.toString();
        if (pics.endsWith(",")) {
            pics = pics.substring(0, pics.length() - 1);
        }
        return pics;
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

}
