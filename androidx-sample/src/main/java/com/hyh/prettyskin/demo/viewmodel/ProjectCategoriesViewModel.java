package com.hyh.prettyskin.demo.viewmodel;


import android.content.Context;

import com.hyh.prettyskin.demo.bean.ProjectCategoriesBean;
import com.hyh.prettyskin.demo.net.RetrofitHelper;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * @author Administrator
 * @description
 * @data 2020/4/11
 */
public class ProjectCategoriesViewModel extends ViewModel {

    private final MutableLiveData<ProjectCategoriesBean> mMutableLiveData = new MutableLiveData<>();
    private final ProjectCategoriesApi mProjectCategoriesApi;

    public ProjectCategoriesViewModel(@NonNull Context context) {
        mProjectCategoriesApi = RetrofitHelper.create(context, ProjectCategoriesApi.class);
    }

    public MutableLiveData<ProjectCategoriesBean> getMutableLiveData() {
        return mMutableLiveData;
    }

    public void loadData() {
        mProjectCategoriesApi.get().enqueue(new Callback<ProjectCategoriesBean>() {
            @Override
            public void onResponse(@NonNull Call<ProjectCategoriesBean> call, @NonNull Response<ProjectCategoriesBean> response) {
                if (response.isSuccessful()) {
                    mMutableLiveData.setValue(response.body());
                } else {
                    mMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectCategoriesBean> call, @NonNull Throwable t) {
                mMutableLiveData.setValue(null);
            }
        });
    }

    interface ProjectCategoriesApi {

        @GET("/project/tree/json")
        Call<ProjectCategoriesBean> get();
    }
}