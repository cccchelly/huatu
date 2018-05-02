package com.alex.code.foundation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/28.
 */

public class ExpressEntity implements Parcelable{
    /**
     * "express_name":"顺丰",
     "express_no":"123456",
     "express_code":"SF",
     "shipping_time":"2017-11-23 14:42:44",
     "from_area":"成都",
     "to_area":"北京"
     */

    private String express_name;
    private String express_no;
    private String express_code;
    private String shipping_time;
    private String from_area;
    private String to_area;


    protected ExpressEntity(Parcel in) {
        express_name = in.readString();
        express_no = in.readString();
        express_code = in.readString();
        shipping_time = in.readString();
        from_area = in.readString();
        to_area = in.readString();
    }

    public static final Creator<ExpressEntity> CREATOR = new Creator<ExpressEntity>() {
        @Override
        public ExpressEntity createFromParcel(Parcel in) {
            return new ExpressEntity(in);
        }

        @Override
        public ExpressEntity[] newArray(int size) {
            return new ExpressEntity[size];
        }
    };

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public String getExpress_code() {
        return express_code;
    }

    public void setExpress_code(String express_code) {
        this.express_code = express_code;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getFrom_area() {
        return from_area;
    }

    public void setFrom_area(String from_area) {
        this.from_area = from_area;
    }

    public String getTo_area() {
        return to_area;
    }

    public void setTo_area(String to_area) {
        this.to_area = to_area;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(express_name);
        dest.writeString(express_no);
        dest.writeString(express_code);
        dest.writeString(shipping_time);
        dest.writeString(from_area);
        dest.writeString(to_area);
    }
}
