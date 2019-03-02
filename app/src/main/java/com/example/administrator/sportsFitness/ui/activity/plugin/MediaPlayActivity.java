package com.example.administrator.sportsFitness.ui.activity.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
import com.example.administrator.sportsFitness.widget.media.SuperVideoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/3/1.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MediaPlayActivity extends BaseActivity {

    @BindView(R.id.layout_title_bar)
    RelativeLayout layout_title_bar;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img)
    ImageView title_about_img;
    @BindView(R.id.placeholder_img)
    ImageView placeholder_img;
    @BindView(R.id.placeholder_img_)
    ImageView placeholder_img_;
    @BindView(R.id.video_view)
    SuperVideoView video_view;
    @BindView(R.id.line_title_bar)
    View line_title_bar;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    private BroadcastReceiver broadcastReceiver;
    private ShowDialog instance;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.PHOTO_SAVE:
                DataClass.AlbumVideoFileList.clear();
                finish();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_media_play;
    }

    @Override
    protected void initClass() {
        BroadcastReceiverActionMove();
        instance = ShowDialog.getInstance();
    }

    @Override
    protected void initData() {
        video_view.setVideoPath(DataClass.AlbumVideoFileList.get(0).getPath(), null);
    }

    @Override
    protected void initView() {
        title_name.setText("");
        title_about_img.setVisibility(View.VISIBLE);
        line_title_bar.setVisibility(View.GONE);
        title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.basket_icon));
    }

    @Override
    protected void initListener() {
        video_view.register(this);
    }

    @OnClick({R.id.title_about_img, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.title_about_img:
                instance.showConfirmOrNoDialog(this, getString(R.string.video_or_remover), EventCode.ONSTART, EventCode.PHOTO_SAVE, EventCode.PHOTO_CANCLE);
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    // 加入横(竖)屏要处理的代码
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mobile(true);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mobile(false);
        }
    }

    //应对全屏幕修改边距
    public void mobile(boolean status) {
        if (status) {
            layout_title_bar.setVisibility(View.GONE);
            placeholder_img.setVisibility(View.GONE);
            placeholder_img_.setVisibility(View.GONE);
        } else {
            layout_title_bar.setVisibility(View.VISIBLE);
            placeholder_img.setVisibility(View.VISIBLE);
            placeholder_img_.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 注册广播监听当前熄屏开屏解锁等操作
     */
    private void BroadcastReceiverActionMove() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                LogUtil.e(TAG, "onReceive");
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    LogUtil.e(TAG, "screen on");
                    video_view.refreshBufferStatus(true);
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    LogUtil.e(TAG, "screen off");
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    LogUtil.e(TAG, "screen unlock");
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    LogUtil.e(TAG, " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
                }
            }
        };
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        video_view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        video_view.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}
