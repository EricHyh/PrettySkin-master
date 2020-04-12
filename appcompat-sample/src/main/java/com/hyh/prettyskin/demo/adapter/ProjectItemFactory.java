package com.hyh.prettyskin.demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.demo.bean.ProjectBean;
import com.hyh.prettyskin.demo.multiitem.ItemHolder;
import com.hyh.prettyskin.demo.multiitem.MultiItemFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        @BindView(R.id.iv_project_image)
        ImageView mProjectImage;

        @BindView(R.id.tv_project_title)
        TextView mProjectTitle;

        @BindView(R.id.tv_project_des)
        TextView mProjectDes;

        @BindView(R.id.tv_share_date)
        TextView mShareDate;

        @BindView(R.id.tv_author)
        TextView mAuthor;

        ProjectItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

        @Override
        protected void bindDataAndEvent() {
        }
    }
}