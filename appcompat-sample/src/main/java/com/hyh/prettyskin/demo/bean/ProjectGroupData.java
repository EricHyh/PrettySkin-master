package com.hyh.prettyskin.demo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2020/4/11
 */
public class ProjectGroupData {

    @SerializedName("data")
    public List<Project> projects;


    public static class Project {

        /**
         * "courseId": 13,
         * "id": 294,
         * "name": "完整项目",
         * "order": 145000,
         * "parentChapterId": 293,
         * "userControlSetTop": false,
         * "visible": 0
         */

        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;

    }
}
