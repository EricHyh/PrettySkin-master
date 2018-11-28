package prettyskin.core.handler.support.v7;

import com.hyh.prettyskin.core.handler.ntv.ButtonSH;

/**
 * @author Administrator
 * @description
 * @data 2018/11/7
 */

public class AppCompatButtonSH extends ButtonSH {

    public AppCompatButtonSH() {
        this(android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public AppCompatButtonSH(int defStyleAttr) {
        this(defStyleAttr,0);
    }

    public AppCompatButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}