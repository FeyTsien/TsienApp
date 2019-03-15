package com.hdnz.inanming.ui.activity.home.govaffairsbooking.gabbusiness;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.bean.AppBean;
import com.hdnz.inanming.bean.result.ResultBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.home.GovAffairsBookingActivity;
import com.hdnz.inanming.ui.activity.home.govaffairsbooking.GABBusinessActivity;
import com.hdnz.inanming.ui.activity.me.AuthenticationActivity;
import com.hdnz.inanming.ui.popupwindow.GABBookingInfoPopup;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.bean.CommonBean;

import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2019/01/16
 *     desc   :
 * </pre>
 */
public class GABBookingInfoActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private String officeHall;
    private String businessId;
    private String reservationDate;
    private String reservationTime;

    @BindString(R.string.booking_info)
    String mTitle;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_menu)
    TextView mTvRightMenu;
    @BindView(R.id.tv_office_hall)
    TextView mTvOfficeHall;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gab_business_info;
    }

    @Override
    protected void initData() {
        officeHall = getIntent().getStringExtra(GovAffairsBookingActivity.KEY_OFFICE_HALL);
        businessId = getIntent().getStringExtra(GABBusinessActivity.KEY_BUSINESS_ID);
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, mTitle);
        mTvRightMenu.setText(R.string.submit);
        mTvRightMenu.setVisibility(View.VISIBLE);
        mTvOfficeHall.setText(officeHall);
        mTvTime.setOnClickListener(v ->
                GABBookingInfoPopup.getInstance(GABBookingInfoActivity.this, map -> {
                            mTvTime.setText((String) map.get(GABBookingInfoPopup.KEY_DATE_TIME));
                            reservationDate = (String) map.get(GABBookingInfoPopup.KEY_DATE);
                            reservationTime = (String) map.get(GABBookingInfoPopup.KEY_TIME);
                        }
                ).showPopupWindow());
    }

    @OnClick({R.id.tv_time, R.id.tv_right_menu})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.tv_time:

                break;
            case R.id.tv_right_menu:
                // 提交预约,先去校验实名认证
                if (TextUtils.equals(AppData.getValueStr(AppData.KEY_AUTHENTICATION_STATUS), "1")) {
                    //审核中,因为是以前的缓存，不清楚当前是否认证通过，所以要去请求接口确认（主要原因，后台懒B）
                    mPresenter.request(UrlUtils.AUTHENTICATION_STATUS, "", Map.class);
                } else if (TextUtils.equals(AppData.getValueStr(AppData.KEY_AUTHENTICATION_STATUS), "2")) {
                    //认证通过，则直接执行提交
                    submitReservation();
                } else {
                    //未认证和认证失败都要去实名认证页面
                    ActivityUtils.startActivity(AuthenticationActivity.class);
                }
                break;
        }
    }

    /**
     * 提交预约信息
     */
    private void submitReservation() {
        if (TextUtils.isEmpty(reservationDate) || TextUtils.isEmpty(reservationTime)) {
            ToastUtils.showShort("请选择详细时间");
            return;
        }
        String url = UrlUtils.SUBMIT_RESERVATION
                + "&reservationDate=" + reservationDate //日期
                + "&periodTime=" + reservationTime  //时间段
                + "&custNo=" + AppData.getValueStr(AppData.KEY_USER_ID) //用户ID
                + "&mobile=" + AppData.getValueStr(AppData.KEY_PHONE_NUMBER)    //手机号
                + "&idCard=" + AppData.getValueStr(AppData.KEY_ID_CARD) //身份证
                + "&businessId=" + businessId    //预约业务ID
                + "&brchNo=11";    //预约业务ID
        mPresenter.requestWithGet(url, String.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);

        if (TextUtils.equals(requestUrl, UrlUtils.AUTHENTICATION_STATUS)) {
            Map map = (Map) commonBean.getData();
            if (map == null) {
                return;
            }
            String authStatus = (String) map.get("auditResult");
            AppData.setValueStr(AppData.KEY_AUTHENTICATION_STATUS, authStatus);

            if (TextUtils.equals(authStatus, "1")) {
                ToastUtils.showLong("审核中");
            } else if (TextUtils.equals(authStatus, "2")) {
                //认证通过，则直接执行提交
                submitReservation();
            } else {
                //未认证和认证失败都要去实名认证页面
                ActivityUtils.startActivity(AuthenticationActivity.class);
            }
        } else {
            ToastUtils.showLong("预约成功");
        }

    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }
}
