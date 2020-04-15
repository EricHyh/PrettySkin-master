package com.hyh.prettyskin.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.demo.fragment.CustomerAttrFragment;
import com.hyh.prettyskin.demo.fragment.DynamicDrawableFragment;
import com.hyh.prettyskin.demo.fragment.OtherFragment;
import com.hyh.prettyskin.demo.fragment.ProjectsFragment;
import com.hyh.prettyskin.demo.utils.DisplayUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */
public class MainActivity extends BaseActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initDrawerLayout();
        initFragmentTabHost();
        initLeftDrawer();

        int defaultValue = Reflect.from("android.support.design.R$style")
                .filed("TextAppearance_Design_Tab", int.class)
                .get(null);
        Log.d("MainActivity_", "onCreate: defaultValue = " + defaultValue);
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void initDrawerLayout() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.removeDrawerListener(mDrawerToggle);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void initFragmentTabHost() {
        int tabHostId = R.id.fragment_tab_host;
        int tabContentId = R.id.fragment_container;
        mTabHost = findViewById(tabHostId);
        mTabHost.setup(this, getSupportFragmentManager(), tabContentId);
        setTabWidgetTheme();
        addTabLabels();
    }

    private void initLeftDrawer() {

    }


    /**
     * 设置tab widget的风格
     */
    private void setTabWidgetTheme() {
        TabWidget tabWidget = mTabHost.getTabWidget();
        tabWidget.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        /*tab之间不要分隔标签*/
        tabWidget.setDividerDrawable(null);
    }

    /**
     * 添加底部的各个tab
     */
    private void addTabLabels() {
        {
            TabHost.TabSpec tabSpec = mTabHost
                    .newTabSpec("0")
                    .setIndicator(getIndicatorView("主页"));
            mTabHost.addTab(tabSpec, ProjectsFragment.class, null);
        }
        {
            TabHost.TabSpec tabSpec = mTabHost
                    .newTabSpec("1")
                    .setIndicator(getIndicatorView("属性"));
            mTabHost.addTab(tabSpec, CustomerAttrFragment.class, null);
        }
        {
            TabHost.TabSpec tabSpec = mTabHost
                    .newTabSpec("2")
                    .setIndicator(getIndicatorView("Drawable"));
            mTabHost.addTab(tabSpec, DynamicDrawableFragment.class, null);
        }
        {
            TabHost.TabSpec tabSpec = mTabHost
                    .newTabSpec("3")
                    .setIndicator(getIndicatorView("其他"));
            mTabHost.addTab(tabSpec, OtherFragment.class, null);
        }
    }

    private View getIndicatorView(String text) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(0, DisplayUtil.dip2px(this, 60), 1);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextSize(14);
        textView.setTextColor(getResources().getColorStateList(R.color.bottom_tab_text_color));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}