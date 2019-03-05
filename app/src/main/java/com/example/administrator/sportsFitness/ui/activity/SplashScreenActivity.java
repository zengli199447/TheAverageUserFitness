package com.example.administrator.sportsFitness.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.global.MyApplication;

/**
 * 作者：真理 Created by Administrator on 2019/3/4.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.FLAG = 0;
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

}
