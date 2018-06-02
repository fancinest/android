package com.narancommunity.app.activity.index.book;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.adapter.BookListAdapter;
import com.narancommunity.app.adapter.FindLatestAdapter;
import com.narancommunity.app.adapter.OnItemClickListener;
import com.narancommunity.app.common.DBHelper;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.RecData;
import com.narancommunity.app.entity.RecEntity;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/13 10:05
 * Email：120760202@qq.com
 * FileName : 书籍搜索界面
 */

public class SearchBookAct extends BaseActivity {
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
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ln_hot_switch)
    LinearLayout lnHotSwitch;
    @BindView(R.id.recyclerView_result)
    XRecyclerView recyclerViewResult;
    @BindView(R.id.recyclerView_rec)
    RecyclerView recyclerViewRec;
    @BindView(R.id.ln_no_result)
    LinearLayout lnNoResult;
    @BindView(R.id.iv_help)
    ImageView ivHelp;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.ln_need_help)
    RelativeLayout lnNeedHelp;
    @BindView(R.id.ln_rec)
    LinearLayout lnRec;
    @BindView(R.id.iv_clear_input)
    ImageView ivClearInput;
    List<String> listHistory = new ArrayList<>();
    BookListAdapter adapterRec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_book);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) lnTop.getLayoutParams();
            linearParams.height = Utils.dip2px(getContext(), 48);
            lnTop.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
            lnTop.setPadding(0, 0, 0, 0);
        }

        getSearchHistoryList();
        setView();
        getRecData(false);
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


    List<RecEntity> listRec = new ArrayList<>();

    private void getRecData(boolean isShowProgress) {
        if (isShowProgress)
            LoadDialog.show(getContext(), "请稍后！");
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 3);
        NRClient.getHouseBookRec(map, new ResultCallback<Result<RecData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<RecData> result) {
                LoadDialog.dismiss(getContext());
                setBook(result.getData());
            }
        });
    }

    private void setBook(RecData data) {
        listRec.clear();
        if (data != null && data.getOrders().size() > 0) {
            listRec.addAll(data.getOrders());
            adapterRec.notifyDataSetChanged();
            lnRec.setVisibility(View.VISIBLE);
            recyclerViewRec.setVisibility(View.VISIBLE);
        }
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

                    String key = etSearch.getText().toString().trim();
                    if (key.equals("")) {
                        Toaster.toast(getContext(), "请输入搜索关键字");
                        return false;
                    }
                    addToHistoryList(key);
                    searchBook(key);
                    return false;
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    ivClearInput.setVisibility(View.VISIBLE);
                else ivClearInput.setVisibility(View.GONE);
            }
        });
        tvTitle.setText("借阅热榜单");
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        if (listHistory.size() > 0) {
            rlHistoryView.setVisibility(View.VISIBLE);
            lnHotSwitch.setVisibility(View.GONE);
            recyclerViewResult.setVisibility(View.GONE);
            lnNoResult.setVisibility(View.GONE);
            lnNeedHelp.setVisibility(View.GONE);
        } else {
            rlHistoryView.setVisibility(View.GONE);
            lnHotSwitch.setVisibility(View.GONE);
            recyclerViewResult.setVisibility(View.GONE);
            lnNoResult.setVisibility(View.GONE);
            lnNeedHelp.setVisibility(View.GONE);
        }

        GridLayoutManager linearLayoutRec = new GridLayoutManager(getContext(), 3);
        linearLayoutRec.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewRec.setLayoutManager(linearLayoutRec);

        adapterRec = new BookListAdapter(getContext(), listRec);
        adapterRec.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                startActivity(new Intent(getContext(), BookDetailAct.class)
                        .putExtra("bookId", listRec.get(position).getOrderId()));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerViewRec.setAdapter(adapterRec);
        recyclerViewRec.setNestedScrollingEnabled(false);
    }

    private void clearHistory() {
        SearchHistoryEntity historyEntity = new SearchHistoryEntity();
        try {
            DB snappyDb =
                    DBHelper.getDB(this);
            snappyDb.put(AppConstants.USER_SEARCH_HISTORY_LIST, historyEntity);
            snappyDb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        rlHistoryView.setVisibility(View.GONE);
//        getSearchHistoryList();
    }

    private void addToHistoryList(String s) {
        SearchHistoryEntity historyEntity;
        try {
            DB snappyDb =
                    DBHelper.getDB(this);
            historyEntity = snappyDb.get(AppConstants.USER_SEARCH_HISTORY_LIST, SearchHistoryEntity.class);
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
            snappyDb.put(AppConstants.USER_SEARCH_HISTORY_LIST, historyEntity);
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
            boolean isExists = snappyDb.exists(AppConstants.USER_SEARCH_HISTORY_LIST);
            if (isExists)
                historyEntity = snappyDb.get(AppConstants.USER_SEARCH_HISTORY_LIST, SearchHistoryEntity.class);
            else {
                snappyDb.put(AppConstants.USER_SEARCH_HISTORY_LIST, historyEntity);
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
        List<String> reverseList = listHistory;
        Collections.reverse(reverseList);
        tagHistory.setAdapter(new TagAdapter<String>(Utils.listToArray(reverseList)) {
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
                String key = listHistory.get(position);
                etSearch.setText(key);
                Utils.hideSoftInput(getContext());
                searchBook(key);
                return true;
            }
        });
    }

    int pageSize = 20;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;

    private void searchBook(String key) {
        Map<String, Object> map = new HashMap<>();
        map.put("bookClassify", "");
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        map.put("filter", key);
        NRClient.getBookBySortList(map, new ResultCallback<Result<RecData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
                recyclerViewResult.refreshComplete();
                recyclerViewResult.loadMoreComplete();
                setNoBook();
            }

            @Override
            public void onSuccess(Result<RecData> result) {
                LoadDialog.dismiss(getContext());
                setBookList(result.getData());
                recyclerViewResult.refreshComplete();
                recyclerViewResult.loadMoreComplete();
            }
        });
    }

    private void setNoBook() {
        listResult.clear();
        rlHistoryView.setVisibility(View.GONE);
        lnRec.setVisibility(View.GONE);
        lnHotSwitch.setVisibility(View.GONE);
        recyclerViewResult.setVisibility(View.GONE);
        lnNoResult.setVisibility(View.VISIBLE);
        lnNeedHelp.setVisibility(View.VISIBLE);
    }

    private void setBookList(RecData data) {
        if (pageNum == 1)
            listResult.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getOrders() != null && data.getOrders().size() > 0) {
            rlHistoryView.setVisibility(View.GONE);
            lnRec.setVisibility(View.GONE);
            lnHotSwitch.setVisibility(View.GONE);
            recyclerViewResult.setVisibility(View.VISIBLE);
            lnNoResult.setVisibility(View.GONE);
            lnNeedHelp.setVisibility(View.GONE);
            listResult.addAll(data.getOrders());
            pageNum++;
            if (adapter == null) {
                final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
                lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
                adapter = new FindLatestAdapter(getContext());
                adapter.setListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });
                recyclerViewResult.setLayoutManager(lm_latest);
                recyclerViewResult.setAdapter(adapter);
                recyclerViewResult.setNestedScrollingEnabled(false);
                adapter.isSearch(true);
                recyclerViewResult.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        pageNum = 1;
                        searchBook(etSearch.getText().toString());
                    }

                    @Override
                    public void onLoadMore() {
                        if (pageNum <= TOTAL_PAGE)
                            searchBook(etSearch.getText().toString());
                        else {
//                            View noData = LayoutInflater.from(getContext()).inflate(R.layout.view_no_data,null);
//                            recyclerViewResult.setFootView(noData, new CustomFooterViewCallBack() {
//                                @Override
//                                public void onLoadingMore(View yourFooterView) {
//
//                                }
//
//                                @Override
//                                public void onLoadMoreComplete(View yourFooterView) {
//
//                                }
//
//                                @Override
//                                public void onSetNoMore(View yourFooterView, boolean noMore) {
//
//                                }
//                            });
                            recyclerViewResult.loadMoreComplete();
                            recyclerViewResult.refreshComplete();
                            Toaster.toast(getContext(), "已无更多数据");
                        }
                    }
                });

            }
            adapter.setDataList(listResult);
            adapter.notifyDataSetChanged();

        } else setNoBook();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // any time,when you finish your activity or fragment,call this below
        if (recyclerViewResult != null) {
            recyclerViewResult.destroy(); // this will totally release XR's memory
            recyclerViewResult = null;
        }
    }

    FindLatestAdapter adapter;
    List<RecEntity> listResult = new ArrayList<>();

    @OnClick({R.id.tv_cancel, R.id.iv_clear, R.id.ln_need_help, R.id.iv_clear_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_clear:
                showPopView(ivClear);
                break;
            case R.id.ln_need_help:
                startActivity(new Intent(getContext(), NeedBookAct.class).putExtra("tag", 0));
                break;
            case R.id.iv_clear_input:
                etSearch.setText("");
                ivClearInput.setVisibility(View.GONE);
                pageNum = 1;
                listResult.clear();
                setLastView();
                break;
        }
    }

    private void setLastView() {
        rlHistoryView.setVisibility(View.VISIBLE);
        lnRec.setVisibility(View.VISIBLE);
        lnNoResult.setVisibility(View.GONE);
        lnNeedHelp.setVisibility(View.GONE);
        recyclerViewResult.setVisibility(View.GONE);
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
}
