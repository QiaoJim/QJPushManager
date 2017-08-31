package com.qiaojim.qjpushservice.mi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.entity.QJMessage;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

public class MiPushReceiver extends PushMessageReceiver {

    private String mTopic;
    private String mAlias;
    private String mTips;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        super.onReceivePassThroughMessage(context, message);

        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
            mTips = mTopic;
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
            mTips += mAlias;
        }

        Log.w(QJConstant.MI_TAG, "==== onReceivePassThroughMessage ====\n " + mTips);

    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        super.onNotificationMessageArrived(context, message);

        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
            mTips = mTopic;
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
            mTips += mAlias;
        }

        Log.w(QJConstant.MI_TAG, "==== onNotificationMessageArrived ====\n " + mTips);

        QJMessage qjMessage = new QJMessage();
        qjMessage.setTitle(message.getTitle());
        qjMessage.setBody(message.getDescription());
        qjMessage.setExtra(message.toString());
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {

        QJMessage qjMessage = new QJMessage();
        qjMessage.setTitle(message.getTitle());
        qjMessage.setBody(message.getDescription());
        qjMessage.setExtra(message.toString());
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {

    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {

        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String mRegId = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                Log.i(QJConstant.MI_TAG, "reg success, id=" + mRegId);
            } else {
                Log.e(QJConstant.MI_TAG, "reg fail(2), result code =" + message.getResultCode());
            }
        } else {
            Log.e(QJConstant.MI_TAG, "reg fail(1), reason : " + message.getReason());
        }
    }


}
