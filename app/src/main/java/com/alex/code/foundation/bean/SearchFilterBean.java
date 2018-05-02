package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/14.
 */

public class SearchFilterBean {


    /**
     * search : [{"name":"品牌","value":["苹果3"]},{"name":"尺寸","value":["5寸","6.5寸"]},{"name":"材质","value":["合金"]},{"name":"风格","value":["好看"]}]
     * debug_uid : 301
     * debug_way : mobile
     * session : []
     */

    private List<SearchEntity> search;

    public List<SearchEntity> getSearch() {
        return search;
    }

    public void setSearch(List<SearchEntity> search) {
        this.search = search;
    }

    public static class SearchEntity {
        /**
         * name : 品牌
         * key  : brand
         * value : ["苹果3"]
         */

        private String name;
        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
