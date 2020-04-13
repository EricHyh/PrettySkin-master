package com.hyh.prettyskin.demo.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.demo.adapter.ProjectItemFactory;
import com.hyh.prettyskin.demo.bean.ProjectCategoryBean;
import com.hyh.prettyskin.demo.multiitem.MultiAdapter;
import com.hyh.prettyskin.demo.multiitem.load.ChrysanthemumFootView;
import com.hyh.prettyskin.demo.multiitem.load.LoadingItemFactory;
import com.hyh.prettyskin.demo.multiitem.load.SwipeRefreshClient;
import com.hyh.prettyskin.demo.utils.DisplayUtil;
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
    private LoadingItemFactory mLoadingItemFactory;

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
        ButterKnife.bind(this, contentView);

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = DisplayUtil.dip2px(view.getContext(), 5);
            }
        });

        mMultiAdapter = new MultiAdapter(getContext());

        //添加数据Item
        final ProjectItemFactory projectItemFactory = new ProjectItemFactory();
        mMultiAdapter.addMultiModule(projectItemFactory);

        //添加加载更多Item
        ChrysanthemumFootView footView = new ChrysanthemumFootView(contentView.getContext(), Color.TRANSPARENT, Color.GRAY, 0xFFDDDDDD);
        mLoadingItemFactory = new LoadingItemFactory(footView);
        mLoadingItemFactory.bindRefreshListener(new SwipeRefreshClient(mSwipeRefreshLayout));
        mLoadingItemFactory.bindScrollListener(mRecyclerView);
        mLoadingItemFactory.setLoadingListener(new LoadingItemFactory.LoadingListener() {
            @Override
            public void onRefresh() {
                mProjectViewModel.refresh();
            }

            @Override
            public void onLoadMore() {
                mProjectViewModel.loadMore();
            }
        });
        mMultiAdapter.addMultiModule(mLoadingItemFactory);
        mRecyclerView.setAdapter(mMultiAdapter);

        mProjectViewModel.getMutableLiveData().observe(this, result -> {
            assert result != null;
            if (result.refresh) {
                mLoadingItemFactory.refreshComplete(result.data != null);
                mLoadingItemFactory.setLoadMoreEnabled(result.data != null);
                if (result.data != null) {
                    projectItemFactory.setDataList(result.data.data.projects);
                }
            } else {
                mLoadingItemFactory.loadMoreComplete(result.data != null);
                if (result.data != null) {
                    projectItemFactory.addDataList(result.data.data.projects);
                    if (result.data.data.total == projectItemFactory.getList().size()) {
                        mLoadingItemFactory.setNoMore(true);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        mLoadingItemFactory.executeRefresh();
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