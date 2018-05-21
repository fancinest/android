package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.criteria.BaseCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.WeekDayCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.CurrentMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.NextMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.PreviousMonthCriteria;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/21 16:27
 * Email：120760202@qq.com
 * FileName :
 */
public class LookBookStateAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.calendar)
    CalendarView calendarView;

    int bookId;
    //以下为日历的控件
    private List<BaseCriteria> threeMonthsCriteriaList;
    private WeekDayCriteria fridayCriteria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_look_book_state);
        ButterKnife.bind(this);

        bookId = getIntent().getIntExtra("bookId", 0);
        toolbar.setTitle("查看借阅");
        setBar(toolbar);
        createCriterias();
    }

    private void createCriterias() {
        fridayCriteria = new WeekDayCriteria(Calendar.FRIDAY);

        threeMonthsCriteriaList = new ArrayList<>();
        threeMonthsCriteriaList.add(new CurrentMonthCriteria());
        threeMonthsCriteriaList.add(new NextMonthCriteria());
        threeMonthsCriteriaList.add(new PreviousMonthCriteria());

        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        calendarView.setSelectionType(SelectionType.RANGE);
        calendarView.setSelectedDayBackgroundColor(getResources().getColor(R.color.appBlue));
        calendarView.setSelectedDayBackgroundStartColor(getResources().getColor(R.color.appBlue));
        calendarView.setSelectedDayBackgroundEndColor(getResources().getColor(R.color.appBlue));

//        calendarView.setSelectionManager(new RangeSelectionManager(onDa));
        RangeSelectionManager manager = new RangeSelectionManager(new OnDaySelectedListener() {
            @Override
            public void onDaySelected() {

            }
        });
        Day day = new Day(new Date());
        manager.toggleDay(day);
        calendarView.setSelectionManager(manager);
    }
}
