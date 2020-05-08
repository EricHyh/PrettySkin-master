package com.hyh.prettyskin.demo.viewmodel;

import android.content.Context;

import com.hyh.prettyskin.utils.reflect.Reflect;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author Administrator
 * @description
 * @data 2020/4/11
 */
public class ContextViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;

    public ContextViewModelFactory(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return Reflect.from(modelClass)
                .constructor()
                .param(Context.class, mContext)
                .newInstance();
    }
}