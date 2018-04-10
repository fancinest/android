package com.narancommunity.app.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.PicUploadAdapter;
import com.narancommunity.app.adapter.StationeryAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.ExpandableHeightGridView;
import com.narancommunity.app.common.ImageUtils;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.SDCardUtils;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AddressEntity;
import com.narancommunity.app.entity.Stationery;
import com.narancommunity.app.entity.StationeryEntity;
import com.narancommunity.app.entity.UpdateFilesEntity;
import com.narancommunity.app.net.AppConstants;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Writer：fancy on 2017/12/22 11:14
 * Email：120760202@qq.com
 * FileName : 捐赠和许愿同一个界面
 */

public class MakeWishAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.gridview)
    ExpandableHeightGridView gridView;
    @BindView(R.id.ln_pic)
    LinearLayout lnPic;
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.ln_name)
    LinearLayout lnName;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ln_address)
    LinearLayout lnAddress;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.ctv_yes)
    CheckedTextView ctvYes;
    @BindView(R.id.ctv_no)
    CheckedTextView ctvNo;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.ctv_yes_anonymous)
    CheckedTextView ctvYesAnonymous;
    @BindView(R.id.ctv_no_anonymous)
    CheckedTextView ctvNoAnonymous;
    @BindView(R.id.tagFlow_new)
    TagFlowLayout tagFlowNew;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.ctv_no_clear)
    CheckedTextView ctvNoClear;
    @BindView(R.id.ctv_forget)
    CheckedTextView ctvForget;
    @BindView(R.id.ln_donate_include)
    LinearLayout lnDonateInclude;
    @BindView(R.id.ln_include_address)
    LinearLayout lnIncludeAddress;

    PicUploadAdapter mAdapter;
    boolean isWish;
    String stationeryId = "";
    String selectedType = "";
    String xjTag = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_make_wish);
        ButterKnife.bind(this);
        MApplication.putActivity("makeDonate", getContext());

        setBar(toolbar);
        isWish = getIntent().getBooleanExtra("isWish", true);
        selectedType = getIntent().getStringExtra("tag");
        setDifference();
        setView();
        setGrid();
    }

    private void setDifference() {
        if (!isWish) {
            toolbar.setTitle("发布捐赠");
            etContent.setHint("请开始你的表演，介绍一下你所捐赠的物品...");
            lnIncludeAddress.setVisibility(View.GONE);
            lnDonateInclude.setVisibility(View.VISIBLE);
            setNewOld();
            etPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ctvForget.setChecked(false);
                    ctvNoClear.setChecked(false);
                }
            });
            etPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    ctvForget.setChecked(false);
                    ctvNoClear.setChecked(false);
                }
            });
        } else {
            lnIncludeAddress.setVisibility(View.VISIBLE);
            lnDonateInclude.setVisibility(View.GONE);
            toolbar.setTitle(getString(R.string.release_wish));
        }
    }

    private void setNewOld() {
        final String xjArr[] = new String[]{"全新", "九成新", "七成新", "六成新以下"};
        final String xjData[] = new String[]{"ALL_NEW", "NINE_NEW", "SEVEN_NEW", "SIX_NEW"};
        List<String> list = new ArrayList<>();
        for (int i = 0; i < xjArr.length; i++) {
            list.add(xjArr[i]);
        }
        tagFlowNew.setAdapter(new TagAdapter(list) {

            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                LayoutInflater mInflater = LayoutInflater.from(getContext());
                CheckedTextView tv = (CheckedTextView) mInflater.inflate(R.layout.item_xj_item,
                        tagFlowNew, false);
                tv.setText(xjArr[position]);
                return tv;
            }
        });
        tagFlowNew.setMaxSelectCount(1);
        tagFlowNew.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                xjTag = xjData[position];
                Log.i("fancy", "xjTag = " + xjTag);
                return false;
            }
        });
    }

    private void setGrid() {
        gridView.setExpanded(true);
        mAdapter = new PicUploadAdapter(this, null);
        mAdapter.setMaxPicCount(9);
        gridView.setAdapter(mAdapter);
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

    StationeryAdapter stationeryAdapter;
    List<StationeryEntity> stationeryEntityList = new ArrayList<>();

    private void setView() {
        tvSelectAddress.setVisibility(View.VISIBLE);
        lnAddress.setVisibility(View.GONE);
        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvRelease.setClickable(false);
                tvCount.setText("" + (30 - s.length()));
                if (!etTitle.getText().toString().equals("")) {
                    if (!etContent.getText().toString().equals("")) {
                        tvRelease.setClickable(true);
                        tvRelease.setAlpha(1);
                    } else
                        tvRelease.setAlpha((float) 0.4);
                } else
                    tvRelease.setAlpha((float) 0.4);
            }
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvRelease.setClickable(false);
                if (!etTitle.getText().toString().equals("")) {
                    if (!etContent.getText().toString().equals("")) {
                        tvRelease.setAlpha(1);
                        tvRelease.setClickable(true);
                    } else
                        tvRelease.setAlpha((float) 0.4);
                } else
                    tvRelease.setAlpha((float) 0.4);
            }
        });

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayout);

        stationeryAdapter = new StationeryAdapter(getContext(), stationeryEntityList);
        stationeryAdapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                stationeryId = stationeryEntityList.get(position).getStationeryImg();
                String[] arr = stationeryId.split("/", -1);
                stationeryId = arr[arr.length - 1];
                Log.i("fancy", "信纸：" + stationeryId);
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        stationeryAdapter.setLimited(true);
        recyclerView.setAdapter(stationeryAdapter);
        stationeryAdapter.notifyDataSetChanged();
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
    protected void onPause() {
        super.onPause();
        Utils.hideSoftInput(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AndPermission.with(this)
                .requestCode(300)
                .permission(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .rationale(rationaleListener).callback(this)
                .start();
        getStationery();
    }

    private void getStationery() {
        LoadDialog.show(getContext());
        NRClient.getStationeries(new ResultCallback<Result<Stationery>>() {
            @Override
            public void onSuccess(Result<Stationery> result) {
                LoadDialog.dismiss(getContext());
                setStationery(result.getData());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    private void setStationery(Stationery data) {
        List<StationeryEntity> list = data.getStationerys();
        stationeryEntityList.clear();
        stationeryAdapter.clear();
        stationeryEntityList.addAll(list);
        stationeryAdapter.setList(stationeryEntityList);
        stationeryAdapter.notifyDataSetChanged();
    }

    RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            AlertDialog.newBuilder(MakeWishAct.this).setTitle("温馨提示")
                    .setMessage("你已拒绝过定位权限,是否授权")
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

    // 成功回调的方法，用注解即可，这里的300就是请求时的requestCode。
    @PermissionYes(300)
    private void getPermissionYes(List<String> grantedPermissions) {
        // TODO 申请权限成功。
    }

    @PermissionNo(300)
    private void getPermissionNo(List<String> deniedPermissions) {
        // TODO 申请权限失败。
    }

    @OnClick({R.id.ln_address, R.id.ctv_yes, R.id.ctv_no, R.id.ctv_yes_anonymous, R.id.ctv_no_anonymous
            , R.id.iv_refresh, R.id.tv_release, R.id.tv_select_address, R.id.ctv_no_clear, R.id.ctv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ctv_no_clear:
                ctvNoClear.setChecked(true);
                ctvForget.setChecked(false);
                etPrice.clearFocus();
                //TODO
                break;
            case R.id.ctv_forget:
                ctvForget.setChecked(true);
                ctvNoClear.setChecked(false);
                etPrice.clearFocus();
                break;
            case R.id.ln_address:
            case R.id.tv_select_address:
                Intent its = new Intent(getContext(), AddressAct.class);
                its.putExtra("isForResult", true);
                startActivityForResult(its, 2000);
                break;
            case R.id.ctv_yes:
                ctvYes.setChecked(true);
                ctvNo.setChecked(false);
                break;
            case R.id.ctv_no:
                ctvYes.setChecked(false);
                ctvNo.setChecked(true);
                break;
//            case R.id.ctv_yes_anonymous:
//                ctvYesAnonymous.setChecked(true);
//                ctvNoAnonymous.setChecked(false);
//                break;
//            case R.id.ctv_no_anonymous:
//                ctvNoAnonymous.setChecked(true);
//                ctvYesAnonymous.setChecked(false);
//                break;
            case R.id.iv_refresh:
                Toaster.toast(getContext(), "刷新中...");
                break;
            case R.id.tv_release:
                if (isWish) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("accessToken", MApplication.getAccessToken(getContext()));
                    map.put("commodityType", selectedType);
                    if (!etTitle.getText().toString().equals(""))
                        map.put("orderTitle", etTitle.getText().toString());
                    else {
                        etTitle.requestFocus();
                        Toaster.toast(getContext(), "请输入标题!");
                        return;
                    }
                    if (!etContent.getText().toString().equals(""))
                        map.put("orderContent", etContent.getText().toString());
                    else {
                        etContent.requestFocus();
                        Toaster.toast(getContext(), "请填写你的故事");
                        return;
                    }
                    String orderImgs = getImages();
                    if (!orderImgs.equals(""))
                        map.put("orderImgs", orderImgs);
                    else {
                        showPopView(view);
                        return;
                    }
                    String backdropImg = getStationeryImg();
                    if (!backdropImg.equals(""))
                        map.put("backdropImg", getStationeryImg());
                    else {
                        Toaster.toast(getContext(), "请选择一张信纸!");
                        recyclerView.requestFocus();
                        return;
                    }
                    if (mAddress == null) {
                        Toaster.toast(getContext(), "请输入收货地址!");
                        return;
                    }
                    map.put("willing", ctvYes.isChecked() ? true : false);
//                    map.put("anonymous", ctvYesAnonymous.isChecked() ? true : false);
                    map.put("mailAddress", mAddress.getProvince() + mAddress.getCity()
                            + mAddress.getCounty() + mAddress.getMailAddress());
                    map.put("mailName", mAddress.getMailName());
                    map.put("mailPhone", mAddress.getMailPhone());
                    map.put("province", mAddress.getProvince());
                    map.put("city", mAddress.getCity());
                    map.put("county", mAddress.getCounty());
                    LoadDialog.show(this, "正在发布心愿...");
                    NRClient.makeWish(map, new ResultCallback<Result<Void>>() {
                        @Override
                        public void onSuccess(Result<Void> result) {
                            LoadDialog.dismiss(getContext());
                            Toaster.toastLong(getContext(), "发布成功，您可至心愿列表查看您的心愿");
                            MApplication.finishAllActivity();
                            finish();
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            LoadDialog.dismiss(getContext());
                            Utils.showErrorToast(getContext(), throwable);
                        }
                    });
                } else {
                    if (etTitle.getText().toString().equals("")) {
                        etTitle.requestFocus();
                        Toaster.toast(getContext(), "请输入标题!");
                        return;
                    }
                    if (etContent.getText().toString().equals("")) {
                        etContent.requestFocus();
                        Toaster.toast(getContext(), "请填写你的故事");
                        return;
                    }
                    String orderImgs = getImages();
                    if (orderImgs.equals("")) {
                        Toaster.toast(getContext(), "请至少上传一张图片");
                        return;
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("accessToken", MApplication.getAccessToken(getContext()));
                    map.put("commodityType", selectedType);
                    map.put("orderTitle", etTitle.getText().toString());
                    map.put("orderContent", etContent.getText().toString());
                    map.put("orderImgs", orderImgs);
                    map.put("agingDegree", xjTag);
                    map.put("estimateWeight", etWeight.getText().toString() + "");
                    map.put("orderPrice", getPrice());
                    startActivity(new Intent(getContext(), AskPaperAct.class)
                            .putExtra("isWish", isWish)
                            .putExtra("data", (Serializable) map));
                }
                break;
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
            TextView tv_dial = (TextView) v.findViewById(R.id.dial);
            tv_dial.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv_dial.setText("选个图片增加一下颜值吧");
            tv_dial.setTextColor(getResources().getColor(R.color.black));
            Button cancel = v.findViewById(R.id.cancel);
            Button ok = v.findViewById(R.id.ok);
            cancel.setTextColor(getResources().getColor(R.color.login_gray));
            ok.setTextColor(getResources().getColor(R.color.login_gray));
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                    showPhotoDialog();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    private String getPrice() {
        if (ctvNoClear.isChecked()) {
            return "UNCLEAR";
        } else if (ctvForget.isChecked()) {
            return "FORGET";
        } else {
            return etPrice.getText().toString();
        }
    }

    private String getStationeryImg() {
        for (int i = 0; i < stationeryEntityList.size(); i++) {
            if (stationeryEntityList.get(i).isChecked())
                return stationeryEntityList.get(i).getStationeryImg();
        }
        return "";
    }

    private String getImages() {
        if (sb != null && !sb.toString().equals("")) {
            return sb.substring(0, sb.toString().length() - 1);
        } else
            return "";
    }

    private static final int GET_IMAGE_VIA_SDCARD = 1000;
    private static final int GET_IMAGE_VIA_CAMERA = 1001;

    AddressEntity mAddress = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && data != null) {
            AddressEntity address = (AddressEntity) data.getSerializableExtra("address");
            mAddress = address;
            setAddress(address);
        } else if (resultCode == RESULT_OK) {
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
                    mAdapter.add(picture.getAbsolutePath());
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
                if (result.getData() != null && result.getData().getData().size() > 0) {
                    Log.i("fancy", "fdsafasfadsfs----------");
                    if (result.getData().getData().size() > 0) {
                        sb.append(result.getData().getData().get(0) + ",");
//                        deleteTempFile();
                    }
                } else {
                    Toaster.toast(getContext(), "数据为空！");
                }
            }
        });
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
