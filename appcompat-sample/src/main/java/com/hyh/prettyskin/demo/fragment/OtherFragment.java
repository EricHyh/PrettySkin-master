package com.hyh.prettyskin.demo.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.SkinView;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.demo.pop.DimBackgroundPopWindow;
import com.hyh.prettyskin.demo.utils.DisplayUtil;

import java.util.HashMap;
import java.util.Map;

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

        Context context = contentView.getContext();
        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText("我是一个通过代码new出来的TextView，我的文字颜色、背景、文字大小属性加入了皮肤管理，换个皮肤再来看看我吧！");
        mFrameLayout.addView(textView);

        int defaultBg = context.getResources().getColor(R.color.primary_red);
        int defaultTextColor = Color.WHITE;
        float defaultTextSize = DisplayUtil.dip2px(context, 16);

        textView.setBackgroundColor(defaultBg);
        textView.setTextColor(defaultTextColor);
        textView.setTextSize(16);

        Map<String, String> attrKeyMap = new HashMap<>();
        attrKeyMap.put("background", "new_text_view_bg");
        attrKeyMap.put("textColor", "new_text_view_text_color");
        attrKeyMap.put("textSize", "new_text_view_text_size");
        Map<String, AttrValue> defaultAttrValueMap = new HashMap<>();
        defaultAttrValueMap.put("new_text_view_bg", new AttrValue(context, ValueType.TYPE_COLOR_INT, defaultBg));
        defaultAttrValueMap.put("new_text_view_text_color", new AttrValue(context, ValueType.TYPE_COLOR_INT, defaultTextColor));
        defaultAttrValueMap.put("new_text_view_text_size", new AttrValue(context, ValueType.TYPE_FLOAT, defaultTextSize));
        SkinView skinView = new SkinView(textView, attrKeyMap, defaultAttrValueMap);

        PrettySkin.getInstance().addSkinView(skinView);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_show_dialog)
    public void showDialog(View view) {
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.view_dialog, null);
        AlertDialog alertDialog = new AlertDialog
                .Builder(view.getContext())
                .setView(dialogView)
                .create();
        //PrettySkin.getInstance().setContextSkinable(alertDialog.getContext());
        dialogView.findViewById(R.id.dialog_close_btn).setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }

    @OnClick(R.id.btn_show_popup_window)
    public void showPopupWindow(View view) {
        View popupView = LayoutInflater.from(view.getContext()).inflate(R.layout.view_popup, null);
        PopupWindow popupWindow = new DimBackgroundPopWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT) {
        };
        popupView.findViewById(R.id.popup_close_btn).setOnClickListener(v -> popupWindow.dismiss());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }
}