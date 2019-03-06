package com.example.administrator.sportsfitness.di.component;

import android.app.Activity;


import com.example.administrator.sportsfitness.di.module.ActivityModule;
import com.example.administrator.sportsfitness.di.scope.ActivityScope;
import com.example.administrator.sportsfitness.ui.activity.HomeActivity;
import com.example.administrator.sportsfitness.ui.activity.LoginActivity;
import com.example.administrator.sportsfitness.ui.activity.WelcomeActivity;
import com.example.administrator.sportsfitness.ui.activity.component.BindPhoneActivity;
import com.example.administrator.sportsfitness.ui.activity.component.CardPageActivity;
import com.example.administrator.sportsfitness.ui.activity.component.CityScreeningActivity;
import com.example.administrator.sportsfitness.ui.activity.component.CoachPrivateInformationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.CollectionActivity;
import com.example.administrator.sportsfitness.ui.activity.component.CourseInformationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.DynamicDetailsActivity;
import com.example.administrator.sportsfitness.ui.activity.component.EmbeddedWebActivity;
import com.example.administrator.sportsfitness.ui.activity.component.ForwardingActivity;
import com.example.administrator.sportsfitness.ui.activity.component.FriendsCircleActivity;
import com.example.administrator.sportsfitness.ui.activity.component.GeneralFormActivity;
import com.example.administrator.sportsfitness.ui.activity.component.GymClassSelectActivity;
import com.example.administrator.sportsfitness.ui.activity.component.GymInfomationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.InfoCoachPrivateActivity;
import com.example.administrator.sportsfitness.ui.activity.component.InfoCourseActivity;
import com.example.administrator.sportsfitness.ui.activity.component.InfoGymActivity;
import com.example.administrator.sportsfitness.ui.activity.component.ModifyThePassWordActivity;
import com.example.administrator.sportsfitness.ui.activity.component.MoreDataReferenceActivity;
import com.example.administrator.sportsfitness.ui.activity.component.OrderFormActivity;
import com.example.administrator.sportsfitness.ui.activity.component.PersonalActivity;
import com.example.administrator.sportsfitness.ui.activity.component.PrivateGeneralFormActivity;
import com.example.administrator.sportsfitness.ui.activity.component.QueryFriendsActivity;
import com.example.administrator.sportsfitness.ui.activity.component.ReleaseNewVideoDynamicActivity;
import com.example.administrator.sportsfitness.ui.activity.component.SelectDiversifiedFormActivity;
import com.example.administrator.sportsfitness.ui.activity.component.RegisteredAccountActivity;
import com.example.administrator.sportsfitness.ui.activity.component.ReleaseNewDynamicActivity;
import com.example.administrator.sportsfitness.ui.activity.component.ShowGeneraActivity;
import com.example.administrator.sportsfitness.ui.activity.component.SingleConditionsSelectActivity;
import com.example.administrator.sportsfitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.TheTagModifyActivity;
import com.example.administrator.sportsfitness.ui.activity.component.WalletActivity;
import com.example.administrator.sportsfitness.ui.activity.component.WebConcentratedActivity;
import com.example.administrator.sportsfitness.ui.activity.plugin.MediaPlayActivity;

import dagger.Component;


/**
 * Created by Administrator on 2017/10/27.
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(WelcomeActivity welcomeActivity);

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisteredAccountActivity registeredAccountActivity);

    void inject(EmbeddedWebActivity embeddedWebActivity);

    void inject(DynamicDetailsActivity dynamicDetailsActivity);

    void inject(ReleaseNewDynamicActivity releaseNewDynamicActivity);

    void inject(TheDetailsInformationActivity theDetailsInformationActivity);

    void inject(TheTagModifyActivity theTagModifyActivity);

    void inject(GeneralFormActivity generalFormActivity);

    void inject(InfoCoachPrivateActivity infoCoachPrivateActivity);

    void inject(InfoGymActivity infoGymActivity);

    void inject(CoachPrivateInformationActivity coachPrivateInformationActivity);

    void inject(SelectDiversifiedFormActivity selectDiversifiedFormActivity);

    void inject(GymInfomationActivity gymInfomationActivity);

    void inject(PrivateGeneralFormActivity privateGeneralFormActivity);

    void inject(ShowGeneraActivity showGeneraActivity);

    void inject(WebConcentratedActivity webConcentratedActivity);

    void inject(FriendsCircleActivity friendsCircleActivity);

    void inject(WalletActivity walletActivity);

    void inject(CardPageActivity cardPageActivity);

    void inject(PersonalActivity personalActivity);

    void inject(CityScreeningActivity cityScreeningActivity);

    void inject(ForwardingActivity forwardingActivity);

    void inject(ModifyThePassWordActivity modifyThePassWordActivity);

    void inject(OrderFormActivity orderFormActivity);

    void inject(SingleConditionsSelectActivity singleConditionsSelectActivity);

    void inject(MoreDataReferenceActivity moreDataReferenceActivity);

    void inject(GymClassSelectActivity gymClassSelectActivity);

    void inject(CourseInformationActivity courseInformationActivity);

    void inject(InfoCourseActivity infoCourseActivity);

    void inject(BindPhoneActivity bindPhoneActivity);

    void inject(CollectionActivity collectionActivity);

    void inject(QueryFriendsActivity queryFriendsActivity);

    void inject(ReleaseNewVideoDynamicActivity releaseNewVideoDynamicActivity);

    void inject(MediaPlayActivity mediaPlayActivity);

}
