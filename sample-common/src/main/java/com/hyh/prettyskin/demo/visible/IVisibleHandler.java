package com.hyh.prettyskin.demo.visible;

import android.view.View;

import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2019/6/4
 */

public interface IVisibleHandler {

    List<ItemVisibleStrategy> getItemVisibleStrategies();

    void onViewInvisible(View view, ItemVisibleStrategy strategy);

    void onViewVisible(View view, ItemVisibleStrategy strategy);
}