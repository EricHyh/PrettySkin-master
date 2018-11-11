package prettyskin.core.handler.androidx;

import com.hyh.prettyskin.core.handler.ntv.ButtonSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/7
 */

public class AppCompatButtonSH extends ButtonSH {

    public AppCompatButtonSH() {
        this(ViewAttrUtil.getDefStyleAttr_X("buttonStyle"));//R.attr.buttonStyle
    }

    public AppCompatButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
