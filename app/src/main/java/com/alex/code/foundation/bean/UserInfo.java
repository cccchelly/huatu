package com.alex.code.foundation.bean;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/17.
 */

public class UserInfo {


    /**
     * isSign : true
     * member_info : {"id":299,"member_name":"3310","member_level":47,"reg_time":"2017-10-10 16:26:00","memo":null,"coupon_list":[],"coupon_count":0,"point":0,"balance":0,"coin":0,"level_name":"普通会员","favorites_goods":0,"favorites_shop":0,"history":0,"avatar":"test/img/avatar.png","nick_name":"李四修改","sex":1,"birthday":"2017-10-20","mobile":"13540828857","email":"149234676@qq.com"}
     */

    private boolean isSign;
    private MemberInfoEntity member_info;

    public boolean isIsSign() {
        return isSign;
    }

    public void setIsSign(boolean isSign) {
        this.isSign = isSign;
    }

    public MemberInfoEntity getMember_info() {
        return member_info;
    }

    public void setMember_info(MemberInfoEntity member_info) {
        this.member_info = member_info;
    }

    public static class MemberInfoEntity {
        /**
         * id : 299
         * member_name : 3310
         * member_level : 47
         * reg_time : 2017-10-10 16:26:00
         * memo : null
         * coupon_list : []
         * coupon_count : 0
         * point : 0
         * balance : 0
         * coin : 0
         * level_name : 普通会员
         * favorites_goods : 0
         * favorites_shop : 0
         * history : 0
         * avatar : test/img/avatar.png
         * nick_name : 李四修改
         * sex : 1
         * birthday : 2017-10-20
         * mobile : 13540828857
         * email : 149234676@qq.com
         * "wait_pay":0,
            "wait_delivery":0,
             "wait_receive":0,
             "wait_comment":0,
            "refund_or_sale":0
         */

        private String id;
        private String member_name;
        private int    member_level;
        private String reg_time;
        private int    coupon_count;
        private int    point;
        private int    balance;
        private int    coin;
        private String level_name;
        private String favorites_goods;
        private String favorites_shop;
        private String history;
        private String avatar;
        private String nick_name;
        private String sex;
        private String birthday;
        private String mobile;
        private String email;
        private int user_email_bind;
        public String wait_pay;
        public String wait_delivery;
        public String wait_receive;
        public String wait_comment;
        public String refund_or_sale;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public int getMember_level() {
            return member_level;
        }

        public void setMember_level(int member_level) {
            this.member_level = member_level;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public int getCoupon_count() {
            return coupon_count;
        }

        public void setCoupon_count(int coupon_count) {
            this.coupon_count = coupon_count;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getFavorites_goods() {
            return favorites_goods;
        }

        public void setFavorites_goods(String favorites_goods) {
            this.favorites_goods = favorites_goods;
        }

        public String getFavorites_shop() {
            return favorites_shop;
        }

        public void setFavorites_shop(String favorites_shop) {
            this.favorites_shop = favorites_shop;
        }

        public String getHistory() {
            return history;
        }

        public void setHistory(String history) {
            this.history = history;
        }

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public int getUser_email_bind() {
            return user_email_bind;
        }

        public void setUser_email_bind(int user_email_bind) {
            this.user_email_bind = user_email_bind;
        }

        public String getWait_pay() {
            return wait_pay;
        }

        public void setWait_pay(String wait_pay) {
            this.wait_pay = wait_pay;
        }

        public String getWait_delivery() {
            return wait_delivery;
        }

        public void setWait_delivery(String wait_delivery) {
            this.wait_delivery = wait_delivery;
        }

        public String getWait_receive() {
            return wait_receive;
        }

        public void setWait_receive(String wait_receive) {
            this.wait_receive = wait_receive;
        }

        public String getWait_comment() {
            return wait_comment;
        }

        public void setWait_comment(String wait_comment) {
            this.wait_comment = wait_comment;
        }

        public String getRefund_or_sale() {
            return refund_or_sale;
        }

        public void setRefund_or_sale(String refund_or_sale) {
            this.refund_or_sale = refund_or_sale;
        }
    }
}
