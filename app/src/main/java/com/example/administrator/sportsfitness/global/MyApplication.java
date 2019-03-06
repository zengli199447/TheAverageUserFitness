package com.example.administrator.sportsfitness.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.administrator.sportsfitness.di.component.AppComponent;
import com.example.administrator.sportsfitness.di.component.DaggerAppComponent;
import com.example.administrator.sportsfitness.di.module.AppModule;
import com.example.administrator.sportsfitness.di.module.HttpModule;
import com.example.administrator.sportsfitness.server.InitializeService;
import com.example.administrator.sportsfitness.utils.LogUtil;
import com.example.administrator.sportsfitness.widget.UmPushBuilder;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;


/**
 * Created by Administrator on 2017/10/27.
 */

public class MyApplication extends Application {

    public static AppComponent appComponent;
    public static MyApplication instance;
    public static Set<Activity> allActivities;
    public static ExecutorService executorService;
    public static int FLAG = -1;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.isDebug = true;
        initThreadTools();
        InitializeService.start(this);
        //友盟初始化
        initUm();
        new UmPushBuilder(this).initPushSetting();
    }

    private void initUm() {
        UMConfigure.setLogEnabled(true);

        UMConfigure.init(this, AppKeyConfig.UMID, "umeng", UMConfigure.DEVICE_TYPE_PHONE, AppKeyConfig.UMPUSHID);

        PlatformConfig.setWeixin(AppKeyConfig.WXID, AppKeyConfig.WXID_ABOUT);

        PlatformConfig.setQQZone(AppKeyConfig.QQID, AppKeyConfig.QQID_ABOUT);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void initThreadTools() {
        executorService = ThreadPoolHelp.Builder.fixed(6)
                .name("globalTask")
                .builder();
    }


    public static AppComponent getAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .httpModule(new HttpModule())
                .build();
        return appComponent;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    //自杀
    public static void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
