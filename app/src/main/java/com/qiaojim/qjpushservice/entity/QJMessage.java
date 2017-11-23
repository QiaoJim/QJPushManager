package com.qiaojim.qjpushservice.entity;

import java.io.Serializable;

/**
 * Created by QiaoJim on 2017/8/28.
 */

public class QJMessage implements Serializable{

    private String msgType;
    private String title;
    private String body;
    private Object extra;

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
