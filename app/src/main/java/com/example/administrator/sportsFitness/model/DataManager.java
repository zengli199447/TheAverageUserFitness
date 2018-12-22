package com.example.administrator.sportsFitness.model;

import com.example.administrator.sportsFitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsFitness.model.bean.OuterLayerEntity;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.db.DBHelper;
import com.example.administrator.sportsFitness.model.db.entity.AppDBInfo;
import com.example.administrator.sportsFitness.model.db.entity.LoginUserInfo;
import com.example.administrator.sportsFitness.model.db.entity.SearchDBInfo;
import com.example.administrator.sportsFitness.model.http.HttpHelper;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;


/**
 * Created by Administrator on 2017/10/27.
 */

public class DataManager implements HttpHelper, DBHelper {
    String TAG = "DataManager";

    private HttpHelper mHttpHelper;
    private DBHelper mDbHelper;

    public DataManager(HttpHelper mHttpHelper, DBHelper mDbHelper) {
        this.mHttpHelper = mHttpHelper;
        this.mDbHelper = mDbHelper;
    }

    //---------------------------网络请求---------------------------------------

    @Override
    public Flowable<LoginInfoNetBean> fetchLogin(Map map) {
        return mHttpHelper.fetchLogin(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody) {
        return mHttpHelper.UpCasePicData(multipartBody);
    }

    @Override
    public Flowable<UpLoadStatusNetBean> UpLoadStatusNetBean(Map map) {
        return mHttpHelper.UpLoadStatusNetBean(map);
    }


    //---------------------------数据库查询---------------------------------------

    @Override
    public LoginUserInfo queryLoginUserInfo(String mUserName) {
        return mDbHelper.queryLoginUserInfo(mUserName);
    }

    @Override
    public List<SearchDBInfo> querySearchDBInfo(String mUserId) {
        return mDbHelper.querySearchDBInfo(mUserId);
    }

    @Override
    public List<LoginUserInfo> loadLoginUserInfo() {
        return mDbHelper.loadLoginUserInfo();
    }

    @Override
    public List<AppDBInfo> loadAppDBInfoDao() {
        return mDbHelper.loadAppDBInfoDao();
    }

    @Override
    public void insertLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.insertLoginUserInfo(mLoginUserInfo);
    }

    @Override
    public void insertAppDBInfoDao(AppDBInfo appDBInfo) {
        mDbHelper.insertAppDBInfoDao(appDBInfo);
    }

    @Override
    public void insertSearchDBInfo(SearchDBInfo searchDBInfo) {
        mDbHelper.insertSearchDBInfo(searchDBInfo);
    }

    @Override
    public void deleteLoginUserInfo(String mUserName) {
        mDbHelper.deleteLoginUserInfo(mUserName);
    }

    @Override
    public void deleteSearchDBInfo(String UserId) {
        mDbHelper.deleteSearchDBInfo(UserId);
    }

    @Override
    public void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.UpDataLoginUserInfo(mLoginUserInfo);
    }

}
