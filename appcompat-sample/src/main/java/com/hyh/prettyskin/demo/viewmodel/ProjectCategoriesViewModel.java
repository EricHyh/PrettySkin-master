package com.hyh.prettyskin.demo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.hyh.prettyskin.demo.bean.ProjectCategoriesBean;
import com.hyh.prettyskin.demo.net.RetrofitHelper;

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

    public ProjectCategoriesViewModel(@NonNull Application application) {
        mProjectCategoriesApi = RetrofitHelper.create(application, ProjectCategoriesApi.class);
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