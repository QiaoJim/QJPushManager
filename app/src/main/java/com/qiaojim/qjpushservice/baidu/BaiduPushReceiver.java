package com.qiaojim.qjpushservice.baidu;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.entity.QJMessage;
import com.qiaojim.qjpushservice.utils.QJBroadcastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class BaiduPushReceiver extends PushMessageReceiver {

    /**
     * 调用PushManager.startWork后，sdk将对push
     * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
     * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
     *
     * @param context   BroadcastReceiver的执行Context
     * @param errorCode 绑定接口返回值，0 - 成功
     * @param appid     应用id。errorCode非0时为null
     * @param userId    应用user id。errorCode非0时为null
     * @param channelId 应用channel id。errorCode非0时为null
     * @param requestId 向服务端发起的请求id。在追查问题时有用；
     * @return none
     */
    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString = "errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        Log.i(QJConstant.BAIDU_TAG, "========= onBind ============\n"+responseString);

        if (errorCode == 0) {
            // 绑定成功
            Log.i(QJConstant.BAIDU_TAG, "baidu push bind success");
        }else {
            Log.e(QJConstant.BAIDU_TAG, "baidu push bind failed");

            QJBroadcastUtil.sendQJBroad(context, null, QJConstant.ERROR);
        }

    }

    /**
     * 接收透传消息的函数。
     *
     * @param context             上下文
     * @param message             推送的消息
     * @param customContentString 自定义内容,为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String message,
                          String customContentString) {
        String messageString = "passthrough msg \"" + message
                + "\" customContentString=" + customContentString;
        Log.i(QJConstant.BAIDU_TAG, "========= onMessage ============\n"+messageString);

        /*
        // 自定义内容获取方式，透传消息推送时自定义内容中设置的键和值,键名为QJPushKey
        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String value = null;
                if (!customJson.isNull("QJPushKey")) {
                    value = customJson.getString("QJPushKey");
                    Log.i(QJConstant.BAIDU_TAG,"extra info key value , QJPushKey : "+value);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/


    }

    /**
     * 接收通知到达的函数。
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */

    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String customContentString) {

        String notifyString = "title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        Log.i(QJConstant.BAIDU_TAG, "========= onNotificationArrived ============\n"+notifyString);

        QJMessage qjMessage = new QJMessage();
        qjMessage.setTitle(title);
        qjMessage.setBody(description);
        qjMessage.setExtra(customContentString);
        Log.e("QJ","onNotificationArrived\n本地广播1  准备发送");
        QJBroadcastUtil.sendQJBroad(context, qjMessage, QJConstant.MSG_ARRIVED);

        /*
        // 自定义内容获取方式，透传消息推送时自定义内容中设置的键和值,键名为QJPushKey
        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String value = null;
                if (!customJson.isNull("QJPushKey")) {
                    value = customJson.getString("QJPushKey");
                    Log.i(QJConstant.BAIDU_TAG,"extra info key value , QJPushKey : "+value);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                listener.onError(e);
            }
        }
        */

    }

    /**
     * 接收通知点击的函数。
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "title=\"" + title + "\" description=\""
                + description + "\" customContent=" + customContentString;
        Log.i(QJConstant.BAIDU_TAG, "========= onNotificationClicked ============\n"+notifyString);

        QJMessage qjMessage = new QJMessage();
        qjMessage.setTitle(title);
        qjMessage.setBody(description);
        qjMessage.setExtra(customContentString);
        Log.e("QJ","onNotificationArrived\n本地广播2  准备发送");
        QJBroadcastUtil.sendQJBroad(context, qjMessage, QJConstant.NOTIFICATION_CLICKED);

        /*
        // 自定义内容获取方式，透传消息推送时自定义内容中设置的键和值,键名为QJPushKey
        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String value = null;
                if (!customJson.isNull("QJPushKey")) {
                    value = customJson.getString("QJPushKey");
                    Log.i(QJConstant.BAIDU_TAG,"extra info key value , QJPushKey : "+value);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                listener.onError(e);
            }
        }
        */

    }

    /**
     * setTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
     * @param successTags 设置成功的tag
     * @param failTags    设置失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.i(QJConstant.BAIDU_TAG, "========= onSetTags ============\n"+responseString);

        if (errorCode==0){
            Log.i(QJConstant.BAIDU_TAG,"set tag ok");
        }else{
            Log.e(QJConstant.BAIDU_TAG,"set tag failed");
        }

    }

    /**
     * delTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
     * @param successTags 成功删除的tag
     * @param failTags    删除失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.i(QJConstant.BAIDU_TAG, "========= onDelTags ============\n"+responseString);

        if (errorCode==0){
            Log.i(QJConstant.BAIDU_TAG,"delete tag ok");
        }else{
            Log.e(QJConstant.BAIDU_TAG,"delete tag failed");
        }
    }

    /**
     * listTags() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码。0表示列举tag成功；非0表示失败。
     * @param tags      当前应用设置的所有tag。
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {
        String responseString = "errorCode=" + errorCode + " tags="
                + tags;
        Log.i(QJConstant.BAIDU_TAG, "========= onListTags ============\n"+responseString);

        if (errorCode==0){
            Log.i(QJConstant.BAIDU_TAG,"list all tags ok");
        }else{
            Log.e(QJConstant.BAIDU_TAG,"list all tags failed");
        }


    }

    /**
     * PushManager.stopWork() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码。0表示从云推送解绑定成功；非0表示失败。
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "errorCode=" + errorCode
                + " requestId = " + requestId;
        Log.i(QJConstant.BAIDU_TAG, "========= onUnbind ============\n"+responseString);

        if (errorCode==0){
            Log.i(QJConstant.BAIDU_TAG,"baidu push unbind ok");
        }else{
            Log.e(QJConstant.BAIDU_TAG,"baidu push unbind error");
        }

    }

}
