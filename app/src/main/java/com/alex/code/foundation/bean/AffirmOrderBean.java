package com.alex.code.foundation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/14.
 */

public class AffirmOrderBean implements Parcelable{


    /**
     * address : {"address":"故宫","address_info":"北京市北京市东城区故宫","alias":"","city":"北京市","consigner":"李白","district":"东城区","id":185,"is_default":1,"mobile":"18800001111","phone":"","province":"北京市","uid":301,"zip_code":""}
     * coupon : 0
     * data : [{"list":[{"buyer_id":301,"goods_id":409,"goods_name":"新手套装","id":23,"introduction":"","num":4,"pic_cover_micro":"upload/goods/e718c76adcc310e285d80147dc932c554.jpg","pic_cover_mid":"upload/goods/e718c76adcc310e285d80147dc932c552.jpg","price":"50.00","shop_id":52,"shop_name":"银川店铺","sku_id":0,"spec_name":"颜色:红色;大小:60cm*60cm"}],"shop_id":52,"shop_name":"银川商铺"}]
     * debug_uid : 301
     * debug_way : mobile
     * session : []
     * shipping_money : 0
     */

    private AddressEntity address;
    private String           coupon;
    private double           shipping_money;
    private double           goods_money;
    private List<DataEntity> data;

    protected AffirmOrderBean(Parcel in) {
        address = in.readParcelable(AddressEntity.class.getClassLoader());
        coupon = in.readString();
        shipping_money = in.readDouble();
        goods_money = in.readDouble();
        data = in.createTypedArrayList(DataEntity.CREATOR);
    }

    public static final Creator<AffirmOrderBean> CREATOR = new Creator<AffirmOrderBean>() {
        @Override
        public AffirmOrderBean createFromParcel(Parcel in) {
            return new AffirmOrderBean(in);
        }

        @Override
        public AffirmOrderBean[] newArray(int size) {
            return new AffirmOrderBean[size];
        }
    };


    public double getGoods_money() {
        return goods_money;
    }

    public void setGoods_money(double goods_money) {
        this.goods_money = goods_money;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public double getShipping_money() {
        return shipping_money;
    }

    public void setShipping_money(double shipping_money) {
        this.shipping_money = shipping_money;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(address, flags);
        dest.writeString(coupon);
        dest.writeDouble(shipping_money);
        dest.writeDouble(goods_money);
        dest.writeTypedList(data);
    }

    public static class AddressEntity implements Parcelable{
        /**
         * address : 故宫
         * address_info : 北京市北京市东城区故宫
         * alias :
         * city : 北京市
         * consigner : 李白
         * district : 东城区
         * id : 185
         * is_default : 1
         * mobile : 18800001111
         * phone :
         * province : 北京市
         * uid : 301
         * zip_code :
         */

        private String address;
        private String address_info;
        private String alias;
        private String city;
        private String consigner;
        private String district;
        private int    id;
        private int    is_default;
        private String mobile;
        private String phone;
        private String province;
        private int    uid;
        private String zip_code;

        protected AddressEntity(Parcel in) {
            address = in.readString();
            address_info = in.readString();
            alias = in.readString();
            city = in.readString();
            consigner = in.readString();
            district = in.readString();
            id = in.readInt();
            is_default = in.readInt();
            mobile = in.readString();
            phone = in.readString();
            province = in.readString();
            uid = in.readInt();
            zip_code = in.readString();
        }

        public static final Creator<AddressEntity> CREATOR = new Creator<AddressEntity>() {
            @Override
            public AddressEntity createFromParcel(Parcel in) {
                return new AddressEntity(in);
            }

            @Override
            public AddressEntity[] newArray(int size) {
                return new AddressEntity[size];
            }
        };

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress_info() {
            return address_info;
        }

        public void setAddress_info(String address_info) {
            this.address_info = address_info;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getConsigner() {
            return consigner;
        }

        public void setConsigner(String consigner) {
            this.consigner = consigner;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
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

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(address);
            dest.writeString(address_info);
            dest.writeString(alias);
            dest.writeString(city);
            dest.writeString(consigner);
            dest.writeString(district);
            dest.writeInt(id);
            dest.writeInt(is_default);
            dest.writeString(mobile);
            dest.writeString(phone);
            dest.writeString(province);
            dest.writeInt(uid);
            dest.writeString(zip_code);
        }
    }

    public static class DataEntity implements Parcelable{
        /**
         * list : [{"buyer_id":301,"goods_id":409,"goods_name":"新手套装","id":23,"introduction":"","num":4,"pic_cover_micro":"upload/goods/e718c76adcc310e285d80147dc932c554.jpg","pic_cover_mid":"upload/goods/e718c76adcc310e285d80147dc932c552.jpg","price":"50.00","shop_id":52,"shop_name":"银川店铺","sku_id":0,"spec_name":"颜色:红色;大小:60cm*60cm"}]
         * shop_id : 52
         * shop_name : 银川商铺
         */

        private int shop_id;
        private String           shop_name;
        private List<ListEntity> list;

        protected DataEntity(Parcel in) {
            shop_id = in.readInt();
            shop_name = in.readString();
            list = in.createTypedArrayList(ListEntity.CREATOR);
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

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(shop_id);
            dest.writeString(shop_name);
            dest.writeTypedList(list);
        }

        public static class ListEntity implements Parcelable{
            /**
             * buyer_id : 301
             * goods_id : 409
             * goods_name : 新手套装
             * id : 23
             * introduction :
             * num : 4
             * pic_cover_micro : upload/goods/e718c76adcc310e285d80147dc932c554.jpg
             * pic_cover_mid : upload/goods/e718c76adcc310e285d80147dc932c552.jpg
             * price : 50.00
             * shop_id : 52
             * shop_name : 银川店铺
             * sku_id : 0
             * spec_name : 颜色:红色;大小:60cm*60cm
             */

            private int buyer_id;
            private int    goods_id;
            private String goods_name;
            private int    id;
            private String introduction;
            private int    num;
            private String pic_cover_micro;
            private String pic_cover_mid;
            private String price;
            private int    shop_id;
            private String shop_name;
            private int    sku_id;
            private String spec_name;

            protected ListEntity(Parcel in) {
                buyer_id = in.readInt();
                goods_id = in.readInt();
                goods_name = in.readString();
                id = in.readInt();
                introduction = in.readString();
                num = in.readInt();
                pic_cover_micro = in.readString();
                pic_cover_mid = in.readString();
                price = in.readString();
                shop_id = in.readInt();
                shop_name = in.readString();
                sku_id = in.readInt();
                spec_name = in.readString();
            }

            public static final Creator<ListEntity> CREATOR = new Creator<ListEntity>() {
                @Override
                public ListEntity createFromParcel(Parcel in) {
                    return new ListEntity(in);
                }

                @Override
                public ListEntity[] newArray(int size) {
                    return new ListEntity[size];
                }
            };

            public int getBuyer_id() {
                return buyer_id;
            }

            public void setBuyer_id(int buyer_id) {
                this.buyer_id = buyer_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getPic_cover_micro() {
                return pic_cover_micro;
            }

            public void setPic_cover_micro(String pic_cover_micro) {
                this.pic_cover_micro = pic_cover_micro;
            }

            public String getPic_cover_mid() {
                return pic_cover_mid;
            }

            public void setPic_cover_mid(String pic_cover_mid) {
                this.pic_cover_mid = pic_cover_mid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public String getSpec_name() {
                return spec_name;
            }

            public void setSpec_name(String spec_name) {
                this.spec_name = spec_name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(buyer_id);
                dest.writeInt(goods_id);
                dest.writeString(goods_name);
                dest.writeInt(id);
                dest.writeString(introduction);
                dest.writeInt(num);
                dest.writeString(pic_cover_micro);
                dest.writeString(pic_cover_mid);
                dest.writeString(price);
                dest.writeInt(shop_id);
                dest.writeString(shop_name);
                dest.writeInt(sku_id);
                dest.writeString(spec_name);
            }
        }
    }
}
