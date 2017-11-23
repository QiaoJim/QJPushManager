package com.qiaojim.qjpushservice.entity;

/**
 * Created by QiaoJim on 2017/6/8.
 */

public class QJConstant {

    //日志TAG
    public static final String ROM_TAG ="QJRom";
    public static final String MI_TAG ="QJXiaomi";
    public static final String BAIDU_TAG ="QJBaidu";
    public static final String HUAWEI_TAG ="QJHuawei";

    public static final String QJMESSAGE_KEY = "QJMessage";

    //华为错误处理请求码
    public static final int REQUEST_RESOLVE_HUAWEI_ERROR = 0x1234;

    //本地广播类型参数
    public static final int PASS_THROUGH = 0x3455;
    public static final int MSG_ARRIVED = 0x3456;
    public static final int NOTIFICATION_CLICKED= 0x3457;
    public static final int ERROR = 0x3458;

    public static final String QJPUSH_BROADCAST0 = "com.qiaojim.qjpushservice.PASS_THROUGH";
    public static final String QJPUSH_BROADCAST1 = "com.qiaojim.qjpushservice.MSG_ARRIVED";
    public static final String QJPUSH_BROADCAST2 = "com.qiaojim.qjpushservice.NOTIFICATION_CLICKED";
    public static final String QJPUSH_BROADCAST3 = "com.qiaojim.qjpushservice.ERROR";

    //消息类型
    public static final String TYPE_PASS_THROUGH = "PassThrough";
    public static final String TYPE_NOTIFICATION = "Notification";
}
