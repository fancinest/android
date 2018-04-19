package com.narancommunity.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookCommentCommentAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/12 17:18
 * Email：120760202@qq.com
 * FileName :
 */

public class BookCommentDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_icon)
    SelectableRoundedImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ln_date)
    LinearLayout lnDate;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.ln_comment)
    LinearLayout lnComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.ln_like)
    LinearLayout lnLike;
    @BindView(R.id.tv_comment_Count)
    TextView tvCommentCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BookCommentCommentAdapter adapter;
    List<BookComment> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_comment_detail);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("书评");

        setData();
        setView();
    }

    private void setData() {
        BookComment bookComment;
        for (int i = 0; i < 5; i++) {
            bookComment = new BookComment();
//            bookComment.setCreateTime("2018-4-10 08:10:30");
//            bookComment.setUrl("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=779431233,515089216&fm=27&gp=0.jpg");
//            bookComment.setLikes((new Random().nextInt(20)) + "");
//            bookComment.setName("赵小刀");
            bookComment.setContent(" 那一日正当三月中浣，早饭后，宝玉携了一套《会真记》，走到沁芳闸桥边桃花底下一块石上坐着，展开《会真记》，从头细玩。正看到“落红成阵”，只见一阵风过，把树头上桃花吹下一大半来，落的满身满书满地皆是。 ");
            list.add(bookComment);
        }
    }

    private void setView() {
        tvContent.setText("宝玉要抖将下来，恐怕脚步践踏了，只得兜了那花瓣，来至池边，抖在池内。 那花瓣浮在水面，飘飘荡荡，竟流出沁芳闸去了。 回来只见地下还有许多，宝玉正踟蹰间，只听背后有人说道：“你在这里做什么？”宝玉一回头， 却是林黛玉来了，肩上担着花锄，锄上挂着花囊，手内拿着花帚。宝玉笑道：“好，好，来把这个花扫起来，撂在那水里。我才撂了好些在那里呢。” 林黛玉道：“撂在水里不好。你看这里的水干净，只一流出去，有人家的地方脏的臭的混倒，仍旧把花遭塌了。那畸角上我有一个花冢，如今把他扫了，装在这绢袋里，拿土埋上，日久不过随土化了，岂不干净。” 黛玉之见解可不一般，为桃花立墓牌，可见爱怜惜花之非常，花之美应尽数释放，却不该任由之随波逐流，黛玉对美的独特见解，读到这儿，也让我为之记忆深刻，感慨良久，韵味绕梁，久久不散。 《红楼梦》这部作品被誉为中国历史上思想的最高峰。我想不仅是因为曹雪芹的文采与悲惨遭遇，更多的是书中人物活灵活现的思想高度。这本书，我没有一一尽数品尝，而是选择了一些较为经典的桥段来欣赏。我觉得这就是我的遗憾。光看那些经典桥段，就有一丝情感在我的心头悄然酝酿，我想，我是得好好的去看看了。");
        tvName.setText("丽颖小朋友");
        Utils.setImgF(getContext(), "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3984423969,3360625402&fm=27&gp=0.jpg", ivIcon);

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BookCommentCommentAdapter(getContext(), list);
        recyclerView.setLayoutManager(lm_latest);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {

            }

            @Override
            public void OnDelClick(int position) {

            }
        });
    }

    @OnClick({R.id.iv_icon, R.id.ln_comment, R.id.ln_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                break;
            case R.id.ln_comment:
                break;
            case R.id.ln_like:
                break;
        }
    }
}
