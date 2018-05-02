package com.alex.code.foundation.bean;

import java.io.Serializable;

/**
 * Created by dth
 * Des:
 * Date: 2017-12-28.
 */

public class UMsg implements Serializable{

    private String date;//推送时间
    private String title;//推送标题
    private String content;//推送内容
    private String appUrl;//推送定向跳转链接

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
