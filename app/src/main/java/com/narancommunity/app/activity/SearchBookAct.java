package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookCommentAdapter;
import com.narancommunity.app.adapter.FindLatestAdapter;
import com.narancommunity.app.adapter.OnItemClickListener;
import com.narancommunity.app.common.DBHelper;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookComment;
import com.narancommunity.app.entity.BookEntity;
import com.narancommunity.app.entity.SearchHistoryEntity;
import com.narancommunity.app.net.AppConstants;
import com.snappydb.DB;
import com.snappydb.SnappydbException;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

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
    @BindView(R.id.iv_hot_one)
    ImageView ivHotOne;
    @BindView(R.id.tv_hot_one)
    TextView tvHotOne;
    @BindView(R.id.iv_hot_two)
    ImageView ivHotTwo;
    @BindView(R.id.tv_hot_two)
    TextView tvHotTwo;
    @BindView(R.id.iv_hot_three)
    ImageView ivHotThree;
    @BindView(R.id.tv_hot_three)
    TextView tvHotThree;
    @BindView(R.id.ln_hot_switch)
    LinearLayout lnHotSwitch;
    @BindView(R.id.recyclerView_result)
    RecyclerView recyclerViewResult;
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
    List<String> listHistory = new ArrayList<>();

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
        getRecData();
    }


    List<BookEntity> listRec = new ArrayList<>();

    private void getRecData() {
        String[] arr = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521822134&di=8f8ad66862e43356d2f611c75e837232&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F001oghI3gy6RP8blcDK18%26690"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521840435&di=ccc40b13bce701c1243aa33e7ac8ccbf&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fbaike%2Fw%253D268%253Bg%253D0%2Fsign%3Df7c629d8b9014a90813e41bb914c5e2f%2Fe61190ef76c6a7ef27ca3e4cfdfaaf51f2de66e8.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521857113&di=7c3fcec13a02545b16adf8064a80ab3f&imgtype=0&src=http%3A%2F%2Fimage.xinmin.cn%2F2016%2F10%2F31%2F1477875471441.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521876495&di=5e2f1c14077b7fd644a085d531bafe47&imgtype=0&src=http%3A%2F%2Fimg13.360buyimg.com%2FpopWaterMark%2Fjfs%2Ft481%2F15%2F977914362%2F15133%2F32d03cb1%2F54a1175aN3785b77e.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523539651530&di=076380697cd9b09ecc6bd9f7b8cca7e7&imgtype=0&src=http%3A%2F%2Fp4.qhimg.com%2Ft0126b72b37e2c48a1c.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523522043547&di=2b57cbb4ffac6d93fd4044374ff73297&imgtype=0&src=http%3A%2F%2Fimg34.ddimg.cn%2F24%2F8%2F486114-1_o.jpg"};

        Random random = new Random();
        BookEntity entity;
        for (int i = 0; i < 6; i++) {
            entity = new BookEntity();
            entity.setName("三国志");
            int position = random.nextInt(6);
            Log.i("fancy", "random = " + position);
            entity.setUrl(arr[position] + "");
            listRec.add(entity);
        }
        tvHotOne.setText(Utils.getValue(listRec.get(0).getName()) + "");
        tvHotTwo.setText(Utils.getValue(listRec.get(0).getName()) + "");
        tvHotThree.setText(Utils.getValue(listRec.get(0).getName()) + "");
        Utils.setImgF(getContext(), listRec.get(0).getUrl(), ivHotOne);
        Utils.setImgF(getContext(), listRec.get(1).getUrl(), ivHotTwo);
        Utils.setImgF(getContext(), listRec.get(2).getUrl(), ivHotThree);
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
        getSearchHistoryList();
    }

    private void addToHistoryList(String s) {
        SearchHistoryEntity historyEntity;
        try {
            DB snappyDb =
                    DBHelper.getDB(this);
            historyEntity = snappyDb.get(AppConstants.USER_SEARCH_HISTORY_LIST, SearchHistoryEntity.class);
            if (historyEntity.getData() != null) {
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
        Toaster.toast(getContext(), "开始搜索");
        if (key.equals("红楼梦")) {
            //有结果的话
            rlHistoryView.setVisibility(View.GONE);
            lnRec.setVisibility(View.GONE);
            lnHotSwitch.setVisibility(View.GONE);
            recyclerViewResult.setVisibility(View.VISIBLE);
            lnNoResult.setVisibility(View.GONE);
            lnNeedHelp.setVisibility(View.GONE);

            setDataToView();
        } else {
            //没结果的话
            rlHistoryView.setVisibility(View.GONE);
            lnRec.setVisibility(View.GONE);
            lnHotSwitch.setVisibility(View.GONE);
            recyclerViewResult.setVisibility(View.GONE);
            lnNoResult.setVisibility(View.VISIBLE);
            lnNeedHelp.setVisibility(View.VISIBLE);
        }
    }

    FindLatestAdapter adapter;
    List<BookEntity> listResult = new ArrayList<>();

    private void setDataToView() {
        BookEntity book;
        listResult.clear();
        for (int i = 0; i < 10; i++) {
            book = new BookEntity();
            book.setDesc("《红楼梦》，中国古典四大名著之首，清代作家曹雪芹创作的章回体长篇小说");
            book.setDistance("3km");
            book.setMwriter("曹雪芹");
            book.setName("红楼梦");
            book.setUrl("https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=cd0275550b24ab18f41be96554938da8/0b46f21fbe096b636940ce230e338744ebf8ac6c.jpg");
            listResult.add(book);
        }

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

            adapter.setDataList(listResult);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_cancel, R.id.iv_clear, R.id.ln_need_help, R.id.iv_hot_one, R.id.iv_hot_two, R.id.iv_hot_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_clear:
                clearHistory();
                break;
            case R.id.ln_need_help:
                startActivity(new Intent(getContext(), NeedBookAct.class).putExtra("tag", 0));
                break;
            case R.id.iv_hot_one:
                startActivity(new Intent(getContext(), BookDetailAct.class));
                break;
            case R.id.iv_hot_two:
                startActivity(new Intent(getContext(), BookDetailAct.class));
                break;
            case R.id.iv_hot_three:
                startActivity(new Intent(getContext(), BookDetailAct.class));
                break;
        }
    }
}
