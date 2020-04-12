package com.hyh.prettyskin.demo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hyh.prettyskin.demo.bean.ProjectCategoryBean;

import java.util.List;


public class ProjectsAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private List<ProjectCategoryBean> mProjects;

    public ProjectsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    public void setProjectData(List<ProjectCategoryBean> projects) {
        this.mProjects = projects;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        ProjectCategoryBean projectCategoryBean = mProjects.get(i);
        Bundle bundle = new Bundle();
        bundle.putParcelable("project_category", projectCategoryBean);
        return Fragment.instantiate(mContext, projectCategoryBean.name, bundle);
    }

    @Override
    public int getCount() {
        return mProjects == null ? 0 : mProjects.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mProjects.get(position).name;
    }
}