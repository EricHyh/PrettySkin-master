package com.hyh.prettyskin.demo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectsBean {

    @SerializedName("data")
    public DataBean data;

    public static class DataBean {

        @SerializedName("curPage")
        public int curPage;

        @SerializedName("pageCount")
        public int pageCount;

        @SerializedName("size")
        public int size;

        @SerializedName("total")
        public int total;

        @SerializedName("datas")
        public List<ProjectBean> projects;

    }
}