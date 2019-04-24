package com.hyh.prettyskin.sh;

import com.hyh.prettyskin.sh.RatingBarSH;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class AppCompatRatingBarSH extends RatingBarSH {

    public AppCompatRatingBarSH() {
        this(android.support.v7.appcompat.R.attr.ratingBarStyle);
    }

    public AppCompatRatingBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatRatingBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
