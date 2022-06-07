package org.hzz.tongxin.vo;

import java.util.HashMap;
import java.util.Map;

public final class MsgHeader {
    /*消息体的MD5摘要*/
    private String md5;

    /*消息的ID，因为是同步处理模式，不考虑应答消息需要填入请求消息ID*/
    private long msgID;

    /*消息类型*/
    private byte type;

    /*消息优先级*/
    private byte priority;

    /*消息头额外附件*/
    private Map<String, Object> attachment = new HashMap<String, Object>();

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public final long getMsgID() {
        return msgID;
    }

    public final void setMsgID(long msgID) {
        this.msgID = msgID;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "MyHeader [md5=" + md5
                + ", msgID=" + msgID
                + ", type=" + type
                + ", priority=" + priority
                + ", attachment=" + attachment + "]";
    }
}
