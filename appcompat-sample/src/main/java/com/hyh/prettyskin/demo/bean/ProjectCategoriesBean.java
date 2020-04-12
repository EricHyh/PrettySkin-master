package com.hyh.prettyskin.demo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2020/4/11
 */
public class ProjectCategoriesBean {

    @SerializedName("data")
    public List<ProjectCategoryBean> projectCategories;

}
