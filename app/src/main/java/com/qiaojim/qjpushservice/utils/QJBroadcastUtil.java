package com.qiaojim.qjpushservice.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.qiaojim.qjpushservice.QJPushListener;
import com.qiaojim.qjpushservice.QJPushReceiver;
import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.entity.QJMessage;

/**
 * Created by QiaoJim on 2017/8/29.
 */

public class QJBroadcastUtil {

    /**
     * 注册本地接受器
     * @param context
     * @param listener
     */
    public static void regQJReceiver(Context context, QJPushReceiver qjPushReceiver, QJPushListener listener){
        IntentFilter filter=new IntentFilter();
        filter.addAction(QJConstant.QJPUSH_BROADCAST1);
        filter.addAction(QJConstant.QJPUSH_BROADCAST2);
        filter.addAction(QJConstant.QJPUSH_BROADCAST3);

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.registerReceiver(qjPushReceiver, filter);
        if (listener!=null) {
            qjPushReceiver.setListener(listener);
        }
    }

    /**
     * 注销本地广播
     * @param context
     * @param qjPushReceiver
     */
    public static void unregQJReceiver(Context context, QJPushReceiver qjPushReceiver){
        LocalBroadcastManager manager=LocalBroadcastManager.getInstance(context);
        manager.unregisterReceiver(qjPushReceiver);
    }

    /**
     * 发送本地广播，接受消息推送体
     * @param context
     * @param message
     * @param broadType
     */
    public static void sendQJBroad(Context context, QJMessage message, int broadType) {
        //本地广播
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        Intent intent = null;
        switch (broadType) {
            case QJConstant.MSG_ARRIVED:
                intent = new Intent(QJConstant.QJPUSH_BROADCAST1);
                break;
            case QJConstant.NOTIFICATION_CLICKED:
                intent = new Intent(QJConstant.QJPUSH_BROADCAST2);
                break;
            default:
                intent = new Intent(QJConstant.QJPUSH_BROADCAST3);
                break;
        }
        intent.putExtra(QJConstant.QJMESSAGE_KEY, message);
        localBroadcastManager.sendBroadcast(intent);
        Log.e("QJ","sendQJBroad\n本地广播  已发送");
    }
}
