package com.hyh.prettyskin.demo.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyh.prettyskin.R;

/**
 * @author Administrator
 * @description
 * @data 2020/4/13
 */
public class BindingAdapterUtil {

    @BindingAdapter({"app:imageUrl"})
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image_default_light)
                .error(R.drawable.image_default_light)
                .into(imageView);
    }
}