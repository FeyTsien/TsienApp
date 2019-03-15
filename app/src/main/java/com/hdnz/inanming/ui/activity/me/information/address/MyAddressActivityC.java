package com.hdnz.inanming.ui.activity.me.information.address;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonSyntaxException;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.ProvincesBean;
import com.hdnz.inanming.bean.address.BlockBean;
import com.hdnz.inanming.bean.request.AddressBeanReq;
import com.hdnz.inanming.bean.result.AddressBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.utils.UrlUtils;
import com.hdnz.inanming.webview.WebViewActivity;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventBusUtil;
import com.tsienlibrary.eventbus.EventCode;
import com.tsienlibrary.mvp.GsonManger;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    SettingActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-07 11:09
 * Description: 用户地址添加、详情、编辑页面共用activity
 * Version:     V1.0.0
 * History:     历史信息
 */
public class MyAddressActivityC extends MVPActivity<MVPContract.View, MVPPresenter> {

    public static final String KEY_ADDRESS_ITEM = "address_item";
    //请求接口地址
    private String requestUrl;

    private AddressBean.ListBean mAddress;

    private List<ProvincesBean.ProvinceBean> options1Items = new ArrayList<>();
    private List<List<ProvincesBean.ProvinceBean.CityListBean>> options2Items = new ArrayList<>();
    private List<List<List<ProvincesBean.ProvinceBean.CityListBean.AreaListBean>>> options3Items = new ArrayList<>();

    BlockBean mBlockBean;
    private List<BlockBean.AddArrBean> mBlock1List = new ArrayList<>();
    private List<List<BlockBean.AddArrBean.LcListBean>> mBlock2List = new ArrayList<>();
    private List<List<List<BlockBean.AddArrBean.LcListBean.MphListBean>>> mBlock3List = new ArrayList<>();

    private String itemAddressId;//本条地址的id
    //    private String communityId;//社区
    private String committeeId;//居委会
    private String gridId;//网格
    private String blockId;//楼栋
    private String diasporaId;//散居户
    private String unitId;//单元
    private String floorId;//楼层
    private String houseNumId;//门牌号

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_idCard)
    EditText etIdCard;
    @BindView(R.id.tv_show_district)
    TextView tvShowDistrict;
    @BindView(R.id.tv_show_building)
    TextView tvShowBlock;
    @BindView(R.id.tv_show_house_number)
    TextView tvShowHouseNumber;
    @BindView(R.id.rl_house_number)
    RelativeLayout rlHouseNumber;
    @BindView(R.id.et_remark)
    EditText etRemark;

    private TextView[] views;
    private String[] hits;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_address;
    }

    @Override
    protected void initData() {
        views = new TextView[]{etName, etPhone, etIdCard, tvShowBlock};
        hits = new String[]{"请填写姓名", "请填写手机号", "请填写身份证", "请选择楼栋"};
        mAddress = (AddressBean.ListBean) getIntent().getSerializableExtra(KEY_ADDRESS_ITEM);
        if (mAddress == null) {
            requestUrl = UrlUtils.ADD_ADDRESS;
        } else {
            requestUrl = UrlUtils.UPDATE_ADDRESS;
            itemAddressId = mAddress.getId();
            committeeId = mAddress.getJwh();//居委会
            gridId = mAddress.getWg();//网格
            blockId = mAddress.getLd();//楼栋
            diasporaId = mAddress.getSjh();//散居户
            unitId = mAddress.getDy();//单元
            floorId = mAddress.getLc();//楼层
            houseNumId = mAddress.getMph();//门牌号
        }
        initJsonData();
    }

    @Override
    protected void initView() {
        //根据列表页传递的状态，设置title
        if (mAddress == null) {
            setToolBar(mToolbar, tvTitle, getResources().getString(R.string.add_address));
        } else {
            setToolBar(mToolbar, tvTitle, getResources().getString(R.string.edit_address));
            etName.setText(mAddress.getName());
            etPhone.setText(mAddress.getPhoneNumber());
            etIdCard.setText(mAddress.getNumber());
            tvShowBlock.setText(mAddress.getLdName());
            if (!TextUtils.isEmpty(mAddress.getDyName())) {
                rlHouseNumber.setVisibility(View.VISIBLE);
                tvShowHouseNumber.setText(mAddress.getDyName() + " " + mAddress.getLcName() + " " + mAddress.getMphName());
            }
            etRemark.setText(mAddress.getAddr());
        }
        tvRightMenu.setVisibility(View.VISIBLE);
        tvRightMenu.setText(R.string.save);

    }


    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(Event event) {
        super.onReceiveEvent(event);
        switch (event.getCode()) {
            case EventCode.ME_ADDRESS_A:
                LogUtils.i("addressT", (String) event.getData());
                try {
                    mBlockBean = GsonManger.getGsonManger().gsonFromat((String) event.getData(), BlockBean.class);
                    committeeId = mBlockBean.getJwhId();
                    gridId = mBlockBean.getWgId();
                    blockId = mBlockBean.getLdId();
                    diasporaId = mBlockBean.getSjhId();
                    if (TextUtils.isEmpty(mBlockBean.getAddresq())) {
                        tvShowBlock.setText(mBlockBean.getAddresq());
                    } else {
                        tvShowBlock.setText(mBlockBean.getSjh());
                    }
                    if (mBlockBean.getAddArr().size() > 0) {
                        rlHouseNumber.setVisibility(View.VISIBLE);
                        initBlockJsonData(mBlockBean);
                    } else {
                        rlHouseNumber.setVisibility(View.GONE);
                    }
                } catch (JsonSyntaxException e) {
                    ToastUtils.showLong("数据异常");
                }
                break;
        }
    }

    @Override
    protected void request() {
        super.request();
        for (int i = 0; i < views.length; i++) {
            if (TextUtils.isEmpty(views[i].getText().toString())) {
                ToastUtils.showLong(hits[i]);
                return;
            }
        }
        AddressBeanReq request = new AddressBeanReq();
        AddressBeanReq.ParamsBean params = new AddressBeanReq.ParamsBean();
        params.setId(itemAddressId);
        params.setName(etName.getText().toString());
        params.setPhoneNumber(etPhone.getText().toString());
        params.setNumber(etIdCard.getText().toString());
        params.setProvince("贵州省");
        params.setCity("贵阳市");
        params.setCounty("南明区");
//        params.setSq(communityId);
        params.setJwh(committeeId);
        params.setWg(gridId);
        params.setLd(blockId);
        params.setSjh(diasporaId);
        params.setDy(unitId);
        params.setLc(floorId);
        params.setMph(houseNumId);
        params.setAddr(etRemark.getText().toString());
        request.setParams(params);
        String jsonData = GsonManger.getGsonManger().toJson(request);
        showProgressDialog("保存中");
        mPresenter.request(requestUrl, jsonData, Object.class);

    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        EventBusUtil.post(new Event<>(EventCode.ME_ADDRESS_B, ""));
        this.finish();
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }

    @OnClick({R.id.tv_right_menu, R.id.rl_district, R.id.rl_building, R.id.rl_house_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_menu:
                request();
                break;
            case R.id.rl_district:
                showPickerView(tvShowDistrict, 0, 0, 0);
                break;
            case R.id.rl_building:
                WebViewActivity.goToWebView(this, "http://192.168.3.233:8007/addressMap");
//                ToastUtils.showShort("跳转楼栋H5页面...");
                break;
            case R.id.rl_house_number:
                if (mBlockBean == null) {
                    WebViewActivity.goToWebView(this, "http://192.168.3.233:8007/addressMap");
                } else {
                    showBlockPickerView(tvShowHouseNumber, 0, 0, 0);
                }
                break;
        }
    }

    /**
     * 弹出选择器
     */
    private void showPickerView(TextView textView, int pos1, int pos2, int pos3) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String text = "";
                if (options1Items != null && options1Items.size() > 0) {
                    text = options1Items.get(options1).getPickerViewText();
                }
                if (options2Items.get(options1) != null && options2Items.get(options1).size() > 0) {
                    text = options1Items.get(options1).getPickerViewText()
                            + " " + options2Items.get(options1).get(options2).getPickerViewText();
                }
                if (options3Items.get(options1).get(options2) != null && options3Items.get(options1).get(options2).size() > 0) {
                    text = options1Items.get(options1).getPickerViewText()
                            + " " + options2Items.get(options1).get(options2).getPickerViewText()
                            + " " + options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                }
                textView.setText(text);
                ToastUtils.showLong(text);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setTextXOffset(1, 1, 1)//不偏移则文字会被压缩
                .setSelectOptions(pos1, pos2, pos3)//默认选中项
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    /**
     * //解析省市区数据
     */
    private void initJsonData() {

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = GsonManger.getGsonManger().getJson(this, "district.json");//获取assets目录下的json文件数据
        ProvincesBean provincesBean = GsonManger.getGsonManger().gsonFromat(JsonData, ProvincesBean.class);//用Gson 转成实体

//        String jsonData = ProvincesToJson.getProvincesJson(this);
//        LogUtils.i(jsonData);
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = provincesBean.getProvince();

        for (int i = 0; i < provincesBean.getProvince().size(); i++) {//遍历省份
            List<ProvincesBean.ProvinceBean.CityListBean> cityList = provincesBean.getProvince().get(i).getCityList();//该省的城市列表（第二级）
            List<List<ProvincesBean.ProvinceBean.CityListBean.AreaListBean>> provinceAreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < provincesBean.getProvince().get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                List<ProvincesBean.ProvinceBean.CityListBean.AreaListBean> cityAreaList = provincesBean.getProvince().get(i).getCityList().get(c).getAreaList();//该城市的所有地区列表
                provinceAreaList.add(cityAreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(provinceAreaList);
        }
    }

    /**
     * 弹出选择器
     */
    private void showBlockPickerView(TextView textView, int pos1, int pos2, int pos3) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String unit = "";
                String floor = "";
                String houseNum = "";
                if (mBlock1List != null && mBlock1List.size() > 0) {
                    unit = mBlock1List.get(options1).getPickerViewText();
                    unitId = mBlock1List.get(options1).getId();
                }
                if (mBlock2List.get(options1) != null && mBlock2List.get(options1).size() > 0) {
                    floor = mBlock2List.get(options1).get(options2).getPickerViewText();
                    floorId = mBlock2List.get(options1).get(options2).getId();
                }
                if (mBlock3List.get(options1).get(options2) != null && mBlock3List.get(options1).get(options2).size() > 0) {
                    houseNum = mBlock3List.get(options1).get(options2).get(options3).getPickerViewText();
                    houseNumId = mBlock3List.get(options1).get(options2).get(options3).getId();
                }

                String text = unit + " " + floor + " " + houseNum;
                textView.setText(text);
                ToastUtils.showLong(text);
            }
        })
                .setTitleText("房间选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setTextXOffset(1, 1, 1)//不偏移则文字会被压缩
                .setSelectOptions(pos1, pos2, pos3)//默认选中项
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(mBlock1List, mBlock2List, mBlock3List);//三级选择器
        pvOptions.show();
    }

    private void initBlockJsonData(BlockBean blockBean) {
        //提示：Bean类需要实现IPickerViewData接口类

        LogUtils.i("addressT", "initJsonDa");
        mBlock1List = mBlockBean.getAddArr();

        for (int i = 0; i < mBlock1List.size(); i++) {//遍历省份
            List<BlockBean.AddArrBean.LcListBean> lcList = mBlock1List.get(i).getLcList();//该省的城市列表（第二级）
            List<List<BlockBean.AddArrBean.LcListBean.MphListBean>> addMphList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < lcList.size(); c++) {//遍历该省份的所有城市
                List<BlockBean.AddArrBean.LcListBean.MphListBean> mphList = lcList.get(c).getMphList();//该城市的所有地区列表
                addMphList.add(mphList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            mBlock2List.add(lcList);

            /**
             * 添加地区数据
             */
            mBlock3List.add(addMphList);
        }
    }

    /**
     * 根据名称计算位置
     *
     * @param name
     * @return
     */
    private int provincePos(String name) {
        for (int i = 0; i < options1Items.size(); i++) {
            if (options1Items.get(i).getName().equals(name)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 根据名称计算位置
     *
     * @param name
     * @return
     */
    private int cityPos(String name) {
        for (int i = 0; i < options2Items.size(); i++) {
            List<ProvincesBean.ProvinceBean.CityListBean> cityListBeans = options2Items.get(i);
            for (int j = 0; j < cityListBeans.size(); j++) {
                ProvincesBean.ProvinceBean.CityListBean cityListBean = cityListBeans.get(j);
                if (cityListBean.getName().equals(name)) {
                    return j;
                }
            }
        }
        return 0;
    }


    /**
     * 根据名称计算位置
     *
     * @param name
     * @return
     */
    private int districtPos(String name) {
        for (List<List<ProvincesBean.ProvinceBean.CityListBean.AreaListBean>> options3Item : options3Items) {
            for (List<ProvincesBean.ProvinceBean.CityListBean.AreaListBean> areaListBeans : options3Item) {
                for (int i = 0; i < areaListBeans.size(); i++) {
                    ProvincesBean.ProvinceBean.CityListBean.AreaListBean areaListBean = areaListBeans.get(i);
                    if (areaListBean.getName().equals(name)) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }
}
