package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/1.
 */

public class CommentDetailBean {


    /**
     * content : 物美价廉
     * addtime : 2017-10-17 17:39:07
     * explain_first :
     * member_name : 一个忠实的用户
     * user_id : 337
     * again_content :
     * again_addtime : 2017-10-17 17:39:07
     * again_image : [{"pic_cover":"upload/advertising/1497069876.png"},{"pic_cover":"upload/advertising/1497069865.png"}]
     * again_explain :
     * image : [{"pic_cover":"upload/advertising/1497069865.png"},{"pic_cover":"upload/advertising/1497069876.png"}]
     * user_headimg : null
     * reply : [{"content":"价格低","add_time":"1970-01-01 00:00:00","use_id":337,"name":"180****101","user_headimg":"test/img/avatar.png"},{"content":"就是啊！还实用","add_time":"1970-01-30 00:00:10","use_id":339,"name":"177****079","user_headimg":""},{"content":"你买的是什么","add_time":"1970-02-01 00:00:00","use_id":337,"name":"180****101","user_headimg":"test/img/avatar.png"}]
     */

    private String content;
    private String                 addtime;
    private String                 explain_first;
    private String                 member_name;
    private int                    user_id;
    private String                 again_content;
    private String                 again_addtime;
    private String                 again_explain;
    private String                 user_headimg;
    private List<AgainImageEntity> again_image;
    private List<ImageEntity>      images;
    private List<ReplyEntity>      reply;
    private String goods_spe;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public List<ImageEntity> getImage() {
        return images;
    }

    public void setImage(List<ImageEntity> images) {
        this.images = images;
    }

    public List<ReplyEntity> getReply() {
        return reply;
    }

    public void setReply(List<ReplyEntity> reply) {
        this.reply = reply;
    }

    public String getGoods_spe() {
        return goods_spe;
    }

    public void setGoods_spe(String goods_spe) {
        this.goods_spe = goods_spe;
    }

    public static class AgainImageEntity {
        /**
         * pic_cover : upload/advertising/1497069876.png
         */

        private String pic_cover;

        public String getPic_cover() {
            return pic_cover;
        }

        public void setPic_cover(String pic_cover) {
            this.pic_cover = pic_cover;
        }
    }

    public static class ImageEntity {
        /**
         * pic_cover : upload/advertising/1497069865.png
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
