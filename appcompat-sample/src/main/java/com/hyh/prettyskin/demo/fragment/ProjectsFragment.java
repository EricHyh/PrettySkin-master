package com.hyh.prettyskin.demo.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.demo.adapter.ProjectsAdapter;
import com.hyh.prettyskin.demo.viewmodel.ContextViewModelFactory;
import com.hyh.prettyskin.demo.viewmodel.ProjectCategoriesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 * @description
 * @data 2020/4/10
 */
public class ProjectsFragment extends CommonBaseFragment {

    private ProjectCategoriesViewModel mProjectCategoriesViewModel;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectCategoriesViewModel = new ViewModelProvider(this, new ContextViewModelFactory(getContext())).get(ProjectCategoriesViewModel.class);
    }

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this, contentView);
        final ProjectsAdapter projectsAdapter = new ProjectsAdapter(contentView.getContext(), getChildFragmentManager());
        mViewPager.setAdapter(projectsAdapter);
        mViewPager.setOffscreenPageLimit(1);

        mProjectCategoriesViewModel.getMutableLiveData().observe(this, projectGroupData -> {
            if (projectGroupData == null || projectGroupData.projectCategories == null) {
                showErrorView();
            } else {
                projectsAdapter.setProjectData(projectGroupData.projectCategories);
                showSuccessView();
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {
        showLoadingView();
        mProjectCategoriesViewModel.loadData();
    }

    private void showLoadingView() {

    }

    private void showSuccessView() {

    }

    private void showErrorView() {

    }
}