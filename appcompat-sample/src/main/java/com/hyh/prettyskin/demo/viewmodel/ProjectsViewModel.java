package com.hyh.prettyskin.demo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.hyh.prettyskin.demo.bean.ProjectGroupData;
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
public class ProjectsViewModel extends ViewModel {

    private final MutableLiveData<ProjectGroupData> mMutableLiveData = new MutableLiveData<>();
    private final ProjectsApi mProjectsApi;

    public ProjectsViewModel(@NonNull Application application) {
        mProjectsApi = RetrofitHelper.create(application, ProjectsApi.class);
    }

    public MutableLiveData<ProjectGroupData> getMutableLiveData() {
        return mMutableLiveData;
    }

    public void loadProjects() {
        mProjectsApi.get().enqueue(new Callback<ProjectGroupData>() {
            @Override
            public void onResponse(@NonNull Call<ProjectGroupData> call, @NonNull Response<ProjectGroupData> response) {
                if (response.isSuccessful()) {
                    mMutableLiveData.setValue(response.body());
                } else {
                    mMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectGroupData> call, @NonNull Throwable t) {
                mMutableLiveData.setValue(null);
            }
        });
    }

    interface ProjectsApi {

        @GET("/project/tree/json")
        Call<ProjectGroupData> get();
    }
}