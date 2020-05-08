package com.hyh.prettyskin.demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProjectCategoryBean implements Parcelable {

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

    public ProjectCategoryBean() {
    }

    protected ProjectCategoryBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<ProjectCategoryBean> CREATOR = new Creator<ProjectCategoryBean>() {
        @Override
        public ProjectCategoryBean createFromParcel(Parcel in) {
            return new ProjectCategoryBean(in);
        }

        @Override
        public ProjectCategoryBean[] newArray(int size) {
            return new ProjectCategoryBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}