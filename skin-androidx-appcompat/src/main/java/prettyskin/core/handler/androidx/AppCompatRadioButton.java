package prettyskin.core.handler.androidx;

import com.hyh.prettyskin.core.handler.ntv.RadioButtonSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class AppCompatRadioButton extends RadioButtonSH {

    public AppCompatRadioButton() {
        this(ViewAttrUtil.getDefStyleAttr_X("radioButtonStyle"));
    }

    public AppCompatRadioButton(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatRadioButton(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
