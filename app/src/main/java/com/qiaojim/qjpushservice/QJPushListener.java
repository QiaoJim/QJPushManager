package com.qiaojim.qjpushservice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.support.api.push.PushReceiver;
import com.qiaojim.qjpushservice.entity.QJMessage;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;

import java.util.List;

/**
 * Created by QiaoJim on 2017/6/8.
 */

public interface QJPushListener {

    /**
     * 收到推送消息
     * @param msg
     */
    void onMsgArrived(QJMessage msg);

    /**
     * 通知点击事件回调
     * @param msg
     */
    void onNotificationClicked(QJMessage msg);

    /**
     * 错误处理回调
     */
    void onError();


    interface QJHuaweiListener{
        void onUpdateFailed(@NonNull ConnectionResult connectionResult);
        void onConnected();
        void onConnectionSuspended(int i);
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

}
