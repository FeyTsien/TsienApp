package com.hdnz.inanming.ui.popupwindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GABBookingInfoPopup extends MyBasePopupWindow {

    public static final String KEY_DATE_TIME = "date_time";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";

    private List<DateBean> mDateList;
    private List<DateBean.TimeBean> mTimeList;
    private String mStrDate;
    private String mStrTime;

    RecyclerViewAdapter adapterWeekDate;
    RecyclerViewAdapter adapterTime;

    public static GABBookingInfoPopup getInstance(Context context, TsienPopupWindowCallback tsienPopupWindowCallback) {
        mTsienPopupWindowCallback = tsienPopupWindowCallback;
        return new GABBookingInfoPopup(context);
    }

    private GABBookingInfoPopup(Context context) {
        super(context);
    }

    @Override
    protected int getLayouId() {
        return R.layout.popup_gab_booking_info;
    }

    @Override
    protected void initData() {
        mDateList = new ArrayList<>();
        mTimeList = new ArrayList<>();
        initDate();
    }

    @Override
    protected void initView(View rootView) {
        RecyclerView rvWeek = rootView.findViewById(R.id.rv_week);
        RecyclerView rvTime = rootView.findViewById(R.id.rv_time);

        adapterWeekDate = new RecyclerViewAdapter<DateBean>(mDateList, R.layout.item_gab_booking_info_weekdate) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                TextView tvWeek = holder.getView(R.id.tv_week);
                TextView tvDate = holder.getView(R.id.tv_date);
                LinearLayout llWeekDate = holder.getView(R.id.ll_week_date);
                tvWeek.setText(mDateList.get(position).getWeek());
                tvDate.setText(mDateList.get(position).getDate());
                if (mDateList.get(position).isSelect()) {
                    tvWeek.setTextColor(Color.WHITE);
                    tvDate.setTextColor(Color.WHITE);
                    llWeekDate.setBackgroundResource(R.color.colorTheme);
                } else {
                    tvWeek.setTextColor(getContext().getResources().getColor(R.color.colorTextTitle));
                    tvDate.setTextColor(getContext().getResources().getColor(R.color.colorTextTitle));
//                    llWeekDate.setBackground(null);
                    llWeekDate.setBackgroundResource(R.color.ghostwhite);
                }
            }
        };

        //选择日期
        adapterWeekDate.setOnItemClickListener(pos -> {
            mStrTime = "";
            mTimeList.clear();
            mTimeList.addAll(mDateList.get(pos).getTimeList());
            mStrDate = mDateList.get(pos).getDate();
            for (int i = 0; i < mDateList.size(); i++) {
                mDateList.get(i).setSelect(false);
                for (int j = 0; j < mTimeList.size(); j++) {
                    mDateList.get(i).getTimeList().get(j).setSelect(false);
                }
            }
            mDateList.get(pos).setSelect(true);
            adapterWeekDate.notifyDataSetChanged();
            adapterTime.notifyDataSetChanged();
        });

        adapterTime = new RecyclerViewAdapter<DateBean.TimeBean>(mTimeList, R.layout.item_gab_booking_info_time) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                TextView textView = holder.getView(R.id.tv_time);
                textView.setText(mTimeList.get(position).getTimeFrame());
                //字体颜色
                textView.setTextColor(getContext().getResources().getColor(R.color.colorTextTitle));
                //控件背景
                if (mTimeList.get(position).isOverdue()) {
                    //过期的时间段，不设置点击监听
                    textView.setBackgroundResource(R.drawable.item_btn_overdue_bg);
                    textView.setOnClickListener(null);
                } else {
                    textView.setBackgroundResource(R.drawable.item_btn_bg); //选择时间段
                    textView.setOnClickListener(v -> {
                        mStrTime = mTimeList.get(position).getTimeFrame();
                        //先将每个日期下面的时间段被选择状态都初始化
                        for (int i = 0; i < mDateList.size(); i++) {
                            for (int j = 0; j < mTimeList.size(); j++) {
                                mDateList.get(i).getTimeList().get(j).setSelect(false);
                            }
                        }
                        mTimeList.get(position).setSelect(true);
                        notifyDataSetChanged();
                    });
                }
                if (mTimeList.get(position).isSelect()) {
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundResource(R.drawable.item_btn_select_bg);
                }

            }
        };

        //日期用横向滑动的列表展示
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvWeek.setLayoutManager(linearLayoutManager);
        MultiItemDivider itemDivider = new MultiItemDivider(getContext(), MultiItemDivider.HORIZONTAL_LIST, R.drawable.divider_vertical, 20);
        itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
        // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
        rvWeek.addItemDecoration(itemDivider);
        rvWeek.setAdapter(adapterWeekDate);

        //时间段，用表格展示
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvTime.setLayoutManager(gridLayoutManager);
        rvTime.setNestedScrollingEnabled(false);
        rvTime.setAdapter(adapterTime);

        //点击确认
        rootView.findViewById(R.id.btn_confirm).setOnClickListener(v -> callGABBookingInfo());
    }

    private void callGABBookingInfo() {
        if (TextUtils.isEmpty(mStrDate)) {
            ToastUtils.showLong("请选择日期");
            return;
        }
        if (TextUtils.isEmpty(mStrTime)) {
            ToastUtils.showLong("请选择时间段");
            return;
        }
        String time = mStrDate + " " + mStrTime;
        Map map = new HashMap();
        map.put(KEY_DATE, mStrDate);
        map.put(KEY_TIME, mStrTime);
        map.put(KEY_DATE_TIME, time);
        sendCallback(map);
        this.dismiss();
    }


    /**
     * 初始化时间
     */
    @SuppressLint("SimpleDateFormat")
    private void initDate() {
        long thisDayMills = TimeUtils.getNowMills();//当天的时间戳
        long oneDayMills = 24 * 60 * 60 * 1000;//一天的时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = TimeUtils.millis2String(thisDayMills, simpleDateFormat);
        for (int i = 0; i < 5; i++) {
            DateBean dateBean = new DateBean();
            List<DateBean.TimeBean> timeList = new ArrayList<>();
            if (TextUtils.equals(TimeUtils.getChineseWeek(thisDayMills), "周六")) {
                thisDayMills = thisDayMills + oneDayMills + oneDayMills;
            } else if (TextUtils.equals(TimeUtils.getChineseWeek(thisDayMills), "周日")) {
                thisDayMills = thisDayMills + oneDayMills;
            }
            dateBean.setWeek(TimeUtils.getChineseWeek(thisDayMills));
            dateBean.setDate(TimeUtils.millis2String(thisDayMills, simpleDateFormat));
            for (int j = 0; j < 8; j++) {
                DateBean.TimeBean timeBean = new DateBean.TimeBean();
                timeBean.setSelect(false);
                timeBean.setOverdue(false);
                if (j == 0) {
                    timeBean.setTimeFrame("09:00-10:00");
                    if (TextUtils.equals(today, TimeUtils.millis2String(thisDayMills, simpleDateFormat)) && thisDayMills > TimeUtils.string2Millis(today + " 10:00:00")) {
                        timeBean.setOverdue(true);
                    }
                } else if (j == 1) {
                    timeBean.setTimeFrame("10:00-11:00");
                    if (TextUtils.equals(today, TimeUtils.millis2String(thisDayMills, simpleDateFormat)) && thisDayMills > TimeUtils.string2Millis(today + " 11:00:00")) {
                        timeBean.setOverdue(true);
                    }
                } else if (j == 2) {
                    timeBean.setTimeFrame("11:00-12:00");
                    if (TextUtils.equals(today, TimeUtils.millis2String(thisDayMills, simpleDateFormat)) && thisDayMills > TimeUtils.string2Millis(today + " 12:00:00")) {
                        timeBean.setOverdue(true);
                    }
                } else if (j == 3) {
                    timeBean.setTimeFrame("13:00-14:00");
                    if (TextUtils.equals(today, TimeUtils.millis2String(thisDayMills, simpleDateFormat)) && thisDayMills > TimeUtils.string2Millis(today + " 14:00:00")) {
                        timeBean.setOverdue(true);
                    }
                } else if (j == 4) {
                    timeBean.setTimeFrame("14:00-15:00");
                    if (TextUtils.equals(today, TimeUtils.millis2String(thisDayMills, simpleDateFormat)) && thisDayMills > TimeUtils.string2Millis(today + " 15:00:00")) {
                        timeBean.setOverdue(true);
                    }
                } else if (j == 5) {
                    timeBean.setTimeFrame("15:00-16:00");
                    if (TextUtils.equals(today, TimeUtils.millis2String(thisDayMills, simpleDateFormat)) && thisDayMills > TimeUtils.string2Millis(today + " 16:00:00")) {
                        timeBean.setOverdue(true);
                    }
                } else if (j == 6) {
                    timeBean.setTimeFrame("16:00-17:00");
                    if (TextUtils.equals(today, TimeUtils.millis2String(thisDayMills, simpleDateFormat)) && thisDayMills > TimeUtils.string2Millis(today + " 17:00:00")) {
                        timeBean.setOverdue(true);
                    }
                } else {
                    timeBean.setTimeFrame("17:00-18:00");
                    if (TextUtils.equals(today, TimeUtils.millis2String(thisDayMills, simpleDateFormat)) && thisDayMills > TimeUtils.string2Millis(today + " 18:00:00")) {
                        timeBean.setOverdue(true);
                    }
                }
                timeList.add(timeBean);
            }
            dateBean.setTimeList(timeList);
            dateBean.setSelect(false);
            mDateList.add(dateBean);
            thisDayMills = thisDayMills + oneDayMills;
        }
        mTimeList.addAll(mDateList.get(0).getTimeList());
    }

    private static class DateBean {
        private String week;
        private String date;
        private boolean isSelect;
        private List<TimeBean> timeList;

        String getWeek() {
            return week;
        }

        void setWeek(String week) {
            this.week = week;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        boolean isSelect() {
            return isSelect;
        }

        void setSelect(boolean select) {
            isSelect = select;
        }

        List<TimeBean> getTimeList() {
            return timeList;
        }

        void setTimeList(List<TimeBean> timeBean) {
            this.timeList = timeBean;
        }

        static class TimeBean {
            private boolean isOverdue;
            private String timeFrame;
            private boolean isSelect;

            boolean isOverdue() {
                return isOverdue;
            }

            void setOverdue(boolean overdue) {
                isOverdue = overdue;
            }

            boolean isSelect() {
                return isSelect;
            }

            void setSelect(boolean select) {
                isSelect = select;
            }

            String getTimeFrame() {
                return timeFrame;
            }

            void setTimeFrame(String timeFrame) {
                this.timeFrame = timeFrame;
            }
        }
    }
}
