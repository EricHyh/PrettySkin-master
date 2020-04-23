package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.AutoSizeableTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.reflect.Reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/11/13
 */

public class AppCompatTextSH implements ISkinHandler {

    private int mDefStyleAttr;

    public AppCompatTextSH(int defStyleAttr) {
        mDefStyleAttr = defStyleAttr;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof TextView &&
                (
                        TextUtils.equals(attrName, "drawableLeft")
                                || TextUtils.equals(attrName, "drawableTop")
                                || TextUtils.equals(attrName, "drawableRight")
                                || TextUtils.equals(attrName, "drawableBottom")
                                //|| TextUtils.equals(attrName, "drawableStart")
                                //|| TextUtils.equals(attrName, "drawableEnd")
                                || TextUtils.equals(attrName, "textColor")
                                || TextUtils.equals(attrName, "textColorHint")
                                || TextUtils.equals(attrName, "textColorLink")
                                //|| TextUtils.equals(attrName, "textAllCaps")
                                || TextUtils.equals(attrName, "textSize")

                                || TextUtils.equals(attrName, "firstBaselineToTopHeight")
                                || TextUtils.equals(attrName, "lastBaselineToBottomHeight")
                                || TextUtils.equals(attrName, "lineHeight")
                );

    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
    }

    @SuppressLint("RestrictedApi")
    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = null;

        TextView textView = (TextView) view;
        AutoSizeableTextView autoSizeableTextView = (AutoSizeableTextView) view;

        switch (attrName) {
            case "drawableLeft": {
                Drawable[] drawables = textView.getCompoundDrawables();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawables[0]);
                break;
            }
            case "drawableTop": {
                Drawable[] drawables = textView.getCompoundDrawables();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawables[1]);
                break;
            }
            case "drawableRight": {
                Drawable[] drawables = textView.getCompoundDrawables();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawables[2]);
                break;
            }
            case "drawableBottom": {
                Drawable[] drawables = textView.getCompoundDrawables();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawables[3]);
                break;
            }
            case "textColor": {
                ColorStateList textColors = textView.getTextColors();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, textColors);
                break;
            }
            case "textColorHint": {
                ColorStateList textColors = textView.getHintTextColors();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, textColors);
                break;
            }
            case "textColorLink": {
                ColorStateList textColors = textView.getLinkTextColors();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, textColors);
                break;
            }
            case "textSize": {
                float textSize = textView.getTextSize();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, textSize);
                break;
            }
            case "firstBaselineToTopHeight": {
                int firstBaselineToTopHeight;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    firstBaselineToTopHeight = textView.getFirstBaselineToTopHeight();
                } else {
                    firstBaselineToTopHeight = Reflect.from(view.getClass())
                            .method("getFirstBaselineToTopHeight", int.class)
                            .invoke(view);
                }
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_INT, firstBaselineToTopHeight);
                break;
            }
            case "lastBaselineToBottomHeight": {
                int lastBaselineToBottomHeight;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    lastBaselineToBottomHeight = textView.getLastBaselineToBottomHeight();
                } else {
                    lastBaselineToBottomHeight = Reflect.from(view.getClass())
                            .method("getLastBaselineToBottomHeight", int.class)
                            .invoke(view);
                }
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_INT, lastBaselineToBottomHeight);
                break;
            }
            case "lineHeight": {
                int lineHeight;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    lineHeight = textView.getLineHeight();
                } else {
                    lineHeight = Reflect.from(view.getClass())
                            .method("getLineHeight", int.class)
                            .invoke(view);
                }
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_INT, lineHeight);
                break;
            }
        }
        return attrValue;
    }

    @Override
    public void finishParse() {
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        TextView textView = (TextView) view;
        switch (attrName) {
            case "drawableLeft": {
                Drawable[] drawables = textView.getCompoundDrawables();
                textView.setCompoundDrawables(
                        attrValue.getTypedValue(Drawable.class, null),
                        drawables[1],
                        drawables[2],
                        drawables[3]);
                break;
            }
            case "drawableTop": {
                Drawable[] drawables = textView.getCompoundDrawables();
                textView.setCompoundDrawables(
                        drawables[0],
                        attrValue.getTypedValue(Drawable.class, null),
                        drawables[2],
                        drawables[3]);
                break;
            }
            case "drawableRight": {
                Drawable[] drawables = textView.getCompoundDrawables();
                textView.setCompoundDrawables(
                        drawables[0],
                        drawables[1],
                        attrValue.getTypedValue(Drawable.class, null),
                        drawables[3]);
                break;
            }
            case "drawableBottom": {
                Drawable[] drawables = textView.getCompoundDrawables();
                textView.setCompoundDrawables(
                        drawables[0],
                        drawables[1],
                        drawables[2],
                        attrValue.getTypedValue(Drawable.class, null));
                break;
            }
            case "textColor": {
                textView.setTextColor(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "textColorHint": {
                textView.setHintTextColor(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "textColorLink": {
                textView.setLinkTextColor(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "textSize": {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "firstBaselineToTopHeight": {
                int firstBaselineToTopHeight = attrValue.getTypedValue(int.class, -1);
                if (firstBaselineToTopHeight > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        textView.setFirstBaselineToTopHeight(firstBaselineToTopHeight);
                    } else {
                        Reflect.from(view.getClass())
                                .method("setFirstBaselineToTopHeight")
                                .param(int.class, firstBaselineToTopHeight)
                                .invoke(view);
                    }
                }
                break;
            }
            case "lastBaselineToBottomHeight": {
                int lastBaselineToBottomHeight = attrValue.getTypedValue(int.class, -1);
                if (lastBaselineToBottomHeight > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        textView.setLastBaselineToBottomHeight(lastBaselineToBottomHeight);
                    } else {
                        Reflect.from(view.getClass())
                                .method("setLastBaselineToBottomHeight")
                                .param(int.class, lastBaselineToBottomHeight)
                                .invoke(view);
                    }
                }
                break;
            }
            case "lineHeight": {
                int lineHeight = attrValue.getTypedValue(int.class, -1);
                if (lineHeight > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        textView.setLineHeight(lineHeight);
                    } else {
                        Reflect.from(view.getClass())
                                .method("setLineHeight")
                                .param(int.class, lineHeight)
                                .invoke(view);
                    }
                }
                break;
            }
        }
    }
}