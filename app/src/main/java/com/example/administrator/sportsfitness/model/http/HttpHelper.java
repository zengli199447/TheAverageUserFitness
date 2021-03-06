package com.example.administrator.sportsfitness.model.http;

import com.example.administrator.sportsfitness.model.bean.CardFormNetBean;
import com.example.administrator.sportsfitness.model.bean.CoachFormNetBean;
import com.example.administrator.sportsfitness.model.bean.CoachPrivateNetBean;
import com.example.administrator.sportsfitness.model.bean.CommentsNetBean;
import com.example.administrator.sportsfitness.model.bean.CourseFormNetBean;
import com.example.administrator.sportsfitness.model.bean.CourseTypeNetBean;
import com.example.administrator.sportsfitness.model.bean.DynamicDetailsNetBean;
import com.example.administrator.sportsfitness.model.bean.FriendsCircleNetBean;
import com.example.administrator.sportsfitness.model.bean.FriendsCircleRelatedNetBean;
import com.example.administrator.sportsfitness.model.bean.GymFormNetBean;
import com.example.administrator.sportsfitness.model.bean.GymTypeNetBean;
import com.example.administrator.sportsfitness.model.bean.HomePageInfoNetBean;
import com.example.administrator.sportsfitness.model.bean.HomePageNetBean;
import com.example.administrator.sportsfitness.model.bean.InfoAboutNetBean;
import com.example.administrator.sportsfitness.model.bean.InfoCoachPrivateNetBean;
import com.example.administrator.sportsfitness.model.bean.InfoCourseNetBean;
import com.example.administrator.sportsfitness.model.bean.InfoGymNetBean;
import com.example.administrator.sportsfitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsfitness.model.bean.MyTripNetBean;
import com.example.administrator.sportsfitness.model.bean.OrderFormNetBean;
import com.example.administrator.sportsfitness.model.bean.OuterLayerEntity;
import com.example.administrator.sportsfitness.model.bean.PraiseStatusNetBean;
import com.example.administrator.sportsfitness.model.bean.ReplySendNetBean;
import com.example.administrator.sportsfitness.model.bean.StudentFormNetBean;
import com.example.administrator.sportsfitness.model.bean.TagNetBean;
import com.example.administrator.sportsfitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsfitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsfitness.model.bean.UserInfoNetBean;
import com.example.administrator.sportsfitness.model.bean.VerificationCodeNetWork;
import com.example.administrator.sportsfitness.model.bean.WalletLogNetBean;
import com.example.administrator.sportsfitness.model.bean.WechatPayContentNetBean;
import com.example.administrator.sportsfitness.model.bean.ZfbPayContentNetBean;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;


/**
 * Created by Administrator on 2017/10/27.
 */


public interface HttpHelper {

    Flowable<LoginInfoNetBean> fetchLogin(Map map);

    Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody);

    Flowable<UpLoadStatusNetBean> UpLoadStatusNetBean(Map map);

    Flowable<VerificationCodeNetWork> Verification(Map map);

    Flowable<LoginInfoNetBean> Registered(Map map);

    Flowable<MyTripNetBean> MyTrip(Map map);

    Flowable<CourseTypeNetBean> CourseType(Map map);

    Flowable<GymFormNetBean> GymForm(Map map);

    Flowable<GymTypeNetBean> GymType(Map map);

    Flowable<OrderFormNetBean> OrderForm(Map map);

    Flowable<FriendsCircleNetBean> FriendsCircle(Map map);

    Flowable<DynamicDetailsNetBean> DynamicDetails(Map map);

    Flowable<ReplySendNetBean> ReplySend(Map map);

    Flowable<PraiseStatusNetBean> Praise(Map map);

    Flowable<UserInfoNetBean> UserInfo(Map map);

    Flowable<InfoAboutNetBean> InfoAbout(Map map);

    Flowable<HomePageInfoNetBean> HomePageInfo(Map map);

    Flowable<TagNetBean> Tag(Map map);

    Flowable<WalletLogNetBean> WalletLog(Map map);

    Flowable<FriendsCircleRelatedNetBean> FriendsCircleRelated(Map map);

    Flowable<CommentsNetBean> CommentsForm(Map map);

    Flowable<CourseFormNetBean> CourseForm(Map map);

    Flowable<CoachPrivateNetBean> CoachPrivate(Map map);

    Flowable<CardFormNetBean> CardForm(Map map);

    Flowable<InfoGymNetBean> InfoGym(Map map);

    Flowable<InfoCoachPrivateNetBean> InfoCoachPrivate(Map map);

    Flowable<InfoCourseNetBean> InfoCourse(Map map);

    Flowable<HomePageNetBean> HomePage(Map map);

    Flowable<TopUpNetBean> TopUp(Map map);

    Flowable<ZfbPayContentNetBean> ZfbPayContent(Map map);

    Flowable<WechatPayContentNetBean> WechatPayContent(Map map);

    Flowable<CoachFormNetBean> CoachForm(Map map);

    Flowable<StudentFormNetBean> StudentForm(Map map);


}
