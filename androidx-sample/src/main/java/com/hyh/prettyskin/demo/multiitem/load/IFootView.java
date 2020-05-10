package com.hyh.prettyskin.demo.multiitem.load;

import android.view.View;

import androidx.annotation.NonNull;



public interface IFootView {

    @NonNull
    View onCreateView(LoadingItemFactory loadingItemFactory);

    void onLoadStateChanged(int oldState, int newState);

}