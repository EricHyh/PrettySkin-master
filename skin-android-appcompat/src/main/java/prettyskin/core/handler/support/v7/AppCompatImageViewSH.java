package prettyskin.core.handler.support.v7;

import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.handler.ntv.ImageViewSH;

/**
 * @author Administrator
 * @description
 * @data 2018/11/12
 */

public class AppCompatImageViewSH extends ImageViewSH {

    public AppCompatImageViewSH() {
    }

    public AppCompatImageViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatImageViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }


    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = super.parseAttrValue(view, set, attrName);
        if (view instanceof AppCompatImageView) {
            
        }
        return attrValue;
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }
}
