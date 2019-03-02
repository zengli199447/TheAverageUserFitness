package com.example.administrator.sportsFitness.widget.media;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.global.MyApplication;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by Song on 2017/4/25.
 * 用于VideoView封装
 */
public class SuperVideoView extends RelativeLayout {

    private String TAG = getClass().getSimpleName();
    private CustomVideoView videoView;
    private SeekBar seekbarProgress;
    private ImageView btnController;
    private TextView tvCurrentProgress;
    private TextView tvTotalProgress;
    private ImageView ivVolume;
    private SeekBar seekbarVolume;
    private ImageView btnScreen;
    private FrameLayout flVolume;
    private FrameLayout flLight;
    private LinearLayout llyController;
    private RelativeLayout rlContainer;
    private AudioManager mAudioManager;
    private int screenWidth;
    private int screenHeight;
    private Context mContext;
    private View videoLayout;
    private Activity mActivity;
    private int videoPos;
    private int state = 0;
    private String mVideoPath;
    private boolean isVerticalScreen = true;
    private static final int UPDATE_PROGRESS = 3;
    private TextView volumeText;
    private TextView brightness;
    int intervalTime = 3;
    private ImageView cover;
    private Bitmap bitmap;
    private RelativeLayout rly_controller;
    private ImageView img_btn_black;
    private TextView title_name;
    private TextView share;
    private RelativeLayout cover_layout;
    private String mName;
    private ImageView play_btn;
    private ImageView buffer_logo;
    private TextView error_prompt;
    private boolean prepareStatus;
    private MyVolumeReceiver myVolumeReceiver;
    private AnimationDrawable animationDrawable;
    private boolean actionStart;
    private boolean bufferStart;
    private boolean completionStart;
    private int mVideoWidth;
    private int mVideoHeight;
    private float scale;
    private int showVideoHeight;

    public SuperVideoView(Context context) {
        super(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SuperVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SuperVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
        initView();
        initData();
        initListener();
        mHandler.sendEmptyMessage(0);
    }

    /**
     * @param activity
     */
    public void register(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 设置媒体路径
     *
     * @param path
     */
    public void setVideoPath(String path, String name) {
//        path = "http://192.168.2.197:8080/data/video/dzt5.mp4";
        this.mVideoPath = path;
        this.mName = name;
        if (path.startsWith("http") || path.startsWith("https")) {
            videoView.setVideoURI(Uri.parse(path));
            NetVideoBitmap(mVideoPath);
        } else {
            videoView.setVideoURI(Uri.parse(path));
        }
//        title_name.setText(name);
    }

    private void init() {
        screenWidth = ScreenUtils.getScreenWidth(mContext);
        screenHeight = ScreenUtils.getScreenHeight(mContext);
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        myVolumeReceiver = new MyVolumeReceiver();
        mContext.registerReceiver(myVolumeReceiver, filter);
    }

    /**
     * UI 初始化
     */
    private void initView() {
        videoLayout = LayoutInflater.from(mContext).inflate(R.layout.video_layout, this, true);
        flVolume = (FrameLayout) videoLayout.findViewById(R.id.fl_volume);
        volumeText = (TextView) videoLayout.findViewById(R.id.volume);
        flLight = (FrameLayout) videoLayout.findViewById(R.id.fl_light);
        brightness = (TextView) videoLayout.findViewById(R.id.brightness);
        videoView = (CustomVideoView) videoLayout.findViewById(R.id.videoView);
        seekbarProgress = (SeekBar) videoLayout.findViewById(R.id.seekbar_progress);
        seekbarVolume = (SeekBar) videoLayout.findViewById(R.id.seekbar_volume);
        btnController = (ImageView) videoLayout.findViewById(R.id.btn_controller);
        btnScreen = (ImageView) videoLayout.findViewById(R.id.btn_screen);
        tvCurrentProgress = (TextView) videoLayout.findViewById(R.id.tv_currentProgress);
        tvTotalProgress = (TextView) videoLayout.findViewById(R.id.tv_totalProgress);
        ivVolume = (ImageView) videoLayout.findViewById(R.id.iv_volume);
        llyController = (LinearLayout) videoLayout.findViewById(R.id.lly_controller);
        rlContainer = (RelativeLayout) videoLayout.findViewById(R.id.rl_container);
        cover = (ImageView) videoLayout.findViewById(R.id.cover);

        rly_controller = (RelativeLayout) videoLayout.findViewById(R.id.rly_controller);
        img_btn_black = (ImageView) videoLayout.findViewById(R.id.img_btn_black);
        title_name = (TextView) videoLayout.findViewById(R.id.title_name);
        share = (TextView) videoLayout.findViewById(R.id.share);

        cover_layout = (RelativeLayout) videoLayout.findViewById(R.id.cover_layout);
        play_btn = (ImageView) videoLayout.findViewById(R.id.play_btn);
        buffer_logo = (ImageView) videoLayout.findViewById(R.id.buffer_logo);
        error_prompt = (TextView) videoLayout.findViewById(R.id.error_prompt);

    }


    /**
     * 初始化数据
     */
    private void initData() {
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekbarVolume.setProgress(currentVolume);
    }

    /**
     * 加载中loading
     *
     * @param status
     */
    public void refreshBufferStatus(final boolean status) {
        if (animationDrawable == null)
            try {
                buffer_logo.setImageResource(R.drawable.progress_white_animlist);
                animationDrawable = (AnimationDrawable) buffer_logo.getDrawable();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        if (status) {
            cover_layout.setVisibility(VISIBLE);
            buffer_logo.setVisibility(View.VISIBLE);
            play_btn.setVisibility(GONE);
            animationDrawable.start();
        } else {
            cover_layout.setVisibility(GONE);
            buffer_logo.setVisibility(View.GONE);
            play_btn.setVisibility(VISIBLE);
            animationDrawable.stop();
        }
    }

    /**
     * 注册事件
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initListener() {

        btnScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVerticalScreen) {
                    if (currentProgressListener != null)
                        currentProgressListener.onScreenListener();
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

        btnController.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    btnController.setImageResource(R.drawable.btn_play_style);
                    videoView.pause();
                    mHandler.removeMessages(UPDATE_PROGRESS);
                    cover_layout.setVisibility(VISIBLE);
                } else {
                    btnController.setImageResource(R.drawable.btn_pause_style);
                    videoView.start();
                    mHandler.sendEmptyMessage(UPDATE_PROGRESS);
                    if (state == 0) state = 1;
                    cover_layout.setVisibility(GONE);
                }
            }
        });

        /**
         * 状态事件
         */
        videoView.setStateListener(new CustomVideoView.StateListener() {
            /**
             * 改变声音大小
             */
            @Override
            public void changeVolumn(float detlaY) {
                if (flVolume.getVisibility() == View.GONE) {
                    flVolume.setVisibility(View.VISIBLE);
                }
                int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                int volume = (int) detlaY;
                double v = (Double.valueOf(volume) / Double.valueOf(maxVolume)) * 100;
                volumeText.setText(new StringBuffer().append(String.format("%.0f", v)).append("%").toString());
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
                seekbarVolume.setProgress(volume);
            }

            /**
             * 改变屏幕亮度
             * @param detlaX
             */
            @Override
            public void changeBrightness(float detlaX) {
                if (flLight.getVisibility() == View.GONE) {
                    flLight.setVisibility(View.VISIBLE);
                }
                WindowManager.LayoutParams wml = mActivity.getWindow().getAttributes();
                float screenBrightness = wml.screenBrightness;
                wml.screenBrightness = screenBrightness + (detlaX / 100);
                if (wml.screenBrightness > 1.0f) {
                    wml.screenBrightness = 1.0f;
                } else if (wml.screenBrightness < 0.01f) {
                    wml.screenBrightness = 0.01f;
                }
                brightness.setText(new StringBuffer().append(String.format("%.0f", wml.screenBrightness * 100)).append("%").toString());
                mActivity.getWindow().setAttributes(wml);
            }

            @Override
            public void hideHint() {
                if (flLight.getVisibility() == View.VISIBLE) {
                    flLight.setVisibility(GONE);
                }

                if (flVolume.getVisibility() == View.VISIBLE) {
                    flVolume.setVisibility(GONE);
                }
            }

            @Override
            public void controllerHideHint() {
                setControllerHideHint();
            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    Log.e(TAG, "缓冲中...");
                    bufferStart = false;
                    refreshBufferStatus(true);
                } else {
                    Log.e(TAG, "缓冲结束...");
                    bufferStart = true;
                    refreshBufferStatus(false);
                    if (currentProgressListener != null)
                        currentProgressListener.onCurrentProgressListener(0, videoView);
                }
                return true;
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.e(TAG, "准备...");
                Measure(mediaPlayer);
                prepareStatus = true;
                btnController.setImageResource(R.drawable.btn_play_style);
                refreshBufferStatus(false);
                cover_layout.setVisibility(VISIBLE);
                if (actionStart) {
                    videoView.start();
                    mHandler.sendEmptyMessage(UPDATE_PROGRESS);
                    if (state == 0) state = 1;
                }
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e(TAG, "播放完成...");
                completionStart = true;
                cover_layout.setVisibility(VISIBLE);
                cover.setVisibility(VISIBLE);
            }
        });

        play_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prepareStatus) {
                    btnController.setImageResource(R.drawable.btn_pause_style);
                    videoView.start();
                    mHandler.sendEmptyMessage(UPDATE_PROGRESS);
                    if (state == 0) state = 1;
                } else {
                    actionStart = true;
                    refreshBufferStatus(true);
                }
                completionStart = false;
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                LogUtil.e(TAG, "onError extra : " + extra);
                LogUtil.e(TAG, "onError what : " + what);
                switch (what) {
                    case MediaPlayer.MEDIA_ERROR_IO:
                        if (mp.isPlaying()) {
                            mp.stop();
                            mp.release();
                            error_prompt.post(new Runnable() {
                                @Override
                                public void run() {
                                    error_prompt.setVisibility(VISIBLE);
                                }
                            });
                        }
                        break;
                }
                return true;
            }
        });

        /**
         * 声音控制
         */
        seekbarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    // We're not interested in programmatically generated changes to
                    // the progress bar's position.
                    return;
                }
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        /**
         * 播放进度
         */
        seekbarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    // We're not interested in programmatically generated changes to
                    // the progress bar's position.
                    return;
                }
                updateTextViewFormat(tvCurrentProgress, progress);
                //缓冲进度
                int bufferPercentage = videoView.getBufferPercentage();
                seekBar.setSecondaryProgress(bufferPercentage);
                if (!videoView.isPlaying())
                    mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 暂停刷新
                mHandler.removeMessages(UPDATE_PROGRESS);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (state != 0) {
                    mHandler.sendEmptyMessage(UPDATE_PROGRESS);
                }
                videoView.seekTo(seekBar.getProgress());
                Log.e(TAG, "停止...");
            }
        });

        img_btn_black.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVerticalScreen) {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

        rlContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setControllerHideHint();
            }
        });

    }

    private void setControllerHideHint() {
        int visibility = llyController.getVisibility();
        intervalTime = 3;
        if (visibility != 0) {
            llyController.setVisibility(VISIBLE);
            rly_controller.setVisibility(VISIBLE);
            mHandler.sendEmptyMessage(0);
//            btnController.setImageResource(R.drawable.btn_pause_style);
        }
    }

    /**
     * 屏幕状态改变
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            isVerticalScreen = true;
//            ivVolume.setVisibility(View.GONE);
//            seekbarVolume.setVisibility(View.GONE);
            img_btn_black.setVisibility(View.GONE);
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dipToPx(mContext, mContext.getResources().getInteger(R.integer.screen_height)));
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//            SystemUtil.showBottomUIMenu((Activity) mContext);
            refreshPortraitScreen(SystemUtil.dp2px(mActivity, mContext.getResources().getInteger(R.integer.screen_height)));
        } else {
            isVerticalScreen = false;
//            ivVolume.setVisibility(View.VISIBLE);
//            seekbarVolume.setVisibility(View.VISIBLE);
            img_btn_black.setVisibility(View.VISIBLE);
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //移除半屏状态
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            SystemUtil.hideBottomUIMenu((Activity) mContext);
            refreshPortraitScreen(SystemUtil.dp2px(mActivity, DataClass.WINDOWS_WIDTH));
        }
    }

    /**
     * 切换尺寸
     *
     * @param width
     * @param height
     */
    public void setVideoViewScale(int width, int height) {

        ViewGroup.LayoutParams videoViewLayoutParams = videoView.getLayoutParams();
        videoViewLayoutParams.width = width;
        videoViewLayoutParams.height = height;
        videoView.setLayoutParams(videoViewLayoutParams);

        LayoutParams rlContainerLayoutParams = (LayoutParams) rlContainer.getLayoutParams();
        rlContainerLayoutParams.width = width;
        rlContainerLayoutParams.height = height;
        rlContainer.setLayoutParams(rlContainerLayoutParams);

    }

    /**
     * 格式化时间进度
     */
    private void updateTextViewFormat(TextView tv, int m) {

        String result;
        // 毫秒转成秒
        int second = m / 1000;
        int hour = second / 3600;
        int minute = second % 3600 / 60;
        int ss = second % 60;

        if (hour != 0) {
            result = String.format("%02d:%02d:%02d", hour, minute, ss);
        } else {
            result = String.format("%02d:%02d", minute, ss);
        }
        tv.setText(result);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    if (videoView.isPlaying() && bufferStart) {
                        cover_layout.setVisibility(GONE);
                    } else {
                        cover_layout.setVisibility(VISIBLE);
                    }

                    // 获取当前时间
                    int currentTime = videoView.getCurrentPosition();
                    // 获取总时间
                    int totalTime = videoView.getDuration() - 100;
                    if (currentTime >= totalTime) {
                        completionStart = true;
                        videoView.pause();
                        videoView.seekTo(0);
                        seekbarProgress.setProgress(0);
                        btnController.setImageResource(R.drawable.btn_play_style);
                        updateTextViewFormat(tvCurrentProgress, 0);
                        mHandler.removeMessages(UPDATE_PROGRESS);
                    } else {
                        seekbarProgress.setMax(totalTime);
                        seekbarProgress.setProgress(currentTime);
                        updateTextViewFormat(tvCurrentProgress, currentTime);
                        updateTextViewFormat(tvTotalProgress, totalTime);
                        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 100);
                    }
                    if (completionStart) {
                        cover.setVisibility(VISIBLE);
                    } else {
                        cover.setVisibility(GONE);
                    }
                    break;
                case 0:
                    if (intervalTime == 0) {
                        llyController.setVisibility(GONE);
                        rly_controller.setVisibility(GONE);
                    } else {
                        intervalTime = intervalTime - 1;
                        mHandler.sendEmptyMessageDelayed(0, 999);
                    }
                    break;
                case 1:
                    cover_layout.setVisibility(VISIBLE);
                    break;
                case 2:
                    cover.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    public void onPause() {
        videoPos = videoView.getCurrentPosition();
        videoView.stopPlayback();
        mHandler.removeMessages(UPDATE_PROGRESS);
    }

    public void onResume() {
        videoView.seekTo(videoPos);
        videoView.resume();
    }

    /**
     * 设置播放进度条样式
     *
     * @param drawable
     */
    public void setProgressBg(Drawable drawable) {
        seekbarProgress.setProgressDrawable(drawable);
    }

    /**
     * 设置声音控制条样式
     *
     * @param drawable
     */
    public void setVolumeBg(Drawable drawable) {
        seekbarVolume.setProgressDrawable(drawable);
    }

    class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                // 当前的媒体音量
                int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                seekbarVolume.setProgress(currentVolume);
            }
        }
    }

    //第一帧封面
    public void NetVideoBitmap(final String path) {
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                try {
                    //根据url获取缩略图
                    retriever.setDataSource(path, new HashMap());
                    //获得第一帧图片
                    bitmap = retriever.getFrameAtTime();
                    mHandler.sendEmptyMessage(2);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    retriever.release();
                }
            }
        });
    }

    //重绘videoView宽高
    private void Measure(MediaPlayer mp) {
        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                //FixMe 获取视频资源的宽度
                mVideoWidth = mp.getVideoWidth();
                //FixMe 获取视频资源的高度
                mVideoHeight = mp.getVideoHeight();
                scale = (float) mVideoWidth / (float) mVideoHeight;
                refreshPortraitScreen(showVideoHeight == 0 ? SystemUtil.dp2px(mActivity, 200) : showVideoHeight);
            }
        });
    }

    //重新刷新 竖屏显示的大小  树屏显示以宽度为准
    public void refreshPortraitScreen(int height) {
        if (height == 0) {
            height = showVideoHeight;
        }
        LogUtil.e(TAG, "height : " + height);
        if (mVideoHeight > 0 && mVideoWidth > 0) {
            //FixMe 拉伸比例
            mVideoWidth = (int) (height * scale);
            //FixMe 设置surfaceview画布大小
            videoView.getHolder().setFixedSize(mVideoWidth, height);
            //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
            videoView.setMeasure(mVideoWidth, height);
            //FixMe 请求调整
            videoView.requestLayout();
        }
    }

    public void unRegisterReceiver() {
        mContext.unregisterReceiver(myVolumeReceiver);
    }

    public interface CurrentProgressListener {
        void onCurrentProgressListener(int seekTo, CustomVideoView videoView);

        void onScreenListener();
    }

    private CurrentProgressListener currentProgressListener;

    public void setCurrentProgressListener(CurrentProgressListener currentProgressListener) {
        this.currentProgressListener = currentProgressListener;
    }


}
