package com.hyh.prettyskin.demo.utils;

import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.drawable.DynamicDrawable;

/**
 * @author Administrator
 * @description
 * @data 2020/4/13
 */
public class BindingAdapterUtil {

    @BindingAdapter({"app:imageUrl"})
    public static void setImageUrl(ImageView imageView, String url) {
        TypedArray typedArray = imageView.getContext().obtainStyledAttributes(R.styleable.PrettySkin);
        Drawable drawable = typedArray.getDrawable(R.styleable.PrettySkin_project_image_default);
        typedArray.recycle();
        if (drawable == null) {
            drawable = imageView.getResources().getDrawable(R.drawable.img_default_white_style);
        }
        DynamicDrawable imageDefault = new DynamicDrawable("project_image_default", drawable);
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(imageDefault)
                .error(imageDefault)
                .into(imageView);

        //project_image_default
    }
}