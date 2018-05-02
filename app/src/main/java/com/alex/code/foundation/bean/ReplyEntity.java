package com.alex.code.foundation.bean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/2.
 */

public class ReplyEntity {

    /**
     * id : 11
     * content : 啦咯啦咯啦咯啦
     * add_time : 2017-11-02 10:39:15
     * use_id : 0
     * name : null
     * user_headimg : null
     */

    private int id;
    private String content;
    private String add_time;
    private int    use_id;
    private String name;
    private String user_headimg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_headimg() {
        return user_headimg;
    }

    public void setUser_headimg(String user_headimg) {
        this.user_headimg = user_headimg;
    }
}
