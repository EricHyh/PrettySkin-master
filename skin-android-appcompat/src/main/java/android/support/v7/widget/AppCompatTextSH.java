package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.handler.ISkinHandler;

/**
 * @author Administrator
 * @description
 * @data 2018/11/13
 */

public class AppCompatTextSH implements ISkinHandler {


    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return false;
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        TextView textView = (TextView) view;
        ColorStateList textColors = textView.getTextColors();
        ColorStateList hintTextColors = textView.getHintTextColors();
        ColorStateList linkTextColors = textView.getLinkTextColors();
        Typeface typeface = textView.getTypeface();


        return null;
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {

    }
}
