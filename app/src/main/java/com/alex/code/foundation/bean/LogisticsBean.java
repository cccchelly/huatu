package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/10.
 */

public class LogisticsBean {


    /**
     * message : ok
     * nu : 289125631549
     * ischeck : 1
     * com : shunfeng
     * status : 200
     * data : [{"time":"2017-10-09 09:37:42","context":"已签收(天天购超市 ),感谢使用顺丰,期待再次为您服务","ftime":"2017-10-09 09:37:42","areaCode":null,"areaName":null,"status":"签收"},{"time":"2017-10-09 07:39:25","context":"快件交给王国未，正在派送途中（联系电话：18628335180）","ftime":"2017-10-09 07:39:25","areaCode":null,"areaName":null,"status":"派件"},{"time":"2017-10-09 06:41:22","context":"快件到达 【成都市高新区新港国际营业点】","ftime":"2017-10-09 06:41:22","areaCode":"CN510703006000","areaName":"四川,绵阳市,涪城区,高新","status":"在途"},{"time":"2017-10-09 04:33:49","context":"快件在【成都双流集散中心】装车，已发往 【成都市高新区新港国际营业点】","ftime":"2017-10-09 04:33:49","areaCode":null,"areaName":null,"status":"在途"},{"time":"2017-10-08 21:50:04","context":"快件到达 【成都双流集散中心】","ftime":"2017-10-08 21:50:04","areaCode":"CN510122000000","areaName":"四川,成都市,双流县","status":"在途"},{"time":"2017-10-08 18:24:44","context":"快件在【成都腾飞营业部】装车，已发往 【成都双流集散中心】","ftime":"2017-10-08 18:24:44","areaCode":null,"areaName":null,"status":"在途"},{"time":"2017-10-08 18:24:34","context":"顺丰速运 已收取快件","ftime":"2017-10-08 18:24:34","areaCode":null,"areaName":null,"status":"揽收"},{"time":"2017-10-08 17:26:38","context":"您的订单己出库","ftime":"2017-10-08 17:26:38","areaCode":null,"areaName":null,"status":"在途"},{"time":"2017-10-08 17:26:31","context":"您的订单已经打包完毕,准备出库.","ftime":"2017-10-08 17:26:31","areaCode":null,"areaName":null,"status":"在途"},{"time":"2017-10-08 16:56:08","context":"您的订单已拣货完成","ftime":"2017-10-08 16:56:08","areaCode":null,"areaName":null,"status":"在途"},{"time":"2017-10-08 15:41:55","context":"您的订单正准备拣货","ftime":"2017-10-08 15:41:55","areaCode":null,"areaName":null,"status":"在途"},{"time":"2017-10-08 14:52:12","context":"您的订单已审核","ftime":"2017-10-08 14:52:12","areaCode":null,"areaName":null,"status":"揽收"}]
     * state : 3
     * condition : D01
     * num :
     */

    private String message;
    private String           nu;
    private String           ischeck;
    private String           com;
    private String           status;
    private String           state;
    private String           condition;
    private String           num;
    private List<DataEntity> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * time : 2017-10-09 09:37:42
         * context : 已签收(天天购超市 ),感谢使用顺丰,期待再次为您服务
         * ftime : 2017-10-09 09:37:42
         * areaCode : null
         * areaName : null
         * status : 签收
         */

        private String time;
        private String context;
        private String ftime;
        private Object areaCode;
        private Object areaName;
        private String status;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public Object getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(Object areaCode) {
            this.areaCode = areaCode;
        }

        public Object getAreaName() {
            return areaName;
        }

        public void setAreaName(Object areaName) {
            this.areaName = areaName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
