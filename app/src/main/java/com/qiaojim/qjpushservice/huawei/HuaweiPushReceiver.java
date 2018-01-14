package com.qiaojim.qjpushservice.huawei;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.support.api.push.PushReceiver;
import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.entity.QJMessage;
import com.qiaojim.qjpushservice.utils.QJBroadcastUtil;

/**
 * Created by QiaoJim on 2017/6/7.
 */

public class HuaweiPushReceiver extends PushReceiver {


    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        String content = "get token and belongId successful, token = " + token + ",belongId = " + belongId;
        Log.i(QJConstant.HUAWEI_TAG, "===== onToken ======\n" + content);

    }

    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            String content = "Receive a Push pass-by message： " + new String(msg, "UTF-8");
            Log.i(QJConstant.HUAWEI_TAG, "===== onPushMsg ======\n" + content);

            QJMessage qjMessage = new QJMessage();
            qjMessage.setRomType(QJConstant.HUAWEI_TAG);
            qjMessage.setMsgType(QJConstant.TYPE_PASS_THROUGH);
            qjMessage.setBody(new String(msg, "UTF-8"));
            qjMessage.setExtra(bundle);

//            Log.e("QJ", "onNotificationArrived\n本地广播1  准备发送");
            QJBroadcastUtil.sendQJBroad(context, qjMessage, QJConstant.MSG_ARRIVED);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onEvent(Context context, Event event, Bundle extras) {
        String content = "receive extented notification message: " + extras.getString(BOUND_KEY.pushMsgKey);
        Log.i(QJConstant.HUAWEI_TAG, "===== onEvent ======\n" + content);

        //打开通知栏消息或点击，关闭通知栏
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {

            QJMessage qjMessage = new QJMessage();
            qjMessage.setRomType(QJConstant.HUAWEI_TAG);
            qjMessage.setMsgType(QJConstant.TYPE_NOTIFICATION);
            qjMessage.setExtra(extras.getString(BOUND_KEY.pushMsgKey));
//            Log.e("QJ", "onNotificationArrived\n本地广播2  准备发送");
            QJBroadcastUtil.sendQJBroad(context, qjMessage, QJConstant.NOTIFICATION_CLICKED);

        }
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        try {
            String content = "The current push status： " + (pushState ? "Connected" : "Disconnected");
            Log.i(QJConstant.HUAWEI_TAG, "===== onPushState ======\n" + content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
