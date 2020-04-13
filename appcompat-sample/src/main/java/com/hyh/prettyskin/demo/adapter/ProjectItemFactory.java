package com.hyh.prettyskin.demo.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.databinding.ItemProjectInfoBinding;
import com.hyh.prettyskin.demo.bean.ProjectBean;
import com.hyh.prettyskin.demo.multiitem.ItemHolder;
import com.hyh.prettyskin.demo.multiitem.MultiItemFactory;

public class ProjectItemFactory extends MultiItemFactory<ProjectBean> {

    @Override
    protected ItemHolder<ProjectBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_info, parent, false);
        return new ProjectItemHolder(view);
    }

    @Override
    protected int getItemViewType(int position) {
        return 0;
    }

    private class ProjectItemHolder extends ItemHolder<ProjectBean> {

        private ItemProjectInfoBinding mDataBinding;

        ProjectItemHolder(View itemView) {
            super(itemView);
            mDataBinding = DataBindingUtil.bind(itemView);
        }

        @Override
        protected void bindDataAndEvent() {
            mDataBinding.setProject(getData());
        }
    }
}