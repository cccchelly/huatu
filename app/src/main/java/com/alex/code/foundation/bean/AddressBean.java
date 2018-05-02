package com.alex.code.foundation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/17.
 */

public class AddressBean {


    /**
     * data : [{"id":169,"uid":299,"consigner":"张三","mobile":"13540828858","phone":"","province":"四川省","city":"成都市","district":"成华区","address":"天府大道","zip_code":"","alias":"","is_default":1,"address_info":"四川省&nbsp;成都市&nbsp;成华区"}]
     * total_count : 1
     * page_count : 1
     */

    private int total_count;
    private int              page_count;
    private List<DataEntity> data;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Parcelable{
        /**
         * id : 169
         * uid : 299
         * consigner : 张三
         * mobile : 13540828858
         * phone :
         * province : 四川省
         * city : 成都市
         * district : 成华区
         * address : 天府大道
         * zip_code :
         * alias :
         * is_default : 1
         * address_info : 四川省&nbsp;成都市&nbsp;成华区
         */

        private String id;
        private int    uid;
        private String consigner;
        private String mobile;
        private String phone;
        private String province;
        private String city;
        private String district;
        private String address;
        private String zip_code;
        private String alias;
        private int    is_default;
        private String address_info;

        protected DataEntity(Parcel in) {
            id = in.readString();
            uid = in.readInt();
            consigner = in.readString();
            mobile = in.readString();
            phone = in.readString();
            province = in.readString();
            city = in.readString();
            district = in.readString();
            address = in.readString();
            zip_code = in.readString();
            alias = in.readString();
            is_default = in.readInt();
            address_info = in.readString();
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel in) {
                return new DataEntity(in);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getConsigner() {
            return consigner;
        }

        public void setConsigner(String consigner) {
            this.consigner = consigner;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public String getAddress_info() {
            return address_info;
        }

        public void setAddress_info(String address_info) {
            this.address_info = address_info;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeInt(uid);
            dest.writeString(consigner);
            dest.writeString(mobile);
            dest.writeString(phone);
            dest.writeString(province);
            dest.writeString(city);
            dest.writeString(district);
            dest.writeString(address);
            dest.writeString(zip_code);
            dest.writeString(alias);
            dest.writeInt(is_default);
            dest.writeString(address_info);
        }
    }
}
