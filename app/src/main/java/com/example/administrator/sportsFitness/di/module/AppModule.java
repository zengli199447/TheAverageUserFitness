package com.example.administrator.sportsFitness.di.module;

import android.content.Context;

import com.example.administrator.sportsFitness.model.DataManager;
import com.example.administrator.sportsFitness.model.db.DBHelper;
import com.example.administrator.sportsFitness.model.db.GreenDaoHelper;
import com.example.administrator.sportsFitness.model.http.HttpHelper;
import com.example.administrator.sportsFitness.model.http.RetrofitHelper;
import com.example.administrator.sportsFitness.utils.ToastUtil;

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
