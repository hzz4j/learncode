package org.hzz.workqueue;

public class SMS {
    private String name;
    private String mobile;
    private String content;

    public SMS(String name, String mobile, String content) {
        this.name = name;
        this.mobile = mobile;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SMS{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
