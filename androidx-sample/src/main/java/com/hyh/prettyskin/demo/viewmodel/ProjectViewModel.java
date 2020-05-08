package com.hyh.prettyskin.demo.viewmodel;


import android.content.Context;

import com.hyh.prettyskin.demo.bean.ProjectsBean;
import com.hyh.prettyskin.demo.net.RetrofitHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ProjectViewModel extends ViewModel {

    public static class ProjectViewModelFactory implements ViewModelProvider.Factory {

        private final Context mContext;
        private final int mCid;

        public ProjectViewModelFactory(Context context, int cid) {
            this.mContext = context;
            this.mCid = cid;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProjectViewModel(mContext, mCid);
        }
    }

    private final MutableLiveData<Result> mMutableLiveData = new MutableLiveData<>();
    private final ProjectApi mProjectApi;
    private final int mCid;

    private int mNextPageIndex;

    public ProjectViewModel(Context context, int cid) {
        this.mProjectApi = RetrofitHelper.create(context, ProjectApi.class);
        this.mCid = cid;
    }

    public MutableLiveData<Result> getMutableLiveData() {
        return mMutableLiveData;
    }

    public void refresh() {
        mProjectApi.get(1, mCid).enqueue(new Callback<ProjectsBean>() {
            @Override
            public void onResponse(@NonNull Call<ProjectsBean> call, @NonNull Response<ProjectsBean> response) {
                if (response.isSuccessful()) {
                    mMutableLiveData.setValue(new Result(true, response.body()));
                    mNextPageIndex = 2;
                } else {
                    mMutableLiveData.setValue(new Result(true));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectsBean> call, @NonNull Throwable t) {
                mMutableLiveData.setValue(new Result(true));
            }
        });
    }

    public void loadMore() {
        mProjectApi.get(mNextPageIndex, mCid).enqueue(new Callback<ProjectsBean>() {
            @Override
            public void onResponse(@NonNull Call<ProjectsBean> call, @NonNull Response<ProjectsBean> response) {
                if (response.isSuccessful()) {
                    mMutableLiveData.setValue(new Result(false, response.body()));
                    mNextPageIndex++;
                } else {
                    mMutableLiveData.setValue(new Result(false));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectsBean> call, @NonNull Throwable t) {
                mMutableLiveData.setValue(new Result(false));
            }
        });
    }

    interface ProjectApi {

        //https://www.wanandroid.com/project/list/1/json?cid=294
        @GET("/project/list/{pageIndex}/json")
        Call<ProjectsBean> get(@Path("pageIndex") int pageIndex, @Query("cid") int cid);

    }

    public static class Result {

        public boolean refresh;

        @Nullable
        public ProjectsBean data;

        public Result(boolean refresh) {
            this.refresh = refresh;
        }

        public Result(boolean refresh, @Nullable ProjectsBean data) {
            this.refresh = refresh;
            this.data = data;
        }
    }
}