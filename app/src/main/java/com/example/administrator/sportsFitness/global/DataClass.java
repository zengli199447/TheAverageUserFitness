package com.example.administrator.sportsFitness.global;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.model.DataManager;
import com.example.administrator.sportsFitness.model.bean.ChangeViewLayoutParamsBean;
import com.example.administrator.sportsFitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsFitness.model.db.entity.LoginUserInfo;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class DataClass {

    private String TAG = getClass().getSimpleName();

    //TODO BaseUrl
    public static String URL = "http://jianshen.027perfect.com/";

    public static String File_URL = URL + "api/api.mingfa.php?";

    public static final String BOUNDARY = "--my_boundary--";

    public static String WEB_URL = URL + "pf_wx.php?act=appweb&";

    //登陆类型
    public static int LOGINTYPE = 1;//登录类型 1/手机登录  2/qq登录  3/wechat登录 4/sina登录

    //登陆
    public static String LOGIN = "user_login_get";
    //验证码
    public static String GET_CODE = "get_code";
    //用户注册
    public static String USER_REGISTER_SET = "user_register_set";
    //第三方登录
    public static String USER_THIRDLOGIN_SET = "user_thirdlogin_set";
    //手机绑定
    public static String PHONE_BIND_SET = "phone_bind_set";
    //密码修改
    public static String USER_PWD_SET = "user_pwd_set";
    //图片保存
    public static String IMAGE_SAVE_SET = "image_save_set";
    //视频保存
    public static String FILE_SAVE_SET = "file_save_set";

    //首页
    public static String HOMEPAGE_GET = "homepage_get";
    //我的行程
    public static String EVENT_LIST_GET = "event_list_get";
    //健身类别（1.课程、私教 2.健身房）
    public static String GET_CATE = "get_cate";
    //课程类别
    public static String COURSESCLASS_LIST_GET = "coursesclass_list_get";
    //健身房列表
    public static String SHOP_LIST_GET = "shop_list_get";
    //健身房类别
    public static String SHOPCLASS_LIST_GET = "shopclass_list_get";
    //动态列表
    public static String NEWS_LIST_GET = "news_list_get";
    //好友圈列表
    public static String FRIEND_LIST_GET = "friend_list_get";
    //评论列表
    public static String COMMENT_LIST_GET = "comment_list_get";
    //课程列表
    public static String KECHENG_LIST_GET = "kecheng_list_get";
    //私教列表
    public static String SIJIAO_LIST_GET = "sijiao_list_get";
    //健身卡列表
    public static String CARD_DETAIL_GET = "card_detail_get";
    //教练列表
    public static String TEACHER_LIST_GET = "teacher_list_get";
    //学员列表
    public static String STUDENT_LIST_GET = "student_list_get";


    //动态详情
    public static String NEWS_DETAIL_GET = "news_detail_get";
    //发送评论
    public static String REPLY_ADD_SET = "reply_add_set";
    //点赞/取消点赞
    public static String NEWS_ZAN_SET = "news_zan_set";
    //举报违规内容
    public static String NEWS_REPORT_SET = "news_report_set";
    //删除动态
    public static String NEWS_DEL_SET = "news_delete_set";
    //用户详情
    public static String USER_DETAIL_GET = "user_detail_get";
    //用户详情相关
    public static String USER_CENTER_GET = "user_center_get";
    //修改用户信息
    public static String USER_INFO_SET = "user_info_set";
    //钱包
    public static String MONEY_DETAIL_GET = "money_detail_get";
    //提现
    public static String TIXIAN_SET = "tixian_set";
    //添加好友
    public static String FRIEND_ADD_SET = "friend_add_set";
    //收藏/取消收藏
    public static String COLLECT_SET = "collect_set";
    //提现
    public static String MONEY_IN_SET = "money_in_set";
    //健身房详情
    public static String SHOP_DETAIL_GET = "shop_detail_get";
    //健身房/私教 预约
    public static String GROUP_JOIN_SET = "group_join_set";
    //私教详情
    public static String JIAOLIAN_DETAIL_GET = "jiaolian_detail_get";
    //课程详情
    public static String KECHENG_DETAIL_GET = "kecheng_detail_get";
    //办理健身卡
    public static String CARD_BUY_SET = "card_buy_set";


    //用户主页
    public static String USER_HOMEPAGE_GET = "user_homepage_get";
    //用户主页设置
    public static String USER_HOMEPAGE_SET = "user_homepage_set";
    //标签
    public static String TAG_LIST_GET = "tag_list_get";
    //转发动态
    public static String NEWS_ZHUAN_SET = "news_zhuan_set";


    //订单
    public static String ORDER_LIST_GET = "order_list_get";
    //取消订单
    public static String ORDER_CANCEL_GET = "order_cancel_get";
    //支付
    public static String ORDER_PAY_SET = "order_pay_set";


    //动态（发布/修改）
    public static String NEWS_SET = "news_set";

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
    //用户生日
    public static String BRITHDAY = "";
    //用户钱包余额
    public static String MONEY = "0";

    //相册集合
    public static ArrayList<AlbumFile> AlbumFileList = new ArrayList<>();
    //用户编辑动态内容
    public static String DYNAMICCONTENT = "";

    //视频集合
    public static ArrayList<AlbumFile> AlbumVideoFileList = new ArrayList<>();
    //用户编辑视频动态内容
    public static String VIDEODYNAMICCONTENT = "";

    //单张图片或封面信息
    public static HashMap<String, ChangeViewLayoutParamsBean> ChangeViewLayoutParamsHashMap = new HashMap<>();

    //图片、视频下载地址
    public static String DOWNLOAD_URL = "";

    //转发标记
    public static String FORWARDING_TAG = ":";

    //当前经纬度
    public static double LONGITUDE;
    public static double LATITUDE;

    public static int WINDOWS_WIDTH = 0;
    public static int WINDOWS_HEIGHT = 0;

    public static int DefaultInformationFlow = 12;

    //用户登录类型 1.普通用户 2.教练
    public static int AUDIT_STATUS_LOGIN_TYPE = 1;

    //支付选择类型状态
    public static int PAY_RETURN_STATUS = -1;


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

    public void initDBData() {
        if (dataManager.queryLoginUserInfo(DataClass.STANDARD_USER) != null) {
            LoginUserInfo loginUserInfo = dataManager.queryLoginUserInfo(DataClass.STANDARD_USER);
            loginUserInfo.setUserid(DataClass.USERID);
            loginUserInfo.setUsername(DataClass.UNAME);
            loginUserInfo.setUserPhoneNumber(DataClass.PHONE);
            loginUserInfo.setUserPassWord("");
            loginUserInfo.setUserStatus("");
            dataManager.UpDataLoginUserInfo(loginUserInfo);
        } else {
            LoginUserInfo loginUserInfo = new LoginUserInfo(DataClass.STANDARD_USER,
                    DataClass.USERID,
                    DataClass.UNAME,
                    DataClass.PHONE,
                    "",
                    "");
            dataManager.insertLoginUserInfo(loginUserInfo);
        }
    }

}
