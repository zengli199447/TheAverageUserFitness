package com.example.administrator.sportsfitness.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.utils.LogUtil;

/**
 * 作者：真理 Created by Administrator on 2018/12/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
@SuppressLint("AppCompatCustomView")
public class BubblesView extends TextView {

    private String TAG = getClass().getSimpleName();

    private int widthMeasureSpec;
    private int heightMeasureSpec;

    public BubblesView(Context context) {
        super(context);
    }

    public BubblesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubblesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置显示的数量
    public void setNum() {
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        LogUtil.e(TAG, "heightMeasureSpec : " + heightMeasureSpec);
        LogUtil.e(TAG, "widthMeasureSpec : " + widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 初始化画笔
        Paint paint = new Paint();
        // 设置抗锯齿
        paint.setAntiAlias(true);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clear_icon);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }


}
