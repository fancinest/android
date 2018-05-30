package com.narancommunity.app.activity.waste;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.QuestionAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.SyLinearLayoutManager;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AskPapers;
import com.narancommunity.app.entity.MOptions;
import com.narancommunity.app.entity.Questionnaires;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/9 22:06
 * Email：120760202@qq.com
 * FileName :问卷调查界面
 */

public class AskPaperAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
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
    Map<String, Object> map = new HashMap<>();
    List<Questionnaires> listQuestion = new ArrayList<>();
    QuestionAdapter questionAdapter;
    @BindView(R.id.ln_check_view)
    LinearLayout lnCheckView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ask_paper);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("发布捐赠");
        map = (Map<String, Object>) getIntent().getSerializableExtra("data");
        lnCheckView.setVisibility(View.GONE);
        setRecyclerView();
        getAskPaper();
    }

    private void getAskPaper() {
        LoadDialog.show(getContext(), "正在加载问答，请稍后！");
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("questionnaireType", "");
        NRClient.getAskPaper(map, new ResultCallback<Result<AskPapers>>() {
            @Override
            public void onSuccess(Result<AskPapers> result) {
                LoadDialog.dismiss(getContext());
                listQuestion.addAll(result.getData().getQuestionnaires());
                questionAdapter.setList(result.getData().getQuestionnaires());
                questionAdapter.notifyDataSetChanged();
                lnCheckView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    private void setRecyclerView() {
        SyLinearLayoutManager linearLayout = new SyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);

        questionAdapter = new QuestionAdapter(getContext(), listQuestion);
        questionAdapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
            }

            @Override
            public void OnDelClick(int position) {
            }
        });
        recyclerView.setAdapter(questionAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @OnClick({R.id.ctv_yes, R.id.ctv_no, R.id.ctv_yes_anonymous, R.id.ctv_no_anonymous, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.btn_complete:
                String ids = getAnswerList();
                ids = ids.substring(0, ids.length() - 1);
                if (ids.equals("")) {
                    Toaster.toast(getContext(), "请先回答问题哦！");
                    return;
                } else if (ids.contains(",") && ids.split(",", -1).length < 5) {
                    Toaster.toast(getContext(), "请完整回答问答哦！");
                    return;
                }
                map.put("willing", ctvYes.isChecked() ? true : false);
//                map.put("anonymous", ctvYesAnonymous.isChecked() ? true : false);
                map.put("questionnaireOptionIds", ids);
                map.put("province", "浙江省");
                map.put("city", "杭州市");
                map.put("county", "余杭区");
                NRClient.donate(map, new ResultCallback<Result<Void>>() {
                    @Override
                    public void onSuccess(Result<Void> result) {
                        LoadDialog.dismiss(getContext());
                        Toaster.toastLong(getContext(), "发布成功，您可至捐赠列表查看您的捐赠");
                        MApplication.finishAllActivity();
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadDialog.dismiss(getContext());
                        Utils.showErrorToast(getContext(), throwable);
                    }
                });
                break;
        }
    }

    /**
     * string	问卷结果ID以,号隔开
     *
     * @return
     */
    private String getAnswerList() {
        StringBuilder sb = null;
        sb = new StringBuilder();
        for (int i = 0; i < listQuestion.size(); i++) {
            Questionnaires questionnaires = listQuestion.get(i);
            List<MOptions> options = questionnaires.getOptions();
            for (int m = 0; m < options.size(); m++) {
                boolean isCheck = options.get(m).isChecked();
                if (isCheck) {
                    sb.append(options.get(m).getOptionId() + ",");
                    break;
                }
            }
        }
        Log.i("fancy", "问卷结果ID：" + sb.toString());
        return sb.toString();
    }
}
