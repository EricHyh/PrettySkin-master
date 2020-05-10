package com.hyh.prettyskin.sh;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ISkinHandlerMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class NativeSkinHandlerMap implements ISkinHandlerMap {

    private final Map<Class, ISkinHandler> mSkinHandlerMap = new ConcurrentHashMap<>();

    {
        mSkinHandlerMap.put(View.class, new ViewSH());
        mSkinHandlerMap.put(ViewGroup.class, new ViewGroupSH());

        mSkinHandlerMap.put(TextView.class, new TextViewSH());
        mSkinHandlerMap.put(Button.class, new ButtonSH());

        mSkinHandlerMap.put(ImageView.class, new ImageViewSH());
    }

    @Override
    public ISkinHandler get(Class viewClass) {
        ISkinHandler skinHandler = mSkinHandlerMap.get(viewClass);
        if (skinHandler != null) {
            return skinHandler;
        }
        switch (viewClass.getName()) {
            case "android.widget.EditText": {
                skinHandler = new EditTextSH();
                break;
            }
            case "android.widget.Chronometer": {
                skinHandler = new ChronometerSH();
                break;
            }
            case "android.widget.CheckedTextView": {
                skinHandler = new CheckedTextViewSH();
                break;
            }
            case "android.widget.TextClock": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    skinHandler = new TextClockSH();
                }
                break;
            }
            case "android.widget.CompoundButton": {
                skinHandler = new CompoundButtonSH();
                break;
            }
            case "android.widget.Switch": {
                skinHandler = new SwitchSH();
                break;
            }
            case "android.widget.CheckBox": {
                skinHandler = new CheckBoxSH();
                break;
            }
            case "android.widget.RadioButton": {
                skinHandler = new RadioButtonSH();
                break;
            }
            case "android.widget.ToggleButton": {
                skinHandler = new ToggleButtonSH();
                break;
            }
            case "android.widget.ImageButton": {
                skinHandler = new ImageButtonSH();
                break;
            }
            case "android.widget.ProgressBar": {
                skinHandler = new ProgressBarSH();
                break;
            }
            case "android.widget.AbsSeekBar": {
                skinHandler = new AbsSeekBarSH();
                break;
            }
            case "android.widget.SeekBar": {
                skinHandler = new SeekBarSH();
                break;
            }
            case "android.widget.RatingBar": {
                skinHandler = new RatingBarSH();
                break;
            }
            case "android.app.MediaRouteButton": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    skinHandler = new MediaRouteButtonSH();
                }
                break;
            }
            case "android.widget.QuickContactBadge": {
                skinHandler = new QuickContactBadgeSH();
                break;
            }
            case "android.widget.Toolbar": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    skinHandler = new ToolbarSH();
                }
                break;
            }
        }
        if (skinHandler != null) {
            mSkinHandlerMap.put(viewClass, skinHandler);
        }
        return skinHandler;
    }
}