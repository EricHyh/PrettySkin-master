package com.hyh.prettyskin.demo.fragment;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hyh.prettyskin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @description
 * @data 2020/4/10
 */
public class OtherFragment extends CommonBaseFragment {

    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_other, container, false);
    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this, contentView);

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText("我是一个通过代码new出来的TextView，我的文字颜色、背景、文字大小属性加入了皮肤管理，换个皮肤再来看看我吧！");
        textView.setTextSize(16);
        textView.setTextColor(Color.BLACK);
        mFrameLayout.addView(textView);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_show_dialog)
    public void showDialog(View view) {

    }

    @OnClick(R.id.btn_show_popup_window)
    public void showPopupWindow(View view) {

    }
}