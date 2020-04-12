package com.hyh.prettyskin.demo.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.demo.bean.ProjectCategoryBean;
import com.hyh.prettyskin.demo.multiitem.MultiAdapter;
import com.hyh.prettyskin.demo.viewmodel.ProjectViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 * @description
 * @data 2020/4/10
 */
public class ProjectFragment extends CommonBaseFragment {


    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ProjectViewModel mProjectViewModel;
    private MultiAdapter mMultiAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        assert arguments != null;
        ProjectCategoryBean projectCategory = arguments.getParcelable("project_category");
        assert projectCategory != null;
        mProjectViewModel = new ViewModelProvider(this,
                new ProjectViewModel.ProjectViewModelFactory(
                        getContext(),
                        projectCategory.id))
                .get(ProjectViewModel.class);
    }

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(contentView);

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mMultiAdapter = new MultiAdapter(getContext());


        mProjectViewModel.getMutableLiveData().observe(this, result -> {
            assert result != null;
            if (result.refresh) {

            } else {

            }
        });
    }

    @Override
    protected void initData() {
        mProjectViewModel.refresh();
    }

    @Override
    protected boolean lazyLoadData() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMultiAdapter != null) {
            mMultiAdapter.destroy();
        }
    }
}
