package prettyskin.core.handler.androidx;

import com.hyh.prettyskin.core.handler.ntv.RatingBarSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class AppCompatRatingBarSH extends RatingBarSH {

    public AppCompatRatingBarSH() {
        super(ViewAttrUtil.getDefStyleAttr_X("ratingBarStyle"));//android.support.v7.appcompat.R.attr.ratingBarStyle
    }

    public AppCompatRatingBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatRatingBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
