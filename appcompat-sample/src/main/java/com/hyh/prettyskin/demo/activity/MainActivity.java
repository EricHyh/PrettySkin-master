package com.hyh.prettyskin.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.hyh.prettyskin.AssetsApkThemeSkin;
import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ISkin;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.ThemeSkin;
import com.hyh.prettyskin.demo.base.SkinStyle;
import com.hyh.prettyskin.demo.fragment.CustomerAttrFragment;
import com.hyh.prettyskin.demo.fragment.DynamicDrawableFragment;
import com.hyh.prettyskin.demo.fragment.OtherFragment;
import com.hyh.prettyskin.demo.fragment.ProjectsFragment;
import com.hyh.prettyskin.demo.utils.DisplayUtil;
import com.hyh.prettyskin.demo.utils.PreferenceUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */
public class MainActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private FragmentTabHost mTabHost;

    private long mLastBackPressedTimeMillis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ISkin currentSkin = PrettySkin.getInstance().getCurrentSkin();
        if(currentSkin!=null){
            AttrValue contentBgColor = currentSkin.getAttrValue("content_bg_color");
            Drawable typedValue = contentBgColor.getTypedValue(Drawable.class, null);
            Log.d("XXXX_", "onCreate: " + typedValue);
            getWindow().setBackgroundDrawable(typedValue);
        }

        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(this);
        LayoutInflater inflater = Reflect.from(AsyncLayoutInflater.class).filed("mInflater",
                LayoutInflater.class)
                .get(asyncLayoutInflater);
        PrettySkin.getInstance().setLayoutInflaterSkinable(inflater);

        asyncLayoutInflater.inflate(R.layout.activity_main, null, (view, i, viewGroup) -> {
            setContentView(view);
            initStatusBar();
            initToolBar();
            initDrawerLayout();
            initFragmentTabHost();
            initLeftDrawer();
        });

        /*setContentView(R.layout.activity_main);
        initStatusBar();
        initToolBar();
        initDrawerLayout();
        initFragmentTabHost();
        initLeftDrawer();*/
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

        RadioGroup skinRadioGroup = findViewById(R.id.skin_radio_group);
        int skinStyle = PreferenceUtil.getInt(this, "skin_style", -1);
        switch (skinStyle) {
            case SkinStyle.WHITE: {
                skinRadioGroup.check(R.id.skin_white_style);
                break;
            }
            case SkinStyle.BLACK: {
                skinRadioGroup.check(R.id.skin_black_style);
                break;
            }
            case SkinStyle.PURPLE: {
                skinRadioGroup.check(R.id.skin_purple_style);
                break;
            }
            case SkinStyle.ORANGE: {
                skinRadioGroup.check(R.id.skin_orange_style);
                break;
            }
        }
        skinRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.skin_white_style: {
                    PreferenceUtil.putInt(this, "skin_style", SkinStyle.WHITE);
                    ThemeSkin themeSkin = new ThemeSkin(this, R.style.PrettySkin_white, R.styleable.class, "PrettySkin");
                    PrettySkin.getInstance().replaceSkinAsync(themeSkin, null);
                    break;
                }
                case R.id.skin_black_style: {
                    PreferenceUtil.putInt(this, "skin_style", SkinStyle.BLACK);
                    ThemeSkin themeSkin = new ThemeSkin(this, R.style.PrettySkin_black, R.styleable.class, "PrettySkin");
                    PrettySkin.getInstance().replaceSkinAsync(themeSkin, null);
                    break;
                }
                case R.id.skin_purple_style: {
                    PreferenceUtil.putInt(this, "skin_style", SkinStyle.PURPLE);
                    ISkin skin = new AssetsApkThemeSkin(this, "skin-package-first", 0);
                    PrettySkin.getInstance().replaceSkinAsync(skin, null);
                    break;
                }
                case R.id.skin_orange_style: {
                    PreferenceUtil.putInt(this, "skin_style", SkinStyle.ORANGE);
                    ISkin skin = new AssetsApkThemeSkin(this, "skin-package-first", 1);
                    PrettySkin.getInstance().replaceSkinAsync(skin, null);
                    break;
                }
            }
        });
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
        TextView textView = (TextView) LayoutInflater.from(this)
                .inflate(R.layout.tabhost_indicator, mTabHost, false);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(0, DisplayUtil.dip2px(this, 60), 1);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
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
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }

        long currentTimeMillis = System.currentTimeMillis();
        long timeInterval = Math.abs(currentTimeMillis - mLastBackPressedTimeMillis);
        if (timeInterval > 3000) {
            mLastBackPressedTimeMillis = currentTimeMillis;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
