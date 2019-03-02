package com.example.administrator.sportsFitness.widget.media;

import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.administrator.sportsFitness.utils.LogUtil;

/**
 * Created by Song on 2017/4/24.
 */

public class CustomVideoView extends VideoView implements View.OnTouchListener {

    String TAG = getClass().getSimpleName();
    private float lastX;
    private float lastY;
    private Context mContext;
    private StateListener mStateListener;
    private int maxVolume;
    private int screenWidth;
    private int screenHeight;
    private AudioManager audioManager;
    private WindowManager windowManager;
    private int downVol;//记录手指按下时的音量
    private Vibrator vibrator;//手机震动器
    private long startTime;
    private long endTime;
    private float endY;
    private float distanceY;
    private float endX;

    private int width;
    private int height;

    public interface StateListener {
        void changeVolumn(float detlaY);

        void changeBrightness(float detlaX);

        void hideHint();

        void controllerHideHint();
    }

    public void setStateListener(StateListener stateListener) {
        this.mStateListener = stateListener;
    }

    public void setMeasure(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public CustomVideoView(Context context) {
        this(context, null);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOnTouchListener(this);
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        // 屏幕宽度（像素）
        screenWidth = dm.widthPixels;
        // 屏幕宽度（像素）
        screenHeight = dm.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = getDefaultSize(1920, widthMeasureSpec);
//        int height = getDefaultSize(1080, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        if (this.width > 0 && this.height > 0) {
            width = this.width;
            height = this.height;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                downVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                endY = event.getY();
                endX = event.getX();
                distanceY = lastY - endY;
                endTime = System.currentTimeMillis();
                if ((endTime - startTime) > 300) {
                    if (lastX > screenWidth / 2) {
                        //屏幕右半部分上滑，声音变大，下滑，声音变小
                        int touchRang = Math.min(screenWidth, screenHeight);
                        int curvol = (int) (downVol + (distanceY / touchRang) * maxVolume);//考虑到横竖屏切换的问题
                        int volume = Math.min(Math.max(0, curvol), maxVolume);
                        mStateListener.changeVolumn(volume);
                    } else {
                        //屏幕左半部分上滑，亮度变大，下滑，亮度变小
                        final double FLING_MIN_DISTANCE = 0.5;
                        final double FLING_MIN_VELOCITY = 0.5;
                        if (distanceY > FLING_MIN_DISTANCE && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
                            mStateListener.changeBrightness(2);
                        }
                        if (distanceY < FLING_MIN_DISTANCE && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
                            mStateListener.changeBrightness(-2);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                endTime = System.currentTimeMillis();
                if ((endTime - startTime) < 100 && lastX - endX < 10 && lastY - endY < 10) {
                    LogUtil.e(TAG, "单击事件");
                    mStateListener.controllerHideHint();
                }
                mStateListener.hideHint();
                break;
        }
        return true;
    }
}
