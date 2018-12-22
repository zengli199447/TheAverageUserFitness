package com.example.administrator.sportsFitness.model.http;



import com.example.administrator.sportsFitness.model.manager.RetrofitUrlManager;

import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class UrlSettingTools {

    //修改BaseUrl;
    public static void UrlSetting(String url){
        HttpUrl CurrentUrl = RetrofitUrlManager.getInstance().getGlobalDomain();
        if (null == CurrentUrl || !CurrentUrl.toString().equals(url))
            RetrofitUrlManager.getInstance().setGlobalDomain(url);
    }

}
