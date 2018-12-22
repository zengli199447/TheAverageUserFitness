package com.example.administrator.sportsFitness.model.http.api;



import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsFitness.model.bean.OuterLayerEntity;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Created by Administrator on 2017/10/30.
 * 网络请求
 * 添加请求头可以统一添加  单一添加请查看操作符
 * 统一添加查看 di 目录下 httmModule
 */

public interface MyApis {

    String HOST = DataClass.URL;

    /**
     * 登录
     *
     * @return
     */
    @GET("api.mingfa.php?")
    Flowable<LoginInfoNetBean> login(@QueryMap Map<String, String> map);
                
    /**
     * 上传文件
     *
     * @return
     */
    @POST("")
    Flowable<OuterLayerEntity> UpCasePicData(@Body MultipartBody multipartBody);

    /**
     * 返回提交状态
     */
    @POST("api.mingfa.php?")
    Flowable<UpLoadStatusNetBean> UpLoadStatus(@QueryMap Map<String, String> map);


}
