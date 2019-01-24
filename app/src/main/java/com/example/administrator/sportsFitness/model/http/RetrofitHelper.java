package com.example.administrator.sportsFitness.model.http;

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

    @Override
    public Flowable<VerificationCodeNetWork> Verification(Map map) {
        return mMyApiService.Verification(map);
    }

    @Override
    public Flowable<LoginInfoNetBean> Registered(Map map) {
        return mMyApiService.Registered(map);
    }

    @Override
    public Flowable<MyTripNetBean> MyTrip(Map map) {
        return mMyApiService.MyTrip(map);
    }

    @Override
    public Flowable<CourseTypeNetBean> CourseType(Map map) {
        return mMyApiService.CourseType(map);
    }

    @Override
    public Flowable<GymFormNetBean> GymForm(Map map) {
        return mMyApiService.GymForm(map);
    }

    @Override
    public Flowable<GymTypeNetBean> GymType(Map map) {
        return mMyApiService.GymType(map);
    }

    @Override
    public Flowable<OrderFormNetBean> OrderForm(Map map) {
        return mMyApiService.OrderForm(map);
    }

    @Override
    public Flowable<FriendsCircleNetBean> FriendsCircle(Map map) {
        return mMyApiService.FriendsCircle(map);
    }

    @Override
    public Flowable<DynamicDetailsNetBean> DynamicDetails(Map map) {
        return mMyApiService.DynamicDetails(map);
    }

    @Override
    public Flowable<ReplySendNetBean> ReplySend(Map map) {
        return mMyApiService.ReplySend(map);
    }

    @Override
    public Flowable<PraiseStatusNetBean> Praise(Map map) {
        return mMyApiService.Praise(map);
    }

    @Override
    public Flowable<UserInfoNetBean> UserInfo(Map map) {
        return mMyApiService.UserInfo(map);
    }

    @Override
    public Flowable<InfoAboutNetBean> InfoAbout(Map map) {
        return mMyApiService.InfoAbout(map);
    }

    @Override
    public Flowable<HomePageInfoNetBean> HomePageInfo(Map map) {
        return mMyApiService.HomePageInfo(map);
    }

    @Override
    public Flowable<TagNetBean> Tag(Map map) {
        return mMyApiService.Tag(map);
    }

    @Override
    public Flowable<WalletLogNetBean> WalletLog(Map map) {
        return mMyApiService.WalletLog(map);
    }

    @Override
    public Flowable<FriendsCircleRelatedNetBean> FriendsCircleRelated(Map map) {
        return mMyApiService.FriendsCircleRelated(map);
    }

    @Override
    public Flowable<CommentsNetBean> CommentsForm(Map map) {
        return mMyApiService.CommentsForm(map);
    }

    @Override
    public Flowable<CourseFormNetBean> CourseForm(Map map) {
        return mMyApiService.CourseForm(map);
    }

    @Override
    public Flowable<CoachPrivateNetBean> CoachPrivate(Map map) {
        return mMyApiService.CoachPrivate(map);
    }

    @Override
    public Flowable<CardFormNetBean> CardForm(Map map) {
        return mMyApiService.CardForm(map);
    }

    @Override
    public Flowable<InfoGymNetBean> InfoGym(Map map) {
        return mMyApiService.InfoGym(map);
    }

    @Override
    public Flowable<InfoCoachPrivateNetBean> InfoCoachPrivate(Map map) {
        return mMyApiService.InfoCoachPrivate(map);
    }

    @Override
    public Flowable<InfoCourseNetBean> InfoCourse(Map map) {
        return mMyApiService.InfoCourse(map);
    }

    @Override
    public Flowable<HomePageNetBean> HomePage(Map map) {
        return mMyApiService.HomePage(map);
    }

    @Override
    public Flowable<TopUpNetBean> TopUp(Map map) {
        return mMyApiService.TopUp(map);
    }

    @Override
    public Flowable<ZfbPayContentNetBean> ZfbPayContent(Map map) {
        return mMyApiService.ZfbPayContent(map);
    }

    @Override
    public Flowable<WechatPayContentNetBean> WechatPayContent(Map map) {
        return mMyApiService.WechatPayContent(map);
    }

    @Override
    public Flowable<CoachFormNetBean> CoachForm(Map map) {
        return mMyApiService.CoachForm(map);
    }

    @Override
    public Flowable<StudentFormNetBean> StudentForm(Map map) {
        return mMyApiService.StudentForm(map);
    }


}
