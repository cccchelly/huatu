package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/30.
 */

public class CommentBean {


    private List<EvalListEntity> eval_list;
    private int total_count;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<EvalListEntity> getEval_list() {
        return eval_list;
    }

    public void setEval_list(List<EvalListEntity> eval_list) {
        this.eval_list = eval_list;
    }



    public static class EvalListEntity {
        /**
         * content : 物美价廉
         * addtime : 2017-10-17 17:39:07
         * member_name : 一个忠实的用户
         * images : [{"pic_cover":"upload/advertising/1497069865.png"},{"pic_cover":"upload/advertising/1497069876.png"}]
         * goods_spe : [{"attr_value":"颜色","attr_value_name":"红色"}]
         * user_headimg : null
         */

        private String id;
        private String content;
        private String               addtime;
        private String               member_name;
        private String               user_headimg;
        private List<ImagesEntity>   images;
        private String goods_spe;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getUser_headimg() {
            return user_headimg;
        }

        public void setUser_headimg(String user_headimg) {
            this.user_headimg = user_headimg;
        }

        public List<ImagesEntity> getImages() {
            return images;
        }

        public void setImages(List<ImagesEntity> images) {
            this.images = images;
        }

        public String getGoods_spe() {
            return goods_spe;
        }

        public void setGoods_spe(String goods_spe) {
            this.goods_spe = goods_spe;
        }

//        public static class ImagesEntity {
//            /**
//             * pic_cover : upload/advertising/1497069865.png
//             */
//
//            private String pic_cover;
//
//            public String getPic_cover() {
//                return pic_cover;
//            }
//
//            public void setPic_cover(String pic_cover) {
//                this.pic_cover = pic_cover;
//            }
//        }

    }


}
