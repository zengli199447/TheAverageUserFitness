package com.example.administrator.sportsFitness.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ImmersedTextView extends TextView {

    private int mViewWidth = 0;
    private TextPaint mPaint;
    private Rect mTextBound = new Rect();
    private LinearGradient mLinearGradient;

    public ImmersedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[]{0xFF029793, 0xFF36c9c4,0xFF85efeb}, null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 3 - mTextBound.width() / 3, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }

}
