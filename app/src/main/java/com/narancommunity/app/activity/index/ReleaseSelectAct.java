package com.narancommunity.app.activity.index;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/8 14:18
 * Email：120760202@qq.com
 * FileName :发布前物品选择
 */

public class ReleaseSelectAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;

    List<Pair<String, Integer>> list = new ArrayList<>();
    Integer arr[] = new Integer[]{R.drawable.selector_item_first, R.drawable.selector_item_second,
            R.drawable.selector_item_third, R.drawable.selector_item_four,
            R.drawable.selector_item_five, R.drawable.selector_item_six,
            R.drawable.selector_item_seven, R.drawable.selector_item_eight,
            R.drawable.selector_item_nine, R.drawable.selector_item_ten,
            R.drawable.selector_item_eleven, R.drawable.selector_item_twelve};
    String sArr[] = new String[]{"书籍", "家具", "文具", "玩具", "鞋包服饰", "母婴用品",
            "美妆护理", "钟表首饰", "工具器材", "宠物用品", "数码电器", "同城服务"};

    boolean isWish;
    String tag = "";
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_release_select);
        ButterKnife.bind(this);
        toolbar.setTitle("选择物品类型（选一）");
        setBar(toolbar);
        isWish = getIntent().getBooleanExtra("isWish", false);
        initData();
        setView();
        MApplication.putActivity("releaseSelect", getContext());
    }

    private void initData() {
        for (int i = 0; i < arr.length; i++) {
            list.add(new Pair<>(sArr[i], arr[i]));
        }
    }

    private void setView() {
        idFlowlayout.setAdapter(new TagAdapter(list) {

            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                LayoutInflater mInflater = LayoutInflater.from(getContext());
                RadioButton tv = (RadioButton) mInflater.inflate(R.layout.item_release_item,
                        idFlowlayout, false);
                tv.setText(list.get(position).first);
                Drawable top = getResources().getDrawable(list.get(position).second);
                tv.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                return tv;
            }
        });
        idFlowlayout.setMaxSelectCount(1);
        idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                tag = sArr[position];
                Log.i("fancy", "tag = " + tag);
                return false;
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_next, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_next) {
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        if (tag.equals(""))
            Toaster.toast(getContext(), "请选择一个条目");
        else
            startActivity(new Intent(this, MakeWishAct.class)
                    .putExtra("isWish", isWish).putExtra("tag", tag));
    }
}
