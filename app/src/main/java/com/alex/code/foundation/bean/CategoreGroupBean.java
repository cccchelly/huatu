package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class CategoreGroupBean {


    /**
     * id : 323
     * category_name : 鞋类
     * short_name : 女鞋，男鞋
     * pid : 0
     * level : 1
     * is_visible : 1
     * attr_id : 0
     * attr_name :
     * keywords :
     * description :
     * sort : 0
     * category_pic : upload/common/1507779157.jpg
     * is_parent : 1
     * child_list : [{"id":324,"category_name":"女鞋","short_name":"女鞋","pid":323,"level":2,"is_visible":1,"attr_id":0,"attr_name":"","keywords":"","description":"","sort":1,"category_pic":"upload/common/1507779157.jpg","is_parent":0},{"id":325,"category_name":"鞋类","short_name":"男鞋","pid":323,"level":2,"is_visible":1,"attr_id":0,"attr_name":"","keywords":"","description":"","sort":2,"category_pic":"upload/common/1507779157.jpg","is_parent":0}]
     */

    private int id;
    private String                category_name;
    private String                short_name;
    private int                   pid;
    private int                   level;
    private int                   is_visible;
    private int                   attr_id;
    private String                attr_name;
    private String                keywords;
    private String                description;
    private int                   sort;
    private String                category_pic;
    private int                   is_parent;
    private List<CategroeChildBean> child_list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIs_visible() {
        return is_visible;
    }

    public void setIs_visible(int is_visible) {
        this.is_visible = is_visible;
    }

    public int getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(int attr_id) {
        this.attr_id = attr_id;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCategory_pic() {
        return category_pic;
    }

    public void setCategory_pic(String category_pic) {
        this.category_pic = category_pic;
    }

    public int getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(int is_parent) {
        this.is_parent = is_parent;
    }

    public List<CategroeChildBean> getChild_list() {
        return child_list;
    }

    public void setChild_list(List<CategroeChildBean> child_list) {
        this.child_list = child_list;
    }
}
