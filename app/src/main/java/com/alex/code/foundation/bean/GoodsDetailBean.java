package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/30.
 */

public class GoodsDetailBean {


    /**
     * goods_detail : {"id":"407","goods_name":"测试商品","price":"1000.00","promotion_price":"1000.00","sales":800,"introduction":"真心适宜沙发","evaluates":0,"shop_site":"江西景德镇","img_list":[{"pic_cover":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg"},{"pic_cover":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg"},{"pic_cover":"upload/goods/dd52fbd678be1837f6932d433db5a93b.jpg"},{"pic_cover":"upload/goods/659d38cbc61180ceb813d048743db51e.jpg"}],"evaluates_info":{"content":"物美价廉","addtime":null,"explain_first":"","member_name":"","again_content":"","again_addtime":null,"again_image":[{"pic_cover":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg"},{"pic_cover":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg"}],"again_explain":"","image":[{"pic_cover":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg"},{"pic_cover":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg"}],"user_headimg":"test/img/avatar.png","goods_spe":[{"attr_value":"颜色","attr_value_name":"红色"}]}}
     * evaluates_count : 3
     * url : goods/particulars
     */

    private GoodsDetailEntity goods_detail;
    private int    evaluates_count;
    private String url;
    private GoodsRecommend goods_list_tj;
    private ShopInfo shop_info;

    public ShopInfo getShop_info() {
        return shop_info;
    }

    public void setShop_info(ShopInfo shop_info) {
        this.shop_info = shop_info;
    }

    public GoodsRecommend getGoods_list_tj() {
        return goods_list_tj;
    }

    public void setGoods_list_tj(GoodsRecommend goods_list_tj) {
        this.goods_list_tj = goods_list_tj;
    }

    public GoodsDetailEntity getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(GoodsDetailEntity goods_detail) {
        this.goods_detail = goods_detail;
    }

    public int getEvaluates_count() {
        return evaluates_count;
    }

    public void setEvaluates_count(int evaluates_count) {
        this.evaluates_count = evaluates_count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class GoodsDetailEntity {
        /**
         * id : 407
         * shop_id :
         * goods_name : 测试商品
         * price : 1000.00
         * promotion_price : 1000.00
         * sales : 800
         * introduction : 真心适宜沙发
         * evaluates : 0
         * favorite : 1
         * "goods_type": 0,
         * "goods_time": 0,
         * share:
         * shop_qq:
         * share_url : "http://jht.meishifulu.cn/goods/goodsinfo?goodsid=4"
         * shop_site : 江西景德镇
         * img_list : [{"pic_cover":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg"},{"pic_cover":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg"},{"pic_cover":"upload/goods/dd52fbd678be1837f6932d433db5a93b.jpg"},{"pic_cover":"upload/goods/659d38cbc61180ceb813d048743db51e.jpg"}]
         * evaluates_info : {"content":"物美价廉","addtime":null,"explain_first":"","member_name":"","again_content":"","again_addtime":null,"again_image":[{"pic_cover":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg"},{"pic_cover":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg"}],"again_explain":"","image":[{"pic_cover":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg"},{"pic_cover":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg"}],"user_headimg":"test/img/avatar.png","goods_spe":[{"attr_value":"颜色","attr_value_name":"红色"}]}
         */

        private String id;
        private String shop_id;
        private String              goods_name;
        private double              price;
        private double              promotion_price;
        private String              sales;
        private String              introduction;
        private String              shipping_fee;
        private int                 evaluates;
        private int                 favorite;
        private int                 goods_type;
        private long                goods_time;
        private String              shop_site;
        private String              share;
        private String              shop_qq;
        private String              share_url;
        private EvaluatesInfoEntity evaluates_info;
        private List<ImgListEntity> img_list;
        private String mansong_name;

        public String getMansong_name() {
            return mansong_name;
        }

        public void setMansong_name(String mansong_name) {
            this.mansong_name = mansong_name;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public long getGoods_time() {
            return goods_time;
        }

        public void setGoods_time(long goods_time) {
            this.goods_time = goods_time;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public String getShop_qq() {
            return shop_qq;
        }

        public void setShop_qq(String shop_qq) {
            this.shop_qq = shop_qq;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShipping_fee() {
            return shipping_fee;
        }

        public void setShipping_fee(String shipping_fee) {
            this.shipping_fee = shipping_fee;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(double promotion_price) {
            this.promotion_price = promotion_price;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public int getEvaluates() {
            return evaluates;
        }

        public void setEvaluates(int evaluates) {
            this.evaluates = evaluates;
        }

        public String getShop_site() {
            return shop_site;
        }

        public void setShop_site(String shop_site) {
            this.shop_site = shop_site;
        }

        public EvaluatesInfoEntity getEvaluates_info() {
            return evaluates_info;
        }

        public void setEvaluates_info(EvaluatesInfoEntity evaluates_info) {
            this.evaluates_info = evaluates_info;
        }

        public List<ImgListEntity> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<ImgListEntity> img_list) {
            this.img_list = img_list;
        }

        public static class EvaluatesInfoEntity {
            /**
             * content : 物美价廉
             * type : 0
             * addtime : null
             * explain_first :
             * member_name :
             * again_content :
             * again_addtime : null
             * again_image : [{"pic_cover":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg"},{"pic_cover":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg"}]
             * again_explain :
             * image : [{"pic_cover":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg"},{"pic_cover":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg"}]
             * user_headimg : test/img/avatar.png
             * goods_spe : [{"attr_value":"颜色","attr_value_name":"红色"}]
             */

            private String content;
            private String                 addtime;
            private String                 explain_first;
            private String                 member_name;
            private String                 again_content;
            private String                 again_addtime;
            private String                 again_explain;
            private String                 user_headimg;
            private List<AgainImageEntity> again_image;
            private List<ImagesEntity>      image;
            private String   goods_spe;
            private int   type;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getExplain_first() {
                return explain_first;
            }

            public void setExplain_first(String explain_first) {
                this.explain_first = explain_first;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getAgain_content() {
                return again_content;
            }

            public void setAgain_content(String again_content) {
                this.again_content = again_content;
            }

            public String getAgain_addtime() {
                return again_addtime;
            }

            public void setAgain_addtime(String again_addtime) {
                this.again_addtime = again_addtime;
            }

            public String getAgain_explain() {
                return again_explain;
            }

            public void setAgain_explain(String again_explain) {
                this.again_explain = again_explain;
            }

            public String getUser_headimg() {
                return user_headimg;
            }

            public void setUser_headimg(String user_headimg) {
                this.user_headimg = user_headimg;
            }

            public List<AgainImageEntity> getAgain_image() {
                return again_image;
            }

            public void setAgain_image(List<AgainImageEntity> again_image) {
                this.again_image = again_image;
            }

            public List<ImagesEntity> getImage() {
                return image;
            }

            public void setImage(List<ImagesEntity> image) {
                this.image = image;
            }

            public String getGoods_spe() {
                return goods_spe;
            }

            public void setGoods_spe(String goods_spe) {
                this.goods_spe = goods_spe;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public static class AgainImageEntity {
                /**
                 * pic_cover : upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg
                 */

                private String pic_cover;

                public String getPic_cover() {
                    return pic_cover;
                }

                public void setPic_cover(String pic_cover) {
                    this.pic_cover = pic_cover;
                }
            }

//            public static class ImageEntity {
//                /**
//                 * pic_cover : upload/goods/bea0252521c548e72afb17c009d9c715.jpg
//                 */
//
//                private String pic_cover;
//
//                public String getPic_cover() {
//                    return pic_cover;
//                }
//
//                public void setPic_cover(String pic_cover) {
//                    this.pic_cover = pic_cover;
//                }
//            }

        }

        public static class ImgListEntity {
            /**
             * pic_cover : upload/goods/bea0252521c548e72afb17c009d9c715.jpg
             */

            private String pic_cover;

            public String getPic_cover() {
                return pic_cover;
            }

            public void setPic_cover(String pic_cover) {
                this.pic_cover = pic_cover;
            }
        }
    }

    public static class GoodsRecommend{

        private List<ADGoods> rec_goods;//推荐商品
        private List<ADGoods> sen_goods;//排行

        public List<ADGoods> getRec_goods() {
            return rec_goods;
        }

        public void setRec_goods(List<ADGoods> rec_goods) {
            this.rec_goods = rec_goods;
        }

        public List<ADGoods> getSen_goods() {
            return sen_goods;
        }

        public void setSen_goods(List<ADGoods> sen_goods) {
            this.sen_goods = sen_goods;
        }

        public static class ADGoods{

            /**
             * "goods_id": 7,
             "goods_name": "SIEMENS/西门子XQG80-WM12P2R88W 8KG滚筒洗衣机 1200转变频",
             "pic_cover": "upload/common/20171127001.jpg",
             "promotion_price": "59.42"
             */

            private String goods_id;
            private String goods_name;
            private String pic_cover;
            private String promotion_price;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getPic_cover() {
                return pic_cover;
            }

            public void setPic_cover(String pic_cover) {
                this.pic_cover = pic_cover;
            }

            public String getPromotion_price() {
                return promotion_price;
            }

            public void setPromotion_price(String promotion_price) {
                this.promotion_price = promotion_price;
            }
        }
    }

    public static class ShopInfo{
        /**
         * "shop_id":1,
         "shop_avatar": "template/shop/default/public/images/temp_14653489106946.jpg",
         "shop_name": "苹果专卖店",
         "shop_qq": 291433591,
         "shop_atte": 2,
         "grade_wu": 3.25,
         "goods_gra": 1.75,
         "goods_number": 3,
         "shop_syn": 2.5
         */

        public String shop_id;
        public String shop_avatar;
        public String shop_name;
        public String shop_qq;
        public String shop_atte;
        public String grade_wu;
        public String goods_gra;
        public String shop_gra;
        public String goods_number;
        public String shop_syn;


        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_avatar() {
            return shop_avatar;
        }

        public void setShop_avatar(String shop_avatar) {
            this.shop_avatar = shop_avatar;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_qq() {
            return shop_qq;
        }

        public void setShop_qq(String shop_qq) {
            this.shop_qq = shop_qq;
        }

        public String getShop_atte() {
            return shop_atte;
        }

        public void setShop_atte(String shop_atte) {
            this.shop_atte = shop_atte;
        }

        public String getGrade_wu() {
            return grade_wu;
        }

        public void setGrade_wu(String grade_wu) {
            this.grade_wu = grade_wu;
        }

        public String getGoods_gra() {
            return goods_gra;
        }

        public void setGoods_gra(String goods_gra) {
            this.goods_gra = goods_gra;
        }

        public String getShop_gra() {
            return shop_gra;
        }

        public void setShop_gra(String shop_gra) {
            this.shop_gra = shop_gra;
        }

        public String getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(String goods_number) {
            this.goods_number = goods_number;
        }

        public String getShop_syn() {
            return shop_syn;
        }

        public void setShop_syn(String shop_syn) {
            this.shop_syn = shop_syn;
        }
    }
}
