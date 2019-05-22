package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.appcompat.R;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.utils.ViewAttrUtil;


/**
 * @author Administrator
 * @description
 * @data 2018/11/13
 */
@SuppressLint({"RestrictedApi", "PrivateResource"})
public class AppCompatBackgroundSH implements ISkinHandler {

    private int mDefStyleAttr;
    private TintTypedArray mTypedArray;

    public AppCompatBackgroundSH(int defStyleAttr) {
        mDefStyleAttr = defStyleAttr;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof TintableBackgroundView &&
                (TextUtils.equals(attrName, "background")
                        || TextUtils.equals(attrName, "app:backgroundTint")
                        || TextUtils.equals(attrName, "app:backgroundTintMode"));
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        mTypedArray = TintTypedArray.obtainStyledAttributes(view.getContext(), set,
                R.styleable.ViewBackgroundHelper, mDefStyleAttr, 0);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = null;
        switch (attrName) {
            case "background": {
                Drawable background = view.getBackground();
                if (background != null) {
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, background);
                }
                break;
            }
            case "app:backgroundTint": {
                ColorStateList backgroundTint = null;
                if (view instanceof TintableBackgroundView) {
                    TintableBackgroundView tintableBackgroundView = (TintableBackgroundView) view;
                    backgroundTint = tintableBackgroundView.getSupportBackgroundTintList();
                } else {
                    try {
                        backgroundTint = mTypedArray.getColorStateList(R.styleable.ViewBackgroundHelper_backgroundTint);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (backgroundTint != null) {
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, backgroundTint);
                }
                break;
            }
            case "app:backgroundTintMode": {
                PorterDuff.Mode backgroundTintMode = null;
                if (view instanceof TintableBackgroundView) {
                    TintableBackgroundView tintableBackgroundView = (TintableBackgroundView) view;
                    backgroundTintMode = tintableBackgroundView.getSupportBackgroundTintMode();
                } else {
                    try {
                        backgroundTintMode = DrawableUtils.parseTintMode(
                                mTypedArray.getInt(R.styleable.ViewBackgroundHelper_backgroundTintMode, -1),
                                null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (backgroundTintMode != null) {
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_OBJECT, backgroundTintMode);
                }
                break;
            }
        }
        return attrValue;
    }

    @Override
    public void finishParse() {
        if (mTypedArray != null) {
            mTypedArray.recycle();
            mTypedArray = null;
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        Context context = attrValue.getThemeContext();
        int type = attrValue.getType();
        if (context == null && type == ValueType.TYPE_REFERENCE) {
            return;
        }
        Resources resources = null;
        if (context != null) {
            resources = context.getResources();
        }
        Object value = attrValue.getValue();
        switch (attrName) {
            case "background": {
                switch (type) {
                    case ValueType.TYPE_COLOR_INT: {
                        int color = 0;
                        if (value != null) {
                            color = (int) value;
                        }
                        view.setBackgroundColor(color);
                        break;
                    }
                    case ValueType.TYPE_DRAWABLE: {
                        Drawable background = null;
                        if (value != null) {
                            background = (Drawable) value;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(background);
                        } else {
                            view.setBackgroundDrawable(background);
                        }
                        break;
                    }
                    case ValueType.TYPE_REFERENCE: {
                        int drawableId = 0;
                        Drawable background = null;
                        if (value != null) {
                            background = resources.getDrawable(drawableId);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(background);
                        } else {
                            view.setBackgroundDrawable(background);
                        }
                        break;
                    }
                }
                break;
            }
            case "app:backgroundTint": {
                if (view instanceof TintableBackgroundView) {
                    ColorStateList backgroundTint = attrValue.getTypedValue(ColorStateList.class, null);
                    TintableBackgroundView tintableBackgroundView = (TintableBackgroundView) view;
                    tintableBackgroundView.setSupportBackgroundTintList(backgroundTint);
                }
                break;
            }
            case "app:backgroundTintMode": {
                if (view instanceof TintableBackgroundView) {
                    PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                    TintableBackgroundView tintableBackgroundView = (TintableBackgroundView) view;
                    tintableBackgroundView.setSupportBackgroundTintMode(tintMode);
                }
                break;
            }
        }
    }
}
