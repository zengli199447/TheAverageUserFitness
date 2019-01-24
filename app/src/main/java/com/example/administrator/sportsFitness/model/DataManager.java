package com.example.administrator.sportsFitness.model;

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

    @Override
    public Flowable<VerificationCodeNetWork> Verification(Map map) {
        return mHttpHelper.Verification(map);
    }

    @Override
    public Flowable<LoginInfoNetBean> Registered(Map map) {
        return mHttpHelper.Registered(map);
    }

    @Override
    public Flowable<MyTripNetBean> MyTrip(Map map) {
        return mHttpHelper.MyTrip(map);
    }

    @Override
    public Flowable<CourseTypeNetBean> CourseType(Map map) {
        return mHttpHelper.CourseType(map);
    }

    @Override
    public Flowable<GymFormNetBean> GymForm(Map map) {
        return mHttpHelper.GymForm(map);
    }

    @Override
    public Flowable<GymTypeNetBean> GymType(Map map) {
        return mHttpHelper.GymType(map);
    }

    @Override
    public Flowable<OrderFormNetBean> OrderForm(Map map) {
        return mHttpHelper.OrderForm(map);
    }

    @Override
    public Flowable<FriendsCircleNetBean> FriendsCircle(Map map) {
        return mHttpHelper.FriendsCircle(map);
    }

    @Override
    public Flowable<DynamicDetailsNetBean> DynamicDetails(Map map) {
        return mHttpHelper.DynamicDetails(map);
    }

    @Override
    public Flowable<ReplySendNetBean> ReplySend(Map map) {
        return mHttpHelper.ReplySend(map);
    }

    @Override
    public Flowable<PraiseStatusNetBean> Praise(Map map) {
        return mHttpHelper.Praise(map);
    }

    @Override
    public Flowable<UserInfoNetBean> UserInfo(Map map) {
        return mHttpHelper.UserInfo(map);
    }

    @Override
    public Flowable<InfoAboutNetBean> InfoAbout(Map map) {
        return mHttpHelper.InfoAbout(map);
    }

    @Override
    public Flowable<HomePageInfoNetBean> HomePageInfo(Map map) {
        return mHttpHelper.HomePageInfo(map);
    }

    @Override
    public Flowable<TagNetBean> Tag(Map map) {
        return mHttpHelper.Tag(map);
    }

    @Override
    public Flowable<WalletLogNetBean> WalletLog(Map map) {
        return mHttpHelper.WalletLog(map);
    }

    @Override
    public Flowable<FriendsCircleRelatedNetBean> FriendsCircleRelated(Map map) {
        return mHttpHelper.FriendsCircleRelated(map);
    }

    @Override
    public Flowable<CommentsNetBean> CommentsForm(Map map) {
        return mHttpHelper.CommentsForm(map);
    }

    @Override
    public Flowable<CourseFormNetBean> CourseForm(Map map) {
        return mHttpHelper.CourseForm(map);
    }

    @Override
    public Flowable<CoachPrivateNetBean> CoachPrivate(Map map) {
        return mHttpHelper.CoachPrivate(map);
    }

    @Override
    public Flowable<CardFormNetBean> CardForm(Map map) {
        return mHttpHelper.CardForm(map);
    }

    @Override
    public Flowable<InfoGymNetBean> InfoGym(Map map) {
        return mHttpHelper.InfoGym(map);
    }

    @Override
    public Flowable<InfoCoachPrivateNetBean> InfoCoachPrivate(Map map) {
        return mHttpHelper.InfoCoachPrivate(map);
    }

    @Override
    public Flowable<InfoCourseNetBean> InfoCourse(Map map) {
        return mHttpHelper.InfoCourse(map);
    }

    @Override
    public Flowable<HomePageNetBean> HomePage(Map map) {
        return mHttpHelper.HomePage(map);
    }

    @Override
    public Flowable<TopUpNetBean> TopUp(Map map) {
        return mHttpHelper.TopUp(map);
    }

    @Override
    public Flowable<ZfbPayContentNetBean> ZfbPayContent(Map map) {
        return mHttpHelper.ZfbPayContent(map);
    }

    @Override
    public Flowable<WechatPayContentNetBean> WechatPayContent(Map map) {
        return mHttpHelper.WechatPayContent(map);
    }

    @Override
    public Flowable<CoachFormNetBean> CoachForm(Map map) {
        return mHttpHelper.CoachForm(map);
    }

    @Override
    public Flowable<StudentFormNetBean> StudentForm(Map map) {
        return mHttpHelper.StudentForm(map);
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
