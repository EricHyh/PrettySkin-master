package com.hyh.prettyskin.demo.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.demo.viewmodel.ContextViewModelFactory;
import com.hyh.prettyskin.demo.viewmodel.ProjectsViewModel;

/**
 * @author Administrator
 * @description
 * @data 2020/4/10
 */
public class MultiFeedsFragment extends CommonBaseFragment {

    private ProjectsViewModel mProjectsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectsViewModel = new ViewModelProvider(this, new ContextViewModelFactory(getContext())).get(ProjectsViewModel.class);
    }

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_multi_feeds, container, false);
    }

    @Override
    protected void initView(View contentView) {



        mProjectsViewModel.getMutableLiveData().observe(this, projectGroupData -> {

        });
    }

    @Override
    protected void initData() {
        mProjectsViewModel.loadProjects();
    }
}