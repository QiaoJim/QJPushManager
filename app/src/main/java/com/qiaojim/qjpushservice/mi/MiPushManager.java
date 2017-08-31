package com.qiaojim.qjpushservice.mi;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.QJPushManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by QiaoJim on 2017/6/8.
 */

public class MiPushManager {

    //app 上下文
    private static Context context = QJPushManager.getContext();

    //小米的 app id 和 app key
    public static String MiPushAppID;
    public static String MiPushAppKey;

    /**
     * 准备，必须最先调用
     */
    public static void prepare(String id, String key) {
        MiPushAppID = id;
        MiPushAppKey = key;
    }

    /**
     * 开启小米推送，官方示例写法
     */
    public static void startMiPush() {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                // 注册服务
                MiPushClient.registerPush(context, MiPushAppID, MiPushAppKey);

                Log.i(QJConstant.MI_TAG,"xiaomi push reg finished");
            }
        }
    }


    /**
     * 注销小米推送，官方建议在onDestroy()调用
     */
    public static void stopMiPush() {
        //注销
        MiPushClient.unregisterPush(context);
    }

    /**
     * 设置别名
     *
     * @param alias
     */
    public static void setAlias(String alias) {
        MiPushClient.setAlias(context, alias, null);
    }

    /**
     * 设置用户名
     *
     * @param account
     */
    public static void setAccount(String account) {
        MiPushClient.setUserAccount(context, account, null);
    }

    /**
     * 设置订阅标签
     *
     * @param tag
     */
    public static void setTag(String tag) {
        MiPushClient.subscribe(context, tag, null);
    }

    /**
     * 删除别名
     *
     * @param alias
     */
    public static void deleteAlias(String alias) {
        MiPushClient.unsetAlias(context, alias, null);
    }

    /**
     * 删除用户
     *
     * @param account
     */
    public static void deleteAccount(String account) {
        MiPushClient.unsetUserAccount(context, account, null);
    }

    /**
     * 删除标签
     *
     * @param tag
     */
    public static void deleteTag(String tag) {
        MiPushClient.unsubscribe(context, tag, null);
    }
}
