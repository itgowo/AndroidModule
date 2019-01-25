package com.itgowo.module.demo;

import java.io.Serializable;

public class MsgEntity implements Serializable {
    private String content;
    private String name;
    private String timeLine;
    private long time;
    private String image;
    private int userid;

    public boolean isMe() {
        if (userid == 10086) {
            return true;
        }
        return false;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public MsgEntity setTimeLine(String timeLine) {
        this.timeLine = timeLine;
        return this;
    }

    public int getUserid() {
        return userid;
    }

    public MsgEntity setUserid(int userid) {
        this.userid = userid;
        return this;
    }

    public long getTime() {
        return time;
    }

    public MsgEntity setTime(long time) {
        this.time = time;
        return this;
    }

    public String getImage() {
        return image;
    }

    public MsgEntity setImage(String image) {
        this.image = image;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MsgEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public String getName() {
        return name;
    }

    public MsgEntity setName(String name) {
        this.name = name;
        return this;
    }
}
