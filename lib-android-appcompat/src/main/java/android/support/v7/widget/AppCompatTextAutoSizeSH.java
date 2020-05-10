package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.support.v4.widget.AutoSizeableTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ValueType;

@SuppressLint("RestrictedApi")
public class AppCompatTextAutoSizeSH implements ISkinHandler {

    private int mDefStyleAttr;

    public AppCompatTextAutoSizeSH(int defStyleAttr) {
        mDefStyleAttr = defStyleAttr;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return (view instanceof AutoSizeableTextView && view instanceof TextView) &&
                (
                        TextUtils.equals(attrName, "autoSizeTextType")
                                || TextUtils.equals(attrName, "autoSizeStepGranularity")
                                || TextUtils.equals(attrName, "autoSizeMinTextSize")
                                || TextUtils.equals(attrName, "autoSizeMaxTextSize")
                                || TextUtils.equals(attrName, "autoSizePresetSizes")
                );
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = null;
        AutoSizeableTextView autoSizeableTextView = (AutoSizeableTextView) view;
        switch (attrName) {
            case "autoSizeTextType": {
               int autoSizeTextType = autoSizeableTextView.getAutoSizeTextType();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_INT, autoSizeTextType);
                break;
            }
            case "autoSizeStepGranularity": {
                int autoSizeStepGranularity = autoSizeableTextView.getAutoSizeStepGranularity();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_INT, autoSizeStepGranularity);
                break;
            }
            case "autoSizeMinTextSize": {
                int autoSizeMinTextSize = autoSizeableTextView.getAutoSizeMinTextSize();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_INT, autoSizeMinTextSize);
                break;
            }
            case "autoSizeMaxTextSize": {
                int autoSizeMaxTextSize = autoSizeableTextView.getAutoSizeMaxTextSize();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_INT, autoSizeMaxTextSize);
                break;
            }
            case "autoSizePresetSizes": {
                int[] autoSizeTextAvailableSizes = autoSizeableTextView.getAutoSizeTextAvailableSizes();
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_OBJECT, autoSizeTextAvailableSizes);
                break;
            }
        }
        return attrValue;
    }

    @Override
    public void finishParse() {
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        AutoSizeableTextView autoSizeableTextView = (AutoSizeableTextView) view;
        switch (attrName) {
            case "autoSizeTextType": {
                autoSizeableTextView.setAutoSizeTextTypeWithDefaults(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "autoSizeStepGranularity": {
                int autoSizeStepGranularity = attrValue.getTypedValue(int.class, -1);
                if (autoSizeStepGranularity > 0) {
                    int autoSizeMinTextSize = autoSizeableTextView.getAutoSizeMinTextSize();
                    int autoSizeMaxTextSize = autoSizeableTextView.getAutoSizeMaxTextSize();
                    autoSizeableTextView.setAutoSizeTextTypeUniformWithConfiguration(
                            autoSizeMinTextSize,
                            autoSizeMaxTextSize,
                            autoSizeStepGranularity,
                            TypedValue.COMPLEX_UNIT_PX);
                }
                break;
            }
            case "autoSizeMinTextSize": {
                int autoSizeMinTextSize = attrValue.getTypedValue(int.class, -1);
                if (autoSizeMinTextSize > 0) {
                    int autoSizeMaxTextSize = autoSizeableTextView.getAutoSizeMaxTextSize();
                    int autoSizeStepGranularity = autoSizeableTextView.getAutoSizeStepGranularity();
                    autoSizeableTextView.setAutoSizeTextTypeUniformWithConfiguration(
                            autoSizeMinTextSize,
                            autoSizeMaxTextSize,
                            autoSizeStepGranularity,
                            TypedValue.COMPLEX_UNIT_PX);
                }
                break;
            }
            case "autoSizeMaxTextSize": {
                int autoSizeMaxTextSize = attrValue.getTypedValue(int.class, -1);
                if (autoSizeMaxTextSize > 0) {
                    int autoSizeMinTextSize = autoSizeableTextView.getAutoSizeMinTextSize();
                    int autoSizeStepGranularity = autoSizeableTextView.getAutoSizeStepGranularity();
                    autoSizeableTextView.setAutoSizeTextTypeUniformWithConfiguration(
                            autoSizeMinTextSize,
                            autoSizeMaxTextSize,
                            autoSizeStepGranularity,
                            TypedValue.COMPLEX_UNIT_PX);
                }
                break;
            }
            case "autoSizePresetSizes": {
                int[] presetSizes = attrValue.getTypedValue(int[].class, null);
                if (presetSizes != null) {
                    autoSizeableTextView.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, TypedValue.COMPLEX_UNIT_PX);
                }
                break;
            }
        }
    }
}