package com.example.administrator.sportsFitness.model.http.api;



import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.CardFormNetBean;
import com.example.administrator.sportsFitness.model.bean.CoachFormNetBean;
import com.example.administrator.sportsFitness.model.bean.CoachPrivateNetBean;
import com.example.administrator.sportsFitness.model.bean.CommentsNetBean;
import com.example.administrator.sportsFitness.model.bean.CourseFormNetBean;
import com.example.administrator.sportsFitness.model.bean.CourseTypeNetBean;
import com.example.administrator.sportsFitness.model.bean.DynamicDetailsNetBean;
import com.example.administrator.sportsFitness.model.bean.FriendsCircleNetBean;
import com.example.administrator.sportsFitness.model.bean.FriendsCircleRelatedNetBean;
import com.example.administrator.sportsFitness.model.bean.GymFormNetBean;
import com.example.administrator.sportsFitness.model.bean.GymTypeNetBean;
import com.example.administrator.sportsFitness.model.bean.HomePageInfoNetBean;
import com.example.administrator.sportsFitness.model.bean.HomePageNetBean;
import com.example.administrator.sportsFitness.model.bean.InfoAboutNetBean;
import com.example.administrator.sportsFitness.model.bean.InfoCoachPrivateNetBean;
import com.example.administrator.sportsFitness.model.bean.InfoCourseNetBean;
import com.example.administrator.sportsFitness.model.bean.InfoGymNetBean;
import com.example.administrator.sportsFitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsFitness.model.bean.MyTripNetBean;
import com.example.administrator.sportsFitness.model.bean.OrderFormNetBean;
import com.example.administrator.sportsFitness.model.bean.OuterLayerEntity;
import com.example.administrator.sportsFitness.model.bean.PraiseStatusNetBean;
import com.example.administrator.sportsFitness.model.bean.ReplySendNetBean;
import com.example.administrator.sportsFitness.model.bean.StudentFormNetBean;
import com.example.administrator.sportsFitness.model.bean.TagNetBean;
import com.example.administrator.sportsFitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.bean.UserInfoNetBean;
import com.example.administrator.sportsFitness.model.bean.VerificationCodeNetWork;
import com.example.administrator.sportsFitness.model.bean.WalletLogNetBean;
import com.example.administrator.sportsFitness.model.bean.WechatPayContentNetBean;
import com.example.administrator.sportsFitness.model.bean.ZfbPayContentNetBean;

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
    @GET("api/api.mingfa.php?")
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
    @POST("api/api.mingfa.php?")
    Flowable<UpLoadStatusNetBean> UpLoadStatus(@QueryMap Map<String, String> map);

    /**
     * 验证码
     */
    @POST("api/api.mingfa.php?")
    Flowable<VerificationCodeNetWork> Verification(@QueryMap Map<String, String> map);

    /**
     * 注册
     */
    @POST("api/api.mingfa.php?")
    Flowable<LoginInfoNetBean> Registered(@QueryMap Map<String, String> map);

    /**
     * 首页数据
     */
    @POST("api/api.mingfa.php?")
    Flowable<HomePageNetBean> HomePage(@QueryMap Map<String, String> map);

    /**
     * 我的行程
     */
    @POST("api/api.mingfa.php?")
    Flowable<MyTripNetBean> MyTrip(@QueryMap Map<String, String> map);

    /**
     * 课程分类
     */
    @POST("api/api.mingfa.php?")
    Flowable<CourseTypeNetBean> CourseType(@QueryMap Map<String, String> map);

    /**
     * 健身房列表
     */
    @POST("api/api.mingfa.php?")
    Flowable<GymFormNetBean> GymForm(@QueryMap Map<String, String> map);

    /**
     * 健身类型分类（此处佚名有异）
     */
    @POST("api/api.mingfa.php?")
    Flowable<GymTypeNetBean> GymType(@QueryMap Map<String, String> map);

    /**
     * 订单列表
     */
    @POST("api/api.mingfa.php?")
    Flowable<OrderFormNetBean> OrderForm(@QueryMap Map<String, String> map);

    /**
     * 好友圈
     */
    @POST("api/api.mingfa.php?")
    Flowable<FriendsCircleNetBean> FriendsCircle(@QueryMap Map<String, String> map);

    /**
     * 动态详情
     */
    @POST("api/api.mingfa.php?")
    Flowable<DynamicDetailsNetBean> DynamicDetails(@QueryMap Map<String, String> map);

    /**
     * 发送评论
     */
    @POST("api/api.mingfa.php?")
    Flowable<ReplySendNetBean> ReplySend(@QueryMap Map<String, String> map);

    /**
     * 点赞/取消点赞
     */
    @POST("api/api.mingfa.php?")
    Flowable<PraiseStatusNetBean> Praise(@QueryMap Map<String, String> map);

    /**
     * 用户信息获取
     */
    @POST("api/api.mingfa.php?")
    Flowable<UserInfoNetBean> UserInfo(@QueryMap Map<String, String> map);

    /**
     * 用户信息相关获取
     */
    @POST("api/api.mingfa.php?")
    Flowable<InfoAboutNetBean> InfoAbout(@QueryMap Map<String, String> map);

    /**
     * 用户主页详情
     */
    @POST("api/api.mingfa.php?")
    Flowable<HomePageInfoNetBean> HomePageInfo(@QueryMap Map<String, String> map);

    /**
     * 标签
     */
    @POST("api/api.mingfa.php?")
    Flowable<TagNetBean> Tag(@QueryMap Map<String, String> map);

    /**
     * 钱包
     */
    @POST("api/api.mingfa.php?")
    Flowable<WalletLogNetBean> WalletLog(@QueryMap Map<String, String> map);

    /**
     * 好友圈
     */
    @POST("api/api.mingfa.php?")
    Flowable<FriendsCircleRelatedNetBean> FriendsCircleRelated(@QueryMap Map<String, String> map);

    /**
     * 评价列表
     */
    @POST("api/api.mingfa.php?")
    Flowable<CommentsNetBean> CommentsForm(@QueryMap Map<String, String> map);

    /**
     * 课程列表
     */
    @POST("api/api.mingfa.php?")
    Flowable<CourseFormNetBean> CourseForm(@QueryMap Map<String, String> map);

    /**
     * 私教列表
     */
    @POST("api/api.mingfa.php?")
    Flowable<CoachPrivateNetBean> CoachPrivate(@QueryMap Map<String, String> map);

    /**
     * 健身卡列表
     */
    @POST("api/api.mingfa.php?")
    Flowable<CardFormNetBean> CardForm(@QueryMap Map<String, String> map);

    /**
     * 健身房详情
     */
    @POST("api/api.mingfa.php?")
    Flowable<InfoGymNetBean> InfoGym(@QueryMap Map<String, String> map);

    /**
     * 教练详情
     */
    @POST("api/api.mingfa.php?")
    Flowable<InfoCoachPrivateNetBean> InfoCoachPrivate(@QueryMap Map<String, String> map);

    /**
     * 课程详情
     */
    @POST("api/api.mingfa.php?")
    Flowable<InfoCourseNetBean> InfoCourse(@QueryMap Map<String, String> map);

    /**
     * 充值
     */
    @POST("api/api.mingfa.php?")
    Flowable<TopUpNetBean> TopUp(@QueryMap Map<String, String> map);

    /**
     * 支付宝支付
     */
    @POST("api/api.mingfa.php?")
    Flowable<ZfbPayContentNetBean> ZfbPayContent(@QueryMap Map<String, String> map);

    /**
     * 微信支付
     */
    @POST("api/api.mingfa.php?")
    Flowable<WechatPayContentNetBean> WechatPayContent(@QueryMap Map<String, String> map);

    /**
     * 教练列表
     */
    @POST("api/api.mingfa.php?")
    Flowable<CoachFormNetBean> CoachForm(@QueryMap Map<String, String> map);

    /**
     * 学员列表
     */
    @POST("api/api.mingfa.php?")
    Flowable<StudentFormNetBean> StudentForm(@QueryMap Map<String, String> map);


}
