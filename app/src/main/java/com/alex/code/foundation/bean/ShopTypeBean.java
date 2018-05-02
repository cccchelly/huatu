package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/25.
 */

public class ShopTypeBean {


    /**
     * debug_uid : 340
     * debug_way : app
     * list : [{"group_name":"3C数码","group_sort":2,"id":3},{"group_name":"美容护理","group_sort":3,"id":4},{"group_name":"家居用品","group_sort":4,"id":5}]
     * requieLogin : false
     */

    private List<ListEntity> list;

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity {
        /**
         * group_name : 3C数码
         * group_sort : 2
         * id : 3
         */

        private String group_name;
        private int group_sort;
        private String id;

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public int getGroup_sort() {
            return group_sort;
        }

        public void setGroup_sort(int group_sort) {
            this.group_sort = group_sort;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
