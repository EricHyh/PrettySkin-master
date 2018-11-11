package prettyskin.core.handler.androidx;

import com.hyh.prettyskin.core.handler.ntv.SeekBarSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class AppCompatSeekBarSH extends SeekBarSH {

    public AppCompatSeekBarSH() {
        super(ViewAttrUtil.getDefStyleAttr_X("seekBarStyle"));//android.support.v7.appcompat.R.attr.ratingBarStyle
    }

    public AppCompatSeekBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatSeekBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
