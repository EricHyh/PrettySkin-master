package com.hyh.prettyskin.sh;

import android.app.MediaRouteButton;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsSeekBar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ISkinHandlerMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @description
 * @data 2019/4/10
 */

public class NativeSkinHandlerMap implements ISkinHandlerMap {

    private final Map<Class<? extends View>, ISkinHandler> mSkinHandlerMap = new ConcurrentHashMap<>();

    {
        mSkinHandlerMap.put(View.class, new ViewSH());
        mSkinHandlerMap.put(ViewGroup.class, new ViewGroupSH());

        mSkinHandlerMap.put(TextView.class, new TextViewSH());
        mSkinHandlerMap.put(EditText.class, new EditTextSH());
        mSkinHandlerMap.put(Chronometer.class, new ChronometerSH());
        mSkinHandlerMap.put(CheckedTextView.class, new CheckedTextViewSH());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mSkinHandlerMap.put(TextClock.class, new TextClockSH());
        }
        mSkinHandlerMap.put(Button.class, new ButtonSH());

        mSkinHandlerMap.put(CompoundButton.class, new CompoundButtonSH());

        mSkinHandlerMap.put(Switch.class, new SwitchSH());
        mSkinHandlerMap.put(CheckBox.class, new CheckBoxSH());
        mSkinHandlerMap.put(RadioButton.class, new RadioButtonSH());
        mSkinHandlerMap.put(ToggleButton.class, new ToggleButtonSH());

        mSkinHandlerMap.put(ImageView.class, new ImageViewSH());
        mSkinHandlerMap.put(ImageButton.class, new ImageButtonSH());

        mSkinHandlerMap.put(ProgressBar.class, new ProgressBarSH());
        mSkinHandlerMap.put(AbsSeekBar.class, new AbsSeekBarSH());
        mSkinHandlerMap.put(SeekBar.class, new SeekBarSH());
        mSkinHandlerMap.put(RatingBar.class, new RatingBarSH());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mSkinHandlerMap.put(MediaRouteButton.class, new MediaRouteButtonSH());
        }

        mSkinHandlerMap.put(QuickContactBadge.class, new QuickContactBadgeSH());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSkinHandlerMap.put(Toolbar.class, new ToolbarSH());
        }
    }

    @Override
    public Map<Class<? extends View>, ISkinHandler> get() {
        return mSkinHandlerMap;
    }
}
