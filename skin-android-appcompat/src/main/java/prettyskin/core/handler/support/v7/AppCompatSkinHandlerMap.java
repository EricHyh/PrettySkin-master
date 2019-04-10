package prettyskin.core.handler.support.v7;

import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.core.handler.ISkinHandlerMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @description
 * @data 2019/4/10
 */

public class AppCompatSkinHandlerMap implements ISkinHandlerMap {

    private final Map<Class<? extends View>, ISkinHandler> mSkinHandlerMap = new ConcurrentHashMap<>();

    {
        mSkinHandlerMap.put(AppCompatButton.class, new AppCompatButtonSH());
    }

    @Override
    public Map<Class<? extends View>, ISkinHandler> get() {
        return mSkinHandlerMap;
    }
}