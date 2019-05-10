package com.tsienlibrary.utils.location;

import android.location.GpsStatus;
import android.location.Location;
import android.os.Bundle;

/**
 *
 */
public interface LocationHelper {

    void UpdateLocation(Location location);//位置信息发生改变

    void UpdateStatus(String provider, int status, Bundle extras);//位置状态发生改变

    void UpdateGPSStatus(GpsStatus pGpsStatus);//GPS状态发生改变

    void UpdateLastLocation(Location location);
}
