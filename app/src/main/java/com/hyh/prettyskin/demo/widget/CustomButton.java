package com.hyh.prettyskin.demo.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.hyh.prettyskin.R;

/**
 * @author Administrator
 * @description
 * @data 2019/4/10
 */

public class CustomButton extends AppCompatButton {

    private static final String TAG = "CustomButton";

    public CustomButton(Context context) {
        this(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /*{
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    Reflect.from("com.android.internal.R$styleable").filed("TextViewAppearance", int[].class).get(null),
                    defStyleAttr,
                    0);
            if (typedArray != null) {
                Integer textAppearanceIndex = Reflect.from("com.android.internal.R$styleable")
                        .filed("TextViewAppearance_textAppearance", int.class).get(null);
                int resourceId = typedArray.getResourceId(textAppearanceIndex, -1);
                typedArray.recycle();
                if (resourceId != -1) {
                    TypedArray appearance = context.obtainStyledAttributes(resourceId,
                            Reflect.from("com.android.internal.R$styleable").filed("TextAppearance", int[].class).get(null));
                    if (appearance != null) {

                        //com.android.internal.R.styleable.TextAppearance_textColor
                        Integer textColorIndex = Reflect.from("com.android.internal.R$styleable")
                                .filed("TextAppearance_textColor", int.class).get(null);
                        ColorStateList colorStateList = appearance.getColorStateList(textColorIndex);
                        Log.d(TAG, "CustomButton: colorStateList1 = " + colorStateList);

                        appearance.recycle();
                    }
                }
            }
        }
        {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    Reflect.from("com.android.internal.R$styleable").filed("TextView", int[].class).get(null),
                    defStyleAttr,
                    0);

            //com.android.internal.R.styleable.TextView_textColor:
            Integer textColorIndex = Reflect.from("com.android.internal.R$styleable").filed("TextView_textColor", int.class).get(null);
            ColorStateList colorStateList = typedArray.getColorStateList(textColorIndex);
            Log.d(TAG, "CustomButton: colorStateList2 = " + colorStateList);
            typedArray.recycle();
        }*/
    }
}
