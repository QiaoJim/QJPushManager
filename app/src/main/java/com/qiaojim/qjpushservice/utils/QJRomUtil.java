package com.qiaojim.qjpushservice.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.qiaojim.qjpushservice.entity.QJConstant;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.qiaojim.qjpushservice.utils.QJRomUtil.ROM_TYPE.Baidu;
import static com.qiaojim.qjpushservice.utils.QJRomUtil.ROM_TYPE.Huawei;
import static com.qiaojim.qjpushservice.utils.QJRomUtil.ROM_TYPE.Mi;

/**
 * Created by QiaoJim on 2017/6/8.
 */

public class QJRomUtil {

    //用户实际rom，初始值设为其他类型（即使用百度推送）
    private static QJRomUtil.ROM_TYPE userType = QJRomUtil.ROM_TYPE.Baidu;
    //用户IMEI
    private static String userIMEI = null;

    /**
     * 准备  设置IMEI，和用户ROM，必须最先调用
     *
     * @param context
     */
    public static void prepare(Context context) {

        QJBuildProperties.loadProperties();
        setIMEI(context);
        setRomType();
        Log.i(QJConstant.ROM_TAG, "rom prepare finish");
    }

    /**
     * 初始化用户手机类别
     */
    public static void setRomType() {

        if (isMi()) {
            userType = Mi;
        } else if (isHuawei()) {
            userType = Huawei;
        } else {
            userType = Baidu;
        }
    }

    /**
     * 获取设备类型
     *
     * @return 设备类型：xiaomi huawei baidu
     */
    public static ROM_TYPE getRomType() {
        return userType;
    }

    /**
     * 获得设备的IMEI号，并初始化其值
     *
     * @param context
     */
    @SuppressLint("MissingPermission")
    public static void setIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm != null) {
            userIMEI = tm.getDeviceId();
        } else {
            userIMEI = null;
        }
    }

    /**
     * @return 用户设备IMEI号
     */
    public static String getIMEI() {
        return userIMEI;
    }

    // 判断是否是mi
    public static boolean isMi() {
        if (QJBuildProperties.containsKey(KEY_MIUI_VERSION_NAME)
                || QJBuildProperties.containsKey(KEY_MIUI_INTERNAL_STORAGE)
                || QJBuildProperties.containsKey(KEY_MIUI_VERSION_CODE)) {
            return true;
        }
        return false;
    }

    //判断是否是华为
    public static boolean isHuawei() {
        if (QJBuildProperties.containsKey(KEY_EMUI_API_LEVEL)
                || QJBuildProperties.containsKey(KEY_EMUI_CONFIG_HW_SYS_VERSION)
                || QJBuildProperties.containsKey(KEY_EMUI_VERSION_CODE)) {
            return true;
        }
        return false;
    }

    //小米配置项
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    //华为配置项
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    public enum ROM_TYPE {Mi, Huawei, Baidu}
}
