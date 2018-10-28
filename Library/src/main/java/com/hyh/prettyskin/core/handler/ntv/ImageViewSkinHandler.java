package com.hyh.prettyskin.core.handler.ntv;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/10/28.
 */

public class ImageViewSkinHandler extends ViewSkinHandler {


    private List<String> mSupportAttrNames = new ArrayList<>();

    {
    }

    public ImageViewSkinHandler() {
        super();
    }

    public ImageViewSkinHandler(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ImageViewSkinHandler(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return super.isSupportAttrName(view, attrName);
    }
}
