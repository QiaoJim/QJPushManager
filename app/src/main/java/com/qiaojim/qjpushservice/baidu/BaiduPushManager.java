package com.qiaojim.qjpushservice.baidu;

import android.content.Context;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.QJPushManager;

import java.util.List;

/**
 * Created by QiaoJim on 2017/6/8.
 */

public class BaiduPushManager {

    //app上下文
    private static Context context = QJPushManager.getContext();

    //百度申请得到的api key
    private static String BaiduPushApiKey=null;

    /**
     * 准备，必须最先调用
     * @param key 申请的api key
     */
    public static void prepare(String key){
        BaiduPushApiKey=key;
    }

    /**
     * 开启百度推送服务
     */
    public static void startBaiduPush(){
        if (null == BaiduPushApiKey){
            Log.e(QJConstant.BAIDU_TAG,"baidu api key not set");
            return;
        }
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, BaiduPushApiKey);
        Log.i(QJConstant.BAIDU_TAG,"baidu push start finished");
    }

    /**
     * 注销百度推送服务
     */
    public static void stopBaiduPush(){
        PushManager.stopWork(context);
        Log.i(QJConstant.BAIDU_TAG,"baidu push stop finished");
    }

    /**
     * 设置订阅标签
     * @param tags
     */
    public static void setTags(List<String> tags){
        PushManager.setTags(context,tags);
    }

    /**
     * 删除标签
     * @param tags
     */
    public static void deleteTags(List<String> tags){
        PushManager.delTags(context,tags);
    }

    /**
     * 列出所有标签
     */
    public static void listTags(){
        PushManager.listTags(context);
    }
}
