package com.qiaojim.qjpushservice.entity;

import java.io.Serializable;

/**
 * Created by QiaoJim on 2017/8/28.
 */

public class QJMessage implements Serializable{

    private String romType; //rom型号 Xiaomi Baidu Huawei
    private String msgType; //推送消息类型
    private String title;   //通知栏消息推送标题，透传为null
    private String body;    //通知栏消息内容，透传唯消息一有效值
    private Object extra;   //通知栏消息附加key-value字段，透传null

    public String getRomType() {
        return romType;
    }

    public void setRomType(String romType) {
        this.romType = romType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
