package com.hyh.prettyskin.core.handler;

import android.view.View;

import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2019/4/10
 */

public interface ISkinHandlerMap {

    Map<Class<? extends View>, ISkinHandler> get();

}
