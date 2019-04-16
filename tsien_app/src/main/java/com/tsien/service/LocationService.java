package com.tsien.service;/*
 * 获取当前的位置，并通过短信发送位置到指定号码
 */

import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.tsien.app.AppData;
import com.tsien.mvp.contract.MVPContract;
import com.tsien.mvp.presenter.MVPPresenter;
import com.tsien.mvp.view.MVPService;
import com.tsienlibrary.utils.location.LocationHelper;
import com.tsienlibrary.utils.location.LocationUtils;

public class LocationService extends MVPService<MVPContract.View, MVPPresenter> {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initLocation();
    }

    private void initLocation() {
        LocationUtils.getInstance(this).initLocation(new LocationHelper() {
            @Override
            public void UpdateLocation(Location location) {
                LogUtils.i("MoLin", "location.getLatitude():" + location.getLatitude());
                AppData.setValueStr(AppData.KEY_LOCATION_LONGITUDE, String.valueOf(location.getLongitude()));
                AppData.setValueStr(AppData.KEY_LOCATION_LATITUDE, String.valueOf(location.getLatitude()));
            }

            @Override
            public void UpdateStatus(String provider, int status, Bundle extras) {
            }

            @Override
            public void UpdateGPSStatus(GpsStatus pGpsStatus) {

            }

            @Override
            public void UpdateLastLocation(Location location) {
                LogUtils.i("MoLin", "UpdateLastLocation_location.getLatitude():" + location.getLatitude());
                AppData.setValueStr(AppData.KEY_LOCATION_LONGITUDE, String.valueOf(location.getLongitude()));
                AppData.setValueStr(AppData.KEY_LOCATION_LATITUDE, String.valueOf(location.getLatitude()));
            }
        });
    }

    @Override
    public void onDestroy() {
        LocationUtils.getInstance(this).removeLocationUpdatesListener();
        super.onDestroy();
    }

}