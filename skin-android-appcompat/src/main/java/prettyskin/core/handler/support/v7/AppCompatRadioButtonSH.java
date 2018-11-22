package prettyskin.core.handler.support.v7;

import com.hyh.prettyskin.core.handler.ntv.RadioButtonSH;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class AppCompatRadioButtonSH extends RadioButtonSH {

    public AppCompatRadioButtonSH() {
        this(android.support.v7.appcompat.R.attr.radioButtonStyle);
    }

    public AppCompatRadioButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatRadioButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
