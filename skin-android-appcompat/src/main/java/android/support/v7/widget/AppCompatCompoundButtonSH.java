package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v4.widget.TintableCompoundButton;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ValueType;

/**
 * Created by Eric_He on 2018/12/4.
 */
@SuppressLint({"RestrictedApi", "PrivateResource"})
public class AppCompatCompoundButtonSH implements ISkinHandler {

    private int mDefStyleAttr;

    private TypedArray mTypedArray;

    public AppCompatCompoundButtonSH(int defStyleAttr) {
        mDefStyleAttr = defStyleAttr;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return (view instanceof TintableCompoundButton
                && view instanceof CompoundButton)
                && (TextUtils.equals(attrName, "button"))
                || (TextUtils.equals(attrName, "app:buttonTint"))
                || (TextUtils.equals(attrName, "app:buttonTintMode"));
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        mTypedArray = view.getContext().obtainStyledAttributes(set, R.styleable.CompoundButton,
                mDefStyleAttr, 0);
    }


    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = null;
        try {
            switch (attrName) {
                case "button": {
                    final int resourceId = mTypedArray.getResourceId(
                            R.styleable.CompoundButton_android_button, 0);
                    if (resourceId != 0) {
                        Drawable drawable = AppCompatResources.getDrawable(view.getContext(), resourceId);
                        if (drawable != null) {
                            attrValue = new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawable);
                        }
                    }
                    break;
                }
                case "app:buttonTint": {
                    ColorStateList colorStateList = mTypedArray.getColorStateList(R.styleable.CompoundButton_buttonTint);
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, colorStateList);
                    break;
                }
                case "app:buttonTintMode": {
                    PorterDuff.Mode tintMode = DrawableUtils.parseTintMode(
                            mTypedArray.getInt(R.styleable.CompoundButton_buttonTintMode, -1), null);
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_OBJECT, tintMode);
                    break;
                }
            }
        } catch (Exception e) {
            //
        }
        return attrValue;
    }

    @Override
    public void finishParse() {
        if (mTypedArray != null) {
            mTypedArray.recycle();
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        Context themeContext = attrValue.getThemeContext();
        int type = attrValue.getType();
        Object value = attrValue.getValue();
        Resources resources = null;
        if (themeContext != null) {
            resources = themeContext.getResources();
        }
        if (resources == null && type == ValueType.TYPE_REFERENCE) {
            return;
        }
        CompoundButton compoundButton = (CompoundButton) view;
        switch (attrName) {
            case "button": {
                if (value != null && value instanceof Drawable) {
                    compoundButton.setButtonDrawable((Drawable) value);
                }
                break;
            }
            case "app:buttonTint": {
                ColorStateList buttonTint = attrValue.getTypedValue(ColorStateList.class, null);
                CompoundButtonCompat.setButtonTintList(compoundButton, buttonTint);
                break;
            }
            case "app:buttonTintMode": {
                PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                CompoundButtonCompat.setButtonTintMode(compoundButton, tintMode);
                break;
            }
        }
    }
}
