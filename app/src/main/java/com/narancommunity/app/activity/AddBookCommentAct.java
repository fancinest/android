package com.narancommunity.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookCommentData;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/12 17:20
 * Email：120760202@qq.com
 * FileName : 添加书评，添加评论，回复评论的活动
 */

public class AddBookCommentAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.et_content)
    EditText etContent;

    int bookId;
    int tag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_book_comment);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("填写书评");

        tag = getIntent().getIntExtra("tag", 0);
        bookId = getIntent().getIntExtra("bookId", 0);
        if (tag == 0)
            etContent.setHint("请输入回复");
        else if (tag == 1) {
            etContent.setHint("请输入回复");
        } else {
            etContent.setHint("说点什么吧");
        }

    }

    @OnClick(R.id.btn_release)
    public void onViewClicked() {
        if (etContent.getText().toString().equals("")) {
            Toaster.toast(getContext(), "请输入书评内容");
            return;
        } else {
            addComment();
        }
        finish();
    }

    private void addComment() {
        LoadDialog.show(getContext(), "正在发布书评");
        Map<String, Object> map = new HashMap<>();
        map.put("content", etContent.getText().toString());
        map.put("orderId", bookId);
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.addBookCommentComment(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "发布成功!");
                finish();
            }
        });
    }
}
