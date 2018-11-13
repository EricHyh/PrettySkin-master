package prettyskin.core.handler.support.v7;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.core.handler.ntv.CompoundButtonSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class SwitchCompatSH extends CompoundButtonSH {

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("thumb");
        mSupportAttrNames.add("track");
        mSupportAttrNames.add("textOn");
        mSupportAttrNames.add("textOff");
        mSupportAttrNames.add("showText");
        mSupportAttrNames.add("thumbTextPadding");
        mSupportAttrNames.add("switchMinWidth");
        mSupportAttrNames.add("switchPadding");
        mSupportAttrNames.add("splitTrack");
        mSupportAttrNames.add("thumbTint");
        mSupportAttrNames.add("thumbTintMode");
        mSupportAttrNames.add("trackTint");
        mSupportAttrNames.add("trackTintMode");
        mSupportAttrNames.add("switchTextAppearance");
    }

    public SwitchCompatSH() {
        this(android.support.v7.appcompat.R.attr.switchStyle);
    }

    public SwitchCompatSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public SwitchCompatSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof SwitchCompat && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        if (super.isSupportAttrName(view, attrName)) {
            return super.parseAttrValue(view, set, attrName);
        } else {
            Class styleableClass = getStyleableClass();
            String styleableName = getStyleableName();
            return parseAttrValue(view, set, attrName, styleableClass, styleableName);
        }
    }

    private Class getStyleableClass() {
        try {
            return Class.forName("android.support.v7.appcompat.R$styleable");
        } catch (Exception e) {
            return null;
        }
    }

    private String getStyleableName() {
        return "SwitchCompat";
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof SwitchCompat) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            SwitchCompat switchCompat = (SwitchCompat) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "thumb": {
                    Drawable thumb = ViewAttrUtil.getDrawable(resources, type, value);
                    switchCompat.setThumbDrawable(thumb);
                    break;
                }
                case "track": {
                    Drawable track = ViewAttrUtil.getDrawable(resources, type, value);
                    switchCompat.setTrackDrawable(track);
                    break;
                }
                case "textOn": {
                    CharSequence textOn = ViewAttrUtil.getCharSequence(resources, type, value);
                    switchCompat.setTextOn(textOn);
                    break;
                }
                case "textOff": {
                    CharSequence textOff = ViewAttrUtil.getCharSequence(resources, type, value);
                    switchCompat.setTextOff(textOff);
                    break;
                }
                case "showText": {
                    boolean showText = ViewAttrUtil.getBoolean(resources, type, value);
                    switchCompat.setShowText(showText);
                    break;
                }
                case "thumbTextPadding": {
                    int thumbTextPadding = ViewAttrUtil.getInt(resources, type, value);
                    switchCompat.setThumbTextPadding(thumbTextPadding);
                    break;
                }
                case "switchMinWidth": {
                    int switchMinWidth = ViewAttrUtil.getInt(resources, type, value);
                    switchCompat.setSwitchMinWidth(switchMinWidth);
                    break;
                }
                case "switchPadding": {
                    int switchPadding = ViewAttrUtil.getInt(resources, type, value);
                    switchCompat.setSwitchPadding(switchPadding);
                    break;
                }
                case "splitTrack": {
                    boolean splitTrack = ViewAttrUtil.getBoolean(resources, type, value);
                    switchCompat.setSplitTrack(splitTrack);
                    break;
                }
                case "thumbTint": {
                    ColorStateList thumbTint = ViewAttrUtil.getColorStateList(resources, type, value);
                    switchCompat.setThumbTintList(thumbTint);
                    break;
                }
                case "thumbTintMode": {
                    PorterDuff.Mode thumbTintMode = ViewAttrUtil.getTintMode(type, value);
                    switchCompat.setThumbTintMode(thumbTintMode);
                    break;
                }
                case "trackTint": {
                    ColorStateList trackTint = ViewAttrUtil.getColorStateList(resources, type, value);
                    switchCompat.setTrackTintList(trackTint);
                    break;
                }
                case "trackTintMode": {
                    PorterDuff.Mode trackTintMode = ViewAttrUtil.getTintMode(type, value);
                    switchCompat.setTrackTintMode(trackTintMode);
                    break;
                }
                case "switchTextAppearance": {
                    int textAppearance = -1;
                    if (value != null) {
                        textAppearance = (int) value;
                    }
                    if (textAppearance != -1) {
                        switchCompat.setTextAppearance(context, textAppearance);
                    }
                    break;
                }
            }
        }
    }
}
