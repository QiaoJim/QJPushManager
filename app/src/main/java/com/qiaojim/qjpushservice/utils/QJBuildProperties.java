package com.qiaojim.qjpushservice.utils;

import android.os.Environment;
import android.util.Log;

import com.qiaojim.qjpushservice.entity.QJConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by QiaoJim on 2017/6/8.
 */

public class QJBuildProperties {

    private static Properties properties = null;

    /**
     * 读取用户设备的配置信息
     */
    public static void loadProperties() {
        try {
            properties=new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(QJConstant.ROM_TAG,"load build.prop fail");
        }

        if (properties==null){
            Log.e(QJConstant.ROM_TAG,"loadProperties, properties is null");
        }else {
            Log.i(QJConstant.ROM_TAG,"loadProperties, properties init ok");
        }
    }

    /**
     * 判断是否包含特定配置项，以区分不同的手机类型
     * @param key  配置项字符串
     * @return true-包含
     */
    public static boolean containsKey(final Object key) {
        return properties.containsKey(key);
    }

    public static boolean containsValue(final Object value) {
        return properties.containsValue(value);
    }

    public static String getProperty(final String name) {
        return properties.getProperty(name);
    }
}
