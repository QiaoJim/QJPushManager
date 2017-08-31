package com.qiaojim.qjpushservice.huawei;

import android.support.annotation.NonNull;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.api.HuaweiApiClient;
import com.qiaojim.qjpushservice.QJPushListener;

/**
 * Created by QiaoJim on 2017/6/12.
 */

public class HuaweiListenerImpl implements HuaweiApiClient.ConnectionCallbacks,
        HuaweiApiAvailability.OnUpdateListener, HuaweiApiClient.OnConnectionFailedListener {


    private QJPushListener.QJHuaweiListener listener=null;

    //设置接口
    public void setQJListener(QJPushListener.QJHuaweiListener listener){
        this.listener = listener;
    }

    @Override
    public void onUpdateFailed(@NonNull ConnectionResult connectionResult) {
        if (listener!=null) {
            listener.onUpdateFailed(connectionResult);
        }
    }

    @Override
    public void onConnected() {
        if (listener!=null) {
            listener.onConnected();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (listener!=null) {
            listener.onConnectionSuspended(i);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (listener!=null) {
            listener.onConnectionFailed(connectionResult);
        }
    }
}
