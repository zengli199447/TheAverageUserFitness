package com.example.administrator.sportsFitness.global;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.model.DataManager;
import com.example.administrator.sportsFitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsFitness.model.db.entity.LoginUserInfo;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class DataClass {

    private String TAG = getClass().getSimpleName();

    //TODO BaseUrl
    public static String URL = "http://mengyh.027perfect.com/api/";

    //登陆类型
    public static int LOGINTYPE = 1;//登录类型 1/手机登录  2/qq登录  3/wechat登录 4/sina登录

    //登陆
    public static String LOGIN = "user_login_get";


    //用户登陆ID
    public static String USERID = ""; //10007 10005
    //账号标示
    public static String STANDARD_USER = "admin";
    //用户名
    public static String UNAME = "小鸟游";
    //用户年龄
    public static String AGE = "";
    //用户电话
    public static String PHONE = "";
    //用户头像
    public static String USERPHOTO = "http://wx1.sinaimg.cn/orj360/006pnLoLgy1ft6yichmarj30j60j675x.jpg";
    //当前城市
    public static String CITY = "武汉市";
    //用户性别
    public static String GENDER = "女";
    //用户密码
    public static String PASSWORD = "";
    //相册集合
    public static ArrayList<AlbumFile> AlbumFileList = new ArrayList<>();
    //用户编辑动态内容
    public static String DYNAMICCONTENT = "";


    //当前经纬度
    public static double LONGITUDE;
    public static double LATITUDE;

    public static int WINDOWS_WIDTH = 0;
    public static int WINDOWS_HEIGHT = 0;

    public static int DefaultInformationFlow = 11;

    public DataClass() {

    }

    public DataManager dataManager;

    public DataClass(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    //引导页
    public ArrayList<Integer> getWelcomeBannerList() {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(R.drawable.welcome1);
        integers.add(R.drawable.welcome2);
        integers.add(R.drawable.welcome3);
        return integers;
    }


}
