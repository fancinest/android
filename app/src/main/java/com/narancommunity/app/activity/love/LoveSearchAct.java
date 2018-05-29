package com.narancommunity.app.activity.love;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.LoveSearchAdapter;
import com.narancommunity.app.adapter.OnItemClickListener;
import com.narancommunity.app.common.DBHelper;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.CompanyData;
import com.narancommunity.app.entity.CompanyEntity;
import com.narancommunity.app.entity.SearchHistoryEntity;
import com.narancommunity.app.net.AppConstants;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.snappydb.DB;
import com.snappydb.SnappydbException;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/5/17 15:45
 * Email：120760202@qq.com
 * FileName :
 */

public class LoveSearchAct extends BaseActivity {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.ln_top)
    LinearLayout lnTop;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.flowlayout)
    TagFlowLayout tagHistory;
    @BindView(R.id.rl_history_view)
    RelativeLayout rlHistoryView;
    @BindView(R.id.recyclerView_result)
    RecyclerView recyclerViewResult;
    @BindView(R.id.ln_no_result)
    LinearLayout lnNoResult;


    List<String> listHistory = new ArrayList<>();

    int pageSize = 6;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_love_search);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) lnTop.getLayoutParams();
            linearParams.height = Utils.dip2px(getContext(), 48);
            lnTop.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
            lnTop.setPadding(0, 0, 0, 0);
        }

        getSearchHistoryList();
        setView();
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void setView() {
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getContext().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    String key = etSearch.getText().toString();
                    addToHistoryList(key);
                    goSearch(key);
                    return false;
                }
                return false;
            }
        });
        if (listHistory.size() > 0) {
            rlHistoryView.setVisibility(View.VISIBLE);
            recyclerViewResult.setVisibility(View.GONE);
            lnNoResult.setVisibility(View.GONE);
        } else {
            rlHistoryView.setVisibility(View.GONE);
            recyclerViewResult.setVisibility(View.GONE);
            lnNoResult.setVisibility(View.GONE);
        }
    }

    private void addToHistoryList(String s) {
        SearchHistoryEntity historyEntity;
        try {
            DB snappyDb =
                    DBHelper.getDB(this);
            historyEntity = snappyDb.get(AppConstants.USER_SEARCH_HISTORY_LIST_OF_LOVE, SearchHistoryEntity.class);
            if (historyEntity.getData() != null) {
                if (historyEntity.getData().size() == 10) {
                    historyEntity.getData().add(s);
                    historyEntity.getData().remove(0);
                } else
                    historyEntity.getData().add(s);
            } else {
                List<String> list = new ArrayList<>();
                list.add(s);
                historyEntity.setData(list);
            }
            snappyDb.put(AppConstants.USER_SEARCH_HISTORY_LIST_OF_LOVE, historyEntity);
            snappyDb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        getSearchHistoryList();
    }

    private void clearHistory() {
        SearchHistoryEntity historyEntity = new SearchHistoryEntity();
        try {
            DB snappyDb =
                    DBHelper.getDB(this);
            snappyDb.put(AppConstants.USER_SEARCH_HISTORY_LIST_OF_LOVE, historyEntity);
            snappyDb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        getSearchHistoryList();
    }

    private void getSearchHistoryList() {
        SearchHistoryEntity historyEntity = new SearchHistoryEntity();
        try {
            DB snappyDb =
                    DBHelper.getDB(this);
            boolean isExists = snappyDb.exists(AppConstants.USER_SEARCH_HISTORY_LIST_OF_LOVE);
            if (isExists)
                historyEntity = snappyDb.get(AppConstants.USER_SEARCH_HISTORY_LIST_OF_LOVE, SearchHistoryEntity.class);
            else {
                snappyDb.put(AppConstants.USER_SEARCH_HISTORY_LIST_OF_LOVE, historyEntity);
            }
            snappyDb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        listHistory.clear();
        if (historyEntity.getData() != null) {
            listHistory.addAll(historyEntity.getData());
        }
        setHistory();
    }

    private void setHistory() {
        tagHistory.setAdapter(new TagAdapter<String>(Utils.listToArray(listHistory)) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tag_search_item,
                        tagHistory, false);
                tv.setText(s);
                return tv;
            }
        });
        tagHistory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //TODO
                String key = listHistory.get(position);
                goSearch(key);
                return true;
            }
        });
    }

    private void goSearch(String key) {
        LoadDialog.show(getContext(), "搜索中...");
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("filter", key);//加关键字
        NRClient.getOrgList(map, new ResultCallback<Result<CompanyData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<CompanyData> result) {
                LoadDialog.dismiss(getContext());
                setDataView(result.getData());
            }
        });
    }

    private void setDataView(CompanyData data) {
        if (data == null || data.getTotalCount() <= 0) {
            rlHistoryView.setVisibility(View.GONE);
            recyclerViewResult.setVisibility(View.GONE);
            lnNoResult.setVisibility(View.VISIBLE);
            return;
        } else {
            rlHistoryView.setVisibility(View.GONE);
            recyclerViewResult.setVisibility(View.VISIBLE);
            lnNoResult.setVisibility(View.GONE);

            if (pageNum == 1)
                listResult.clear();

            if (data.getCompanys() != null && data.getCompanys().size() > 0) {
                listResult.addAll(data.getCompanys());
                if (adapter == null) {
                    final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
                    lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
                    adapter = new LoveSearchAdapter(getContext());
                    adapter.setListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            startActivity(new Intent(getContext(), CompanyDetailAct.class)
                                    .putExtra("id", listResult.get(position).getCompanyId())
                                    .putExtra("accountId", listResult.get(position).getAccountId()));
                        }
                    });
                    recyclerViewResult.setLayoutManager(lm_latest);
                    recyclerViewResult.setAdapter(adapter);
                    recyclerViewResult.setNestedScrollingEnabled(false);
                    adapter.isSearch(true);
                }

                adapter.setDataList(listResult);
            }
            adapter.notifyDataSetChanged();
        }
    }

    LoveSearchAdapter adapter;
    List<CompanyEntity> listResult = new ArrayList<>();

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
            TextView cancel = v.findViewById(R.id.cancel);
            TextView ok = v.findViewById(R.id.ok);

            ok.setTextColor(getResources().getColor(R.color.appBlue));
            ok.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            cancel.setTextColor(getResources().getColor(R.color.appBlue));
            cancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);


            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv_content.setText("确认删除所有记录吗？");
            tv_content.setTextColor(getResources().getColor(R.color.black));
            v.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    clearHistory();
                    mPop.dismiss();
                }
            });
            v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @OnClick({R.id.tv_cancel, R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_clear:
                showPopView(ivClear);
                break;
        }
    }
}
