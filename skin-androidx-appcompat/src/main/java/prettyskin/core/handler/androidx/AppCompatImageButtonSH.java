package prettyskin.core.handler.androidx;

import com.hyh.prettyskin.core.handler.ntv.ImageButtonSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class AppCompatImageButtonSH extends ImageButtonSH {

    public AppCompatImageButtonSH() {
        this(ViewAttrUtil.getDefStyleAttr_X("imageButtonStyle"));//R.attr.imageButtonStyle
    }

    public AppCompatImageButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatImageButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
