package com.example.administrator.sportsfitness.ui.activity.plugin;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：真理 Created by Administrator on 2019/1/4.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CustomScanActivity extends Activity implements DecoratedBarcodeView.TorchListener, View.OnClickListener {

    @BindView(R.id.decorated_barcode_view)
    DecoratedBarcodeView decorated_barcode_view;
    @BindView(R.id.layout_title_bar)
    RelativeLayout layout_title_bar;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.line_title_bar)
    View line_title_bar;
    @BindView(R.id.flashlight)
    ImageView flashlight;

    private CaptureManager captureManager;
    private boolean isLightOn = false;

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return decorated_barcode_view.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);
        ButterKnife.bind(this);
        decorated_barcode_view.setTorchListener(this); // 如果没有闪光灯功能，就去掉相关按钮

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, decorated_barcode_view);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();

        refreshView();
    }

    @Override
    public void onTorchOn() {
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        isLightOn = false;
    }

    private void refreshView() {
        img_btn_black.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.btn_life_white_icon));
        title_name.setText(getString(R.string.qr_code));
        title_name.setTextColor(getResources().getColor(R.color.white));
        layout_title_bar.setBackground(getResources().getDrawable(R.drawable.transparent));
        line_title_bar.setVisibility(View.GONE);

        flashlight.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);

        if (!hasFlash()) {
            flashlight.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flashlight:
                if (isLightOn) {
                    decorated_barcode_view.setTorchOff();
                } else {
                    decorated_barcode_view.setTorchOn();
                }
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }


}
