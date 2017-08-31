package com.qiaojim.qjpushservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.entity.QJMessage;

/**
 * Created by QiaoJim on 2017/8/29.
 */

public class QJPushReceiver extends BroadcastReceiver {

    private QJPushListener listener;

    public void setListener(QJPushListener listener) {
        if (listener!=null) {
            this.listener = listener;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        QJMessage message= (QJMessage) intent.getExtras().get(QJConstant.QJMESSAGE_KEY);
        if (message!=null) {
            Log.e("QJ","onReceive\nQJMessage : "+message.getTitle()+"   "+message.getBody());
        }
        String action = intent.getAction();
        switch (action){
            case QJConstant.QJPUSH_BROADCAST1:
                if (listener!=null){
                    listener.onMsgArrived(message);
                    Log.e("QJ","onReceive\n消息收到回调");
                }
                break;
            case QJConstant.QJPUSH_BROADCAST2:
                if (listener!=null){
                    listener.onNotificationClicked(message);
                    Log.e("QJ","onReceive\n通知栏点击回调");
                }
                break;
            default:
                if (listener!=null){
                    listener.onError();
                    Log.e("QJ","onReceive\n错误回调");
                }
                break;
        }
    }
}
