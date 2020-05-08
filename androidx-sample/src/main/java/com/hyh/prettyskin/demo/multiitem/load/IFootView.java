package com.hyh.prettyskin.demo.multiitem.load;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * @author Administrator
 * @description
 * @data 2017/11/24
 */

public interface IFootView {

    @NonNull
    View onCreateView(LoadingItemFactory loadingItemFactory);

    void onLoadStateChanged(int oldState, int newState);

}