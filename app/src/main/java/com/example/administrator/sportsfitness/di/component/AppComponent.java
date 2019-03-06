package com.example.administrator.sportsfitness.di.component;

import android.content.Context;


import com.example.administrator.sportsfitness.di.module.AppModule;
import com.example.administrator.sportsfitness.di.module.HttpModule;
import com.example.administrator.sportsfitness.model.DataManager;
import com.example.administrator.sportsfitness.model.db.GreenDaoHelper;
import com.example.administrator.sportsfitness.model.http.RetrofitHelper;
import com.example.administrator.sportsfitness.utils.ToastUtil;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Administrator on 2017/10/27.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    Context getContext();

    ToastUtil getToastUtil();

    DataManager getDataManager(); //数据中心

    RetrofitHelper getRetrofitHelper();  //提供http的帮助类

    GreenDaoHelper greenDaoHelper();    //提供数据库帮助类

}
