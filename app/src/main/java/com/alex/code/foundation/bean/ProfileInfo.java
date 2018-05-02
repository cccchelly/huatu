package com.alex.code.foundation.bean;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/19.
 */

public class ProfileInfo {


    /**
     * userinfo : {"avatar":"0","nick_name":"3310","sex":0,"birthday":"1970-01-01"}
     */

    private UserinfoEntity userinfo;

    public UserinfoEntity getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoEntity userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoEntity {
        /**
         * avatar : 0
         * nick_name : 3310
         * sex : 0
         * birthday : 1970-01-01
         */

        private String avatar;
        private String nick_name;
        private int    sex;
        private String birthday;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }
}
