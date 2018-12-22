package com.example.administrator.sportsFitness.model.http;

import com.example.administrator.sportsFitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsFitness.model.bean.OuterLayerEntity;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.http.api.MyApis;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/10/30.
 */

public class RetrofitHelper implements HttpHelper {

    private MyApis mMyApiService;

    @Inject
    public RetrofitHelper(MyApis myApiService) {
        this.mMyApiService = myApiService;
    }


    @Override
    public Flowable<LoginInfoNetBean> fetchLogin(Map map) {
        return mMyApiService.login(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody) {
        return mMyApiService.UpCasePicData(multipartBody);
    }

    @Override
    public Flowable<UpLoadStatusNetBean> UpLoadStatusNetBean(Map map) {
        return mMyApiService.UpLoadStatus(map);
    }




}
