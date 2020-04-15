package com.hyh.prettyskin.demo.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.demo.drawable.AddDrawable;
import com.hyh.prettyskin.drawable.DynamicDrawable;

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

        Context context = contentView.getContext();

        {
            String attrKey = "test_dynamic_drawable";
            Drawable defaultDrawable = context.getResources().getDrawable(R.drawable.test_dynamic_drawable_white_style);
            mButton.setBackgroundDrawable(new DynamicDrawable(attrKey, defaultDrawable));
        }

        {
            String attrKey = "add_drawable_color";
            int defaultColor = context.getResources().getColor(R.color.primary_red);
            DynamicDrawable dynamicDrawable = new DynamicDrawable(attrKey, new AddDrawable(context, defaultColor)) {

                /**
                 * 重写该函数，实现根据指定的皮肤属性创建Drawable，这里的皮肤属性是“add_drawable_color”，根据这个color创建一个AddDrawable
                 */
                @Override
                protected Drawable convertAttrValueToDrawable(AttrValue attrValue) {
                    return new AddDrawable(context, attrValue.getTypedValue(int.class, defaultColor));
                }
            };
            mView.setBackgroundDrawable(dynamicDrawable);
        }
    }

    @Override
    protected void initData() {

    }
}
