package com.hyh.prettyskin.core.handler.ntv;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.core.handler.ISkinHandlerMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @description
 * @data 2019/4/10
 */

public class NativeSkinHandlerMap implements ISkinHandlerMap {

    private final Map<Class<? extends View>, ISkinHandler> mSkinHandlerMap = new ConcurrentHashMap<>();

    {
        mSkinHandlerMap.put(View.class, new ViewSH());
        mSkinHandlerMap.put(TextView.class, new TextViewSH());
        mSkinHandlerMap.put(Button.class, new ButtonSH());
    }

    @Override
    public Map<Class<? extends View>, ISkinHandler> get() {
        return mSkinHandlerMap;
    }
}
