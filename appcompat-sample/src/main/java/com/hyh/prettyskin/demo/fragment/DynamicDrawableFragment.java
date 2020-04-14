package com.hyh.prettyskin.demo.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.demo.drawable.AddDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 * @description
 * @data 2020/4/10
 */
public class DynamicDrawableFragment extends CommonBaseFragment {

    @BindView(R.id.button)
    Button mButton;

    @BindView(R.id.view)
    View mView;

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dynamic_drawable, container, false);
    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this, contentView);
        mButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg_light_style));
        mView.setBackgroundDrawable(new AddDrawable(contentView.getContext(), Color.RED));
    }

    @Override
    protected void initData() {

    }
}
