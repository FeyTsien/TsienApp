package com.hdnz.inanming.utils;

import android.content.Context;

import com.hdnz.inanming.bean.ProvincesBean;
import com.hdnz.inanming.bean.TestBean;
import com.tsienlibrary.mvp.GsonManger;

import java.util.ArrayList;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/24
 *     desc   :
 * </pre>
 */
public class ProvincesToJson {

    public static String getProvincesJson(Context context) {
        String JsonData = GsonManger.getGsonManger().getJson(context, "provinces");//获取assets目录下的json文件数据
        TestBean testBean = GsonManger.getGsonManger().gsonFromat(JsonData, TestBean.class);//用Gson 转成实体
        ProvincesBean provincesBean = new ProvincesBean();
        provincesBean.setProvince(new ArrayList<>());
        for (int i = 0; i < testBean.getAddress().size(); i++) {
            if (testBean.getAddress().get(i).getName().endsWith("市辖区")) {
                testBean.getAddress().remove(i);
                i--;
            }
            if (testBean.getAddress().get(i).getAdcode() % 10000 == 0) {
                ProvincesBean.ProvinceBean provinceBean = new ProvincesBean.ProvinceBean();
                provinceBean.setPos(provincesBean.getProvince().size());//下标

                provinceBean.setAdcode(testBean.getAddress().get(i).getAdcode());
                provinceBean.setName(testBean.getAddress().get(i).getName());
                provinceBean.setCityList(new ArrayList<>());
                provincesBean.getProvince().add(provinceBean);
                testBean.getAddress().remove(testBean.getAddress().get(i));
                i--;
            }
        }
        for (int j = 0; j < provincesBean.getProvince().size(); j++) {
            for (int i = 0; i < testBean.getAddress().size(); i++) {
                if (testBean.getAddress().get(i).getAdcode() % 100 == 0) {
                    int shicode = (int) Math.floor(testBean.getAddress().get(i).getAdcode() / 10000);

                    if (shicode == (int) Math.floor(provincesBean.getProvince().get(j).getAdcode() / 10000)) {
                        ProvincesBean.ProvinceBean.CityListBean cityListBean = new ProvincesBean.ProvinceBean.CityListBean();
                        cityListBean.setPos(provincesBean.getProvince().get(j).getCityList().size());//下标

                        cityListBean.setAdcode(testBean.getAddress().get(i).getAdcode());
                        cityListBean.setName(testBean.getAddress().get(i).getName());
                        cityListBean.setAreaList(new ArrayList<>());
                        provincesBean.getProvince().get(j).getCityList().add(cityListBean);
                        testBean.getAddress().remove(testBean.getAddress().get(i));
                        i--;
                    }
                }
            }
        }
        for (int j = 0; j < provincesBean.getProvince().size(); j++) {
            if (provincesBean.getProvince().get(j).getCityList() != null && provincesBean.getProvince().get(j).getCityList().size() > 0) {
                for (int k = 0; k < provincesBean.getProvince().get(j).getCityList().size(); k++) {
                    for (int i = 0; i < testBean.getAddress().size(); i++) {

                        int testad = (int) Math.floor(testBean.getAddress().get(i).getAdcode() / 100);
                        int provincesad = (int) Math.floor(provincesBean.getProvince().get(j).getCityList().get(k).getAdcode() / 100);

                        if (testad == provincesad) {
                            ProvincesBean.ProvinceBean.CityListBean.AreaListBean areaListBean = new ProvincesBean.ProvinceBean.CityListBean.AreaListBean();
                            areaListBean.setPos(provincesBean.getProvince().get(j).getCityList().get(k).getAreaList().size());//下标

                            areaListBean.setAdcode(testBean.getAddress().get(i).getAdcode());
                            areaListBean.setName(testBean.getAddress().get(i).getName());
                            provincesBean.getProvince().get(j).getCityList().get(k).getAreaList().add(areaListBean);
                            testBean.getAddress().remove(testBean.getAddress().get(i));
                            i--;
                        }
                    }

                }
            }
        }
        return GsonManger.getGsonManger().toJson(provincesBean);
    }
}
