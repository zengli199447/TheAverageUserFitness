package com.example.administrator.sportsFitness.di.component;

import android.app.Activity;


import com.example.administrator.sportsFitness.di.module.ActivityModule;
import com.example.administrator.sportsFitness.di.scope.ActivityScope;
import com.example.administrator.sportsFitness.ui.activity.HomeActivity;
import com.example.administrator.sportsFitness.ui.activity.LoginActivity;
import com.example.administrator.sportsFitness.ui.activity.WelcomeActivity;
import com.example.administrator.sportsFitness.ui.activity.component.CoachPrivateInformationActivity;
import com.example.administrator.sportsFitness.ui.activity.component.DynamicDetailsActivity;
import com.example.administrator.sportsFitness.ui.activity.component.EmbeddedWebActivity;
import com.example.administrator.sportsFitness.ui.activity.component.FriendsCircleActivity;
import com.example.administrator.sportsFitness.ui.activity.component.GeneralFormActivity;
import com.example.administrator.sportsFitness.ui.activity.component.GymInfomationActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoCoachPrivateActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoGymActivity;
import com.example.administrator.sportsFitness.ui.activity.component.PrivateGeneralFormActivity;
import com.example.administrator.sportsFitness.ui.activity.component.SelectDiversifiedFormActivity;
import com.example.administrator.sportsFitness.ui.activity.component.RegisteredAccountActivity;
import com.example.administrator.sportsFitness.ui.activity.component.ReleaseNewDynamicActivity;
import com.example.administrator.sportsFitness.ui.activity.component.ShowGeneraActivity;
import com.example.administrator.sportsFitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsFitness.ui.activity.component.TheTagModifyActivity;
import com.example.administrator.sportsFitness.ui.activity.component.WalletActivity;
import com.example.administrator.sportsFitness.ui.activity.component.WebConcentratedActivity;

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

}
