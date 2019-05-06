package com.hyh.prettyskin.sh;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ISkinHandlerMap;

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
        mSkinHandlerMap.put(View.class, new ViewGroupSH());
        mSkinHandlerMap.put(TextView.class, new TextViewSH());
        mSkinHandlerMap.put(Button.class, new ButtonSH());
    }

    @Override
    public Map<Class<? extends View>, ISkinHandler> get() {
        return mSkinHandlerMap;
    }
}
