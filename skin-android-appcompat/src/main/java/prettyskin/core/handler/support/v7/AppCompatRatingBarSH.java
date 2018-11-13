package prettyskin.core.handler.support.v7;

import com.hyh.prettyskin.core.handler.ntv.RatingBarSH;

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
