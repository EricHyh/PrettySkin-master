package prettyskin.core.handler.androidx;

import com.hyh.prettyskin.core.handler.ntv.CheckBoxSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class AppCompatCheckBoxSH extends CheckBoxSH {

    public AppCompatCheckBoxSH() {
        this(ViewAttrUtil.getDefStyleAttr_X("checkboxStyle"));//R.attr.checkboxStyle
    }

    public AppCompatCheckBoxSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatCheckBoxSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
