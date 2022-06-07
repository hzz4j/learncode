package org.hzz.tongxin.vo;

public final class MyMessage {
    private MsgHeader msgHeader;

    private Object body;

    public final MsgHeader getMyHeader() {
        return msgHeader;
    }

    public final void setMyHeader(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MyMessage [myHeader=" + msgHeader + "][body="+body+"]";
    }
}
