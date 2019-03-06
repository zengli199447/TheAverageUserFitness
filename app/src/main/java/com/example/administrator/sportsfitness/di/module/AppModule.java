package com.example.administrator.sportsfitness.di.module;

import android.content.Context;

import com.example.administrator.sportsfitness.model.DataManager;
import com.example.administrator.sportsfitness.model.db.DBHelper;
import com.example.administrator.sportsfitness.model.db.GreenDaoHelper;
import com.example.administrator.sportsfitness.model.http.HttpHelper;
import com.example.administrator.sportsfitness.model.http.RetrofitHelper;
import com.example.administrator.sportsfitness.utils.ToastUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Administrator on 2017/10/27.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return this.context;
    }

    @Singleton
    @Provides
    ToastUtil provideToastUtil() {
        return new ToastUtil(this.context);
    }


    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Singleton
    @Provides
    DataManager provideDataManager(HttpHelper httpHelper, DBHelper DBHelper) {
        return new DataManager(httpHelper,DBHelper);
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(GreenDaoHelper realmHelper) {
        return realmHelper;
    }

}
