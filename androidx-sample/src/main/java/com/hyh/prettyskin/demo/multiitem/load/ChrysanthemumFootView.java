package com.hyh.prettyskin.demo.multiitem.load;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hyh.prettyskin.R;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;


/**
 * @author Administrator
 * @description
 * @data 2017/11/27
 */

public class ChrysanthemumFootView implements IFootView, View.OnClickListener {

    private static final String LOADING_TEXT = "加载中...";
    private static final String LOAD_SUCCESS_TEXT = "加载成功";
    private static final String LOAD_FAILURE_TEXT = "加载失败，请检查网络后重试～";
    private static final String LOAD_NO_MORE_TEXT = "我也是有底线的";

    private Context mContext;
    private LoadingItemFactory mLoadingItemFactory;
    private int mBackgroundColor;
    private int mTextColor;
    private int mLineColor;

    private TextView mRetryBtn;
    private ProgressBar mProgressBar;
    private TextView mTvPromptWording;
    private View mLeftLine;
    private View mRightLine;
    private View mView;

    public ChrysanthemumFootView(Context context, @ColorInt int backgroundColor, @ColorInt int textColor, @ColorInt int lineColor) {
        this.mContext = context;
        this.mBackgroundColor = backgroundColor;
        this.mTextColor = textColor;
        this.mLineColor = lineColor;
    }

    @NonNull
    @Override
    public View onCreateView(LoadingItemFactory loadingItemFactory) {
        this.mLoadingItemFactory = loadingItemFactory;

        mView = LayoutInflater.from(mContext).inflate(R.layout.multiitem_item_chrysanthemum_loading, null);
        mView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mView.setBackgroundColor(mBackgroundColor);
        mProgressBar = mView.findViewById(R.id.multiitem_chrysabthemum_loading_progressBar);
        mTvPromptWording = mView.findViewById(R.id.multiitem_chrysabthemum_loading_tv_prompt_wording);
        mRetryBtn = mView.findViewById(R.id.multiitem_chrysabthemum_loading_retry_btn);
        mLeftLine = mView.findViewById(R.id.multiitem_chrysabthemum_loading_left_line);
        mRightLine = mView.findViewById(R.id.multiitem_chrysabthemum_loading_right_line);
        mTvPromptWording.setTextColor(mTextColor);
        mRetryBtn.setTextColor(mTextColor);
        mLeftLine.setBackgroundColor(mLineColor);
        mRightLine.setBackgroundColor(mLineColor);
        Drawable drawable = getRetryBtnBackground();
        mRetryBtn.setBackgroundDrawable(drawable);
        mRetryBtn.setOnClickListener(this);
        mView.setVisibility(View.GONE);

        onLoadStateChanged(LoadState.LOAD_STATE_IDLE, LoadState.LOAD_STATE_IDLE);
        return mView;
    }

    @Override
    public void onLoadStateChanged(int oldState, int newState) {
        if (mView == null) return;
        mView.setVisibility(View.VISIBLE);
        switch (newState) {
            case LoadState.LOAD_STATE_IDLE:
            case LoadState.LOAD_STATE_REFRESHING:
            case LoadState.LOAD_STATE_LOADING:
                setVisibility(mRetryBtn, false);
                setVisibility(mLeftLine, false);
                setVisibility(mRightLine, false);
                setVisibility(mProgressBar, true);
                mTvPromptWording.setText(LOADING_TEXT);
                break;
            case LoadState.LOAD_STATE_LOAD_SUCCESS:
                setVisibility(mRetryBtn, false);
                setVisibility(mLeftLine, false);
                setVisibility(mRightLine, false);
                setVisibility(mProgressBar, false);
                mTvPromptWording.setText(LOAD_SUCCESS_TEXT);
                break;
            case LoadState.LOAD_STATE_LOAD_FAILURE:
                setVisibility(mLeftLine, false);
                setVisibility(mRightLine, false);
                setVisibility(mProgressBar, false);
                setVisibility(mRetryBtn, true);
                mTvPromptWording.setText(LOAD_FAILURE_TEXT);
                break;
            case LoadState.LOAD_STATE_NO_MORE:
                setVisibility(mProgressBar, false);
                setVisibility(mRetryBtn, false);
                setVisibility(mLeftLine, true);
                setVisibility(mRightLine, true);
                mTvPromptWording.setText(LOAD_NO_MORE_TEXT);
                break;
        }
    }


    private void setVisibility(View view, boolean visibility) {
        if (visibility) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    private Drawable getRetryBtnBackground() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        {
            GradientDrawable norDrawable = new GradientDrawable();
            norDrawable.setShape(GradientDrawable.RECTANGLE);
            int pressedColor = getPressedColor(mBackgroundColor);
            norDrawable.setColor(pressedColor);
            norDrawable.setStroke(1, mTextColor);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, norDrawable);
        }
        {
            GradientDrawable preDrawable = new GradientDrawable();
            preDrawable.setShape(GradientDrawable.RECTANGLE);
            preDrawable.setColor(mBackgroundColor);
            preDrawable.setStroke(1, mTextColor);
            stateListDrawable.addState(new int[]{}, preDrawable);
        }
        return stateListDrawable;
    }

    private int getPressedColor(int itemBackgroundColor) {
        try {
            String hexColorStr = Integer.toHexString(itemBackgroundColor);
            hexColorStr = "00000000".substring(0, 8 - hexColorStr.length()) + hexColorStr;
            int length = hexColorStr.length() / 2;
            String[] argbStr = new String[length];
            for (int index = 0; index < length; index++) {
                argbStr[index] = hexColorStr.substring(2 * index, 2 * (index + 1));
            }
            String alpha = argbStr[0];
            String red = argbStr[1];
            String green = argbStr[2];
            String blue = argbStr[3];
            int redValue = Integer.parseInt(red, 16);
            int greenValue = Integer.parseInt(green, 16);
            int blueValue = Integer.parseInt(blue, 16);
            redValue = (int) (redValue * 0.9f + 0.5f);
            greenValue = (int) (greenValue * 0.9f + 0.5f);
            blueValue = (int) (blueValue * 0.9f + 0.5f);
            red = keepTwo(Integer.toHexString(redValue));
            green = keepTwo(Integer.toHexString(greenValue));
            blue = keepTwo(Integer.toHexString(blueValue));
            String color = "#".concat(alpha).concat(red).concat(green).concat(blue);
            return Color.parseColor(color);
        } catch (Exception e) {
            return itemBackgroundColor;
        }
    }

    private String keepTwo(String hexString) {
        if (hexString.length() == 0) return "00";
        if (hexString.length() == 1) return "0" + hexString;
        return hexString;
    }

    @Override
    public void onClick(View v) {
        if (mLoadingItemFactory != null) {
            mLoadingItemFactory.executeLoadMore();
        }
    }
}