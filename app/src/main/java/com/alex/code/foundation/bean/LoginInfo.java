package com.alex.code.foundation.bean;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/10.
 */

public class LoginInfo {


    /**
     * id : 300
     * is_system : 0
     * is_member : 1
     * instance_id : 0
     * instance_name : 天使有家
     * token : 64a8d7c34b5c8c14cff03205e2d9dcbc
     */

    private int id;
    private int    is_system;
    private int    is_member;
    private int    instance_id;
    private String instance_name;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_system() {
        return is_system;
    }

    public void setIs_system(int is_system) {
        this.is_system = is_system;
    }

    public int getIs_member() {
        return is_member;
    }

    public void setIs_member(int is_member) {
        this.is_member = is_member;
    }

    public int getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(int instance_id) {
        this.instance_id = instance_id;
    }

    public String getInstance_name() {
        return instance_name;
    }

    public void setInstance_name(String instance_name) {
        this.instance_name = instance_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
