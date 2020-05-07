package com.hyh.prettyskin.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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

import com.hyh.prettyskin.ApkThemeSkin;
import com.hyh.prettyskin.ISkin;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.ThemeSkin;
import com.hyh.prettyskin.demo.base.SkinStyle;
import com.hyh.prettyskin.demo.fragment.CustomerAttrFragment;
import com.hyh.prettyskin.demo.fragment.DynamicDrawableFragment;
import com.hyh.prettyskin.demo.fragment.OtherFragment;
import com.hyh.prettyskin.demo.fragment.ProjectsFragment;
import com.hyh.prettyskin.demo.utils.AssetsSkinHelper;
import com.hyh.prettyskin.demo.utils.DisplayUtil;
import com.hyh.prettyskin.demo.utils.PreferenceUtil;
import com.hyh.prettyskin.demo.utils.ThreadUtil;
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

    private long mLastBackPressedTimeMillis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initStatusBar();
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
                    if (AssetsSkinHelper.isUnziped(this)) {
                        String skinPath = AssetsSkinHelper.getSkinPath(this);
                        ISkin skin = new ApkThemeSkin(getApplicationContext(), skinPath, 0);
                        PrettySkin.getInstance().replaceSkinAsync(skin, null);
                        PreferenceUtil.putInt(this, "skin_style", SkinStyle.PURPLE);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(this)
                                .setView(R.layout.loading_dialog)
                                .setOnDismissListener(dialog -> {
                                    if (AssetsSkinHelper.isUnziped(this)) {
                                        String skinPath = AssetsSkinHelper.getSkinPath(this);
                                        ISkin skin = new ApkThemeSkin(getApplicationContext(), skinPath, 0);
                                        PrettySkin.getInstance().replaceSkinAsync(skin, null);
                                        PreferenceUtil.putInt(this, "skin_style", SkinStyle.PURPLE);
                                    } else {
                                        Toast.makeText(this, "未找到资源", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                        ThreadUtil.execute(() -> {
                            AssetsSkinHelper.unzipAssetsSkin(this);
                            runOnUiThread(alertDialog::dismiss);
                        });
                    }
                    break;
                }
                case R.id.skin_orange_style: {
                    if (AssetsSkinHelper.isUnziped(this)) {
                        String skinPath = AssetsSkinHelper.getSkinPath(this);
                        ISkin skin = new ApkThemeSkin(getApplicationContext(), skinPath, 1);
                        PrettySkin.getInstance().replaceSkinAsync(skin, null);
                        PreferenceUtil.putInt(this, "skin_style", SkinStyle.ORANGE);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(this)
                                .setView(R.layout.loading_dialog)
                                .setOnDismissListener(dialog -> {
                                    if (AssetsSkinHelper.isUnziped(this)) {
                                        String skinPath = AssetsSkinHelper.getSkinPath(this);
                                        ISkin skin = new ApkThemeSkin(getApplicationContext(), skinPath, 1);
                                        PrettySkin.getInstance().replaceSkinAsync(skin, null);
                                        PreferenceUtil.putInt(this, "skin_style", SkinStyle.ORANGE);
                                    } else {
                                        Toast.makeText(this, "未找到资源", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                        ThreadUtil.execute(() -> {
                            AssetsSkinHelper.unzipAssetsSkin(this);
                            runOnUiThread(alertDialog::dismiss);
                        });
                    }
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
