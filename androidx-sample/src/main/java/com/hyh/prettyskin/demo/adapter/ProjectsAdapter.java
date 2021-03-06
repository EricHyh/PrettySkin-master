package com.hyh.prettyskin.demo.adapter;

import android.content.Context;
import android.os.Bundle;

import com.hyh.prettyskin.demo.bean.ProjectCategoryBean;
import com.hyh.prettyskin.demo.fragment.ProjectFragment;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


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
        return Fragment.instantiate(mContext, ProjectFragment.class.getName(), bundle);
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