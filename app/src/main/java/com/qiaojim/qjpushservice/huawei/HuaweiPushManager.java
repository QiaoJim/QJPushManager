package com.qiaojim.qjpushservice.huawei;

import android.content.Context;
import android.util.Log;

import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.TokenResult;
import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.QJPushManager;

/**
 * Created by QiaoJim on 2017/6/8.
 */

public class HuaweiPushManager {

    //app山下文
    private static Context context = QJPushManager.getContext();

    //华为client
    private static HuaweiApiClient client = null;

    /**
     * 华为推送准备，必须最先调用
     *
     * @param listener HuaweiListenerImpl的实例
     */
    public static void prepare(HuaweiListenerImpl listener) {
        //初始化client实例
        client = new HuaweiApiClient.Builder(context)
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(listener)
                .addOnConnectionFailedListener(listener)
                .build();
    }

    /**
     * 开启华为推送
     */
    public static void startHuaweiPush() {
        //连接服务
        client.connect();
        //获取token
        getToken();

        Log.i(QJConstant.HUAWEI_TAG, "huawei push start finished");
    }

    /**
     * 注销华为推送服务，官方推荐在 onStop（）调用
     */
    public static void stopHuaweiPush() {
        client.disconnect();
    }

    /**
     * 尝试获取token
     */
    public static void getToken() {
        //获取前，确保client已连接
        if (!isConnected()) {
            Log.e(QJConstant.HUAWEI_TAG, "huawei client disconnect");
            return;
        }

        Log.i(QJConstant.HUAWEI_TAG, "huawei client is connected");
        // 异步调用方式获取token
        PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
        tokenResult.setResultCallback(new ResultCallback<TokenResult>() {

            @Override
            public void onResult(TokenResult result) {

            }

        });
    }


    /**
     * 注销token，注销成功后，客户端不再接受push消息
     *
     * @param token 设备标示token，华为一般使用手机IMEI号
     */
    public static void deleteToken(final String token) {
        //确保client不为null
        if (client == null) {
            return;
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    HuaweiPush.HuaweiPushApi.deleteToken(client, token);

                } catch (Exception e) {
                    Log.i(QJConstant.HUAWEI_TAG, "delete token exception, " + e.toString());
                }
            }
        }.start();

    }

    /**
     * 获得华为client
     * @return  华为client
     */
    public static HuaweiApiClient getClient() {
        return client;
    }

    /**
     * 判断client连接状态
     * @return true 已连接，可获取token
     */
    private static boolean isConnected() {
        return client != null && client.isConnected();
    }

}
