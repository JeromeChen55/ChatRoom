package com.chen.qqcommon;

import java.io.Serializable;

/**
 * @author Chen
 * @version 1.0
 * @date
 * The message object between Client and Server communication
 */
public class Message implements Serializable {

    private  static final long serialVersionUID = 1L;

    private String sender;
    private String getter;
    private String content;
    private String sendTime;
    private String mesType;

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
