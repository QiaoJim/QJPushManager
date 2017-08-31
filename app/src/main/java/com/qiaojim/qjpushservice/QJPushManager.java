package com.qiaojim.qjpushservice;

import android.content.Context;
import android.util.Log;

import com.huawei.hms.api.HuaweiApiClient;
import com.qiaojim.qjpushservice.baidu.BaiduPushManager;
import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.huawei.HuaweiListenerImpl;
import com.qiaojim.qjpushservice.huawei.HuaweiPushManager;
import com.qiaojim.qjpushservice.mi.MiPushManager;
import com.qiaojim.qjpushservice.utils.QJRomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QiaoJim on 2017/6/8.
 */

public class QJPushManager {

    private static Context context = null;

    private static boolean useImeiTag=false;

    /**
     * 初始化context，准备rom配置
     * @param c  app上下文，建议传入 ApplicationContext
     */
    public static void prepare(Context c,boolean flag){
        context=c;
        useImeiTag=flag;
        QJRomUtil.prepare(c);
    }

    /**
     * 设置小米授权信息
     * @param appId
     * @param appKey
     */
    public static void prepareMi(String appId,String appKey){
        MiPushManager.prepare(appId,appKey);
    }

    /**
     * 设置百度授权信息
     * @param apiKey
     */
    public static void prepareBaidu(String apiKey){
        BaiduPushManager.prepare(apiKey);
    }

    /**
     * 设置华为client必须的接口实现类
     * @param listener  HuaweiListenerImpl类的一个实例，new一个即可
     */
    public static void prepareHuawei(HuaweiListenerImpl listener){
        HuaweiPushManager.prepare(listener);
    }

    /**
     * 开启推送服务
     */
    public static void startPushService() {

        QJRomUtil.ROM_TYPE userType = QJRomUtil.getRomType();
        if (userType == QJRomUtil.ROM_TYPE.Mi) {
            MiPushManager.startMiPush();
            if (useImeiTag){
                MiPushManager.setTag(QJPushManager.getIMEI());
            }
        } else if (userType == QJRomUtil.ROM_TYPE.Huawei) {
            HuaweiPushManager.startHuaweiPush();
        } else {
            BaiduPushManager.startBaiduPush();
            if (useImeiTag){
                List<String> tags = new ArrayList<>();
                tags.add(QJPushManager.getIMEI());
                BaiduPushManager.setTags(tags);
            }
        }
    }

    /**
     * 注销推送服务
     */
    public static void stopPushService(){
        QJRomUtil.ROM_TYPE userType = QJRomUtil.getRomType();
        if (userType == QJRomUtil.ROM_TYPE.Mi) {
            MiPushManager.stopMiPush();
        } else if (userType == QJRomUtil.ROM_TYPE.Huawei) {
            HuaweiPushManager.stopHuaweiPush();
        } else {
            //BaiduPushManager.stopBaiduPush();
            HuaweiPushManager.stopHuaweiPush();
        }
    }

    /**
     * 获得 ROM 类型
     *
     * @return 类型
     */
    public static String getRomType() {
        QJRomUtil.ROM_TYPE userType = QJRomUtil.getRomType();
        if (userType == QJRomUtil.ROM_TYPE.Mi) {
            return "Xiaomi";
        } else if (userType == QJRomUtil.ROM_TYPE.Huawei) {
            return "Huawei";
        } else {
            return "Other";
        }
    }

    /**
     * 获得 用户的 IMEI
     *
     * @return 用户IMEI
     */
    public static String getIMEI() {
        return QJRomUtil.getIMEI();
    }

    /**
     * @return 上下文
     */
    public static Context getContext() {
        return context;
    }


    //小米自定义
    public static void setMiTag(String tag){
        MiPushManager.setTag(tag);
        Log.i(QJConstant.MI_TAG,"xiaomi tag set ok, tag is : "+tag);
    }

    public static void setMiAlias(String alias){
        MiPushManager.setAlias(alias);
        Log.i(QJConstant.MI_TAG,"xiaomi alias set ok, alias is : "+alias);
    }

    public static void setMiAccount(String account){
        MiPushManager.setAccount(account);
        Log.i(QJConstant.MI_TAG,"xiaomi account set ok, account is : "+account);
    }
    public static void deleteMiTag(String tag){
        MiPushManager.deleteTag(tag);
        Log.i(QJConstant.MI_TAG,"xiaomi tag delete ok, tag is : "+tag);
    }

    public static void deleteMiAlias(String alias){
        MiPushManager.deleteAlias(alias);
        Log.i(QJConstant.MI_TAG,"xiaomi alias delete ok, alias is : "+alias);
    }

    public static void deleteMiAccount(String account){
        MiPushManager.deleteAccount(account);
        Log.i(QJConstant.MI_TAG,"xiaomi account delete ok, account is : "+account);
    }

    //百度自定义
    public static void setBaiduTag(List<String> tag){
        BaiduPushManager.setTags(tag);
        Log.i(QJConstant.MI_TAG,"baidu tag set ok, tag is : "+tag.toString());
    }

    public static void deleteBaiduTag(List<String> tag){
        BaiduPushManager.deleteTags(tag);
        Log.i(QJConstant.MI_TAG,"baidu delete set ok, tag is : "+tag.toString());
    }

    public static void listBaiduTag(){
        BaiduPushManager.listTags();
    }

    //华为自定义
    /**
     * @return 华为client
     */
    public static HuaweiApiClient getHuaweiClient(){
        return HuaweiPushManager.getClient();
    }

    /**
     * 获取华为的token
     */
    public static void getHuaweiToken(){
        HuaweiPushManager.getToken();
    }
}
