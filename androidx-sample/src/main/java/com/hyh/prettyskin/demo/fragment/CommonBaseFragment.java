package com.hyh.prettyskin.demo.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyh.prettyskin.demo.lifecycle.ActivityLifecycleHelper;
import com.hyh.prettyskin.demo.visible.IVisibleHandler;
import com.hyh.prettyskin.demo.visible.ItemVisibleStrategy;
import com.hyh.prettyskin.demo.visible.ViewVisibleDetector;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelStore;

/**
 * @author Administrator
 * @description
 * @data 2018/3/6
 */

public abstract class CommonBaseFragment extends Fragment {

    private Context mContext;

    private boolean mIsLoadedData = false;

    private boolean mIsCompelledLoading;

    protected boolean mIsSelected;

    protected View mContentView;

    private boolean isNeedLoadData;

    private Context mThemeContext;

    private ActivityLifecycleListener mLifecycleListener;

    private int mDefaultThemeResId;

    private boolean mIsRegisterActivityLifecycleCallbacks;

    private ViewModelStore mViewModelStore;

    public void setCompelledLoading() {
        mIsCompelledLoading = true;
    }

    public void setDefaultThemeResId(int themeResId) {
        this.mDefaultThemeResId = themeResId;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (!mIsRegisterActivityLifecycleCallbacks && isFollowActivityLifecycle()) {
            Activity activity = getActivity();
            if (activity == null) {
                if (context instanceof Activity) {
                    activity = (Activity) context;
                }
            }
            mLifecycleListener = new ActivityLifecycleListener(activity, this);
            ActivityLifecycleHelper.getInstance().listenerAllActivity(mLifecycleListener);
            mIsRegisterActivityLifecycleCallbacks = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int themeResId = getThemeResId();
        if (themeResId == 0) {
            if (inflater != null) {
                mThemeContext = inflater.getContext();
            } else {
                Context context = getContext();
                mThemeContext = context;
                inflater = LayoutInflater.from(context);
            }
        } else {
            mThemeContext = new ContextThemeWrapper(getContext().getApplicationContext(), themeResId);
            inflater = LayoutInflater.from(mThemeContext);
        }
        if (isNeedReCreateView()) {
            mContentView = getContentView(inflater, container);
            initView(mContentView);
            if (mIsCompelledLoading || !lazyLoadData() || (isNeedLoadData && mIsSelected)) {
                mIsLoadedData = true;
                isNeedLoadData = false;
                initData();
            }
        } else {
            if (mContentView != null) {
                return mContentView;
            }
            mContentView = getContentView(inflater, container);
            initView(mContentView);
            if (mIsCompelledLoading || !lazyLoadData() || (isNeedLoadData && mIsSelected)) {
                mIsLoadedData = true;
                isNeedLoadData = false;
                initData();
            }
        }
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (lazyLoadData()) {
            ViewVisibleDetector.setVisibleHandler(view, new VisibleHandler());
        }
    }

    @Override
    public Context getContext() {
        Context context = super.getContext();
        if (context != null) return context;
        return mContext;
    }

    protected Context getThemeContext() {
        return mThemeContext;
    }

    protected int getThemeResId() {
        return mDefaultThemeResId;
    }

    protected abstract View getContentView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView(View contentView);

    protected abstract void initData();

    protected boolean lazyLoadData() {
        return false;
    }

    protected boolean isNeedReCreateView() {
        return false;
    }

    protected boolean isFollowActivityLifecycle() {
        return false;
    }

    protected boolean isActivityFinish() {
        Activity activity = getActivity();
        return activity == null || activity.isFinishing();
    }


    protected void finishActivity() {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentActivity activity = getActivity();
        boolean isChangingConfigurations = activity != null && activity.isChangingConfigurations();
        if (mViewModelStore != null && !isChangingConfigurations) {
            mViewModelStore.clear();
        }
    }

    protected void onActivityStarted() {
    }

    protected void onActivityResumed() {
    }

    protected void onActivityPaused() {
    }

    protected void onActivityStopped() {
    }

    protected void onActivityDestroyed() {
        ActivityLifecycleListener lifecycleListener = mLifecycleListener;
        if (lifecycleListener != null) {
            ActivityLifecycleHelper.getInstance().unListenerAllActivity(lifecycleListener);
        }
        mLifecycleListener = null;
        mIsRegisterActivityLifecycleCallbacks = false;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        ActivityLifecycleListener lifecycleListener = mLifecycleListener;
        if (lifecycleListener != null) {
            ActivityLifecycleHelper.getInstance().unListenerAllActivity(lifecycleListener);
        }
    }

    private class VisibleHandler implements IVisibleHandler {

        private final List<ItemVisibleStrategy> mVisibleStrategies = Collections.singletonList(
                new ItemVisibleStrategy(
                        ItemVisibleStrategy.VISIBLE_TYPE_HORIZONTAL,
                        1.0f,
                        100)
        );


        @Override
        public List<ItemVisibleStrategy> getItemVisibleStrategies() {
            return mVisibleStrategies;
        }

        @Override
        public void onViewInvisible(View view, ItemVisibleStrategy strategy) {

        }

        @Override
        public void onViewVisible(View view, ItemVisibleStrategy strategy) {
            if (!mIsLoadedData) {
                if (mContentView != null) {
                    mIsLoadedData = true;
                    isNeedLoadData = false;
                    initData();
                } else {
                    isNeedLoadData = true;
                }
            }
        }
    }

    private static class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {

        private final WeakReference<Activity> mActivityReference;

        private final WeakReference<CommonBaseFragment> mFragmentReference;

        ActivityLifecycleListener(Activity activity, CommonBaseFragment fragment) {
            mActivityReference = new WeakReference<>(activity);
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (getActivity() == activity) {
                CommonBaseFragment fragment = mFragmentReference.get();
                if (fragment != null) {
                    fragment.onActivityStarted();
                } else {
                    ActivityLifecycleHelper.getInstance().unListenerAllActivity(this);
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (getActivity() == activity) {
                CommonBaseFragment fragment = mFragmentReference.get();
                if (fragment != null) {
                    fragment.onActivityResumed();
                } else {
                    ActivityLifecycleHelper.getInstance().unListenerAllActivity(this);
                }
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (getActivity() == activity) {
                CommonBaseFragment fragment = mFragmentReference.get();
                if (fragment != null) {
                    fragment.onActivityPaused();
                } else {
                    ActivityLifecycleHelper.getInstance().unListenerAllActivity(this);
                }
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (getActivity() == activity) {
                CommonBaseFragment fragment = mFragmentReference.get();
                if (fragment != null) {
                    fragment.onActivityStopped();
                } else {
                    ActivityLifecycleHelper.getInstance().unListenerAllActivity(this);
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (getActivity() == activity) {
                CommonBaseFragment fragment = mFragmentReference.get();
                if (fragment != null) {
                    fragment.onActivityDestroyed();
                } else {
                    ActivityLifecycleHelper.getInstance().unListenerAllActivity(this);
                }
            }
        }

        private Activity getActivity() {
            Activity activity = mActivityReference.get();
            if (activity == null) {
                CommonBaseFragment commonBaseFragment = mFragmentReference.get();
                if (commonBaseFragment != null) {
                    activity = commonBaseFragment.getActivity();
                }
            }
            return activity;
        }
    }

/*    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if (getContext() == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (mViewModelStore == null) {
            mViewModelStore = new ViewModelStore();
        }
        return mViewModelStore;
    }*/
}