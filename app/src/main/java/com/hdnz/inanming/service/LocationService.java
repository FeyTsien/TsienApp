package com.hdnz.inanming.service;/*
 * 获取当前的位置，并通过短信发送位置到指定号码
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPService;
import com.tbruyelle.rxpermissions2.RxPermissions;
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