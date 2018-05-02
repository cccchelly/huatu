package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/1.
 */

public class CommentTypeBean {

    private List<EvalCountEntity> eval_count;

    public List<EvalCountEntity> getEval_count() {
        return eval_count;
    }

    public void setEval_count(List<EvalCountEntity> eval_count) {
        this.eval_count = eval_count;
    }

    public static class EvalCountEntity {
        /**
         * name : 全部
         * value : 1
         * count : 3
         */

        private String name;
        private int value;
        private int count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
