package com.example.administrator.sportsfitness.di.component;


import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.di.module.ControllerModule;
import com.example.administrator.sportsfitness.di.scope.ControllerScope;
import com.example.administrator.sportsfitness.ui.controller.ConreollerWallet;
import com.example.administrator.sportsfitness.ui.controller.ControllerArrange;
import com.example.administrator.sportsfitness.ui.controller.ControllerCardPage;
import com.example.administrator.sportsfitness.ui.controller.ControllerCoachPrivateInformation;
import com.example.administrator.sportsfitness.ui.controller.ControllerCollection;
import com.example.administrator.sportsfitness.ui.controller.ControllerCommentsForm;
import com.example.administrator.sportsfitness.ui.controller.ControllerCourse;
import com.example.administrator.sportsfitness.ui.controller.ControllerCourseForm;
import com.example.administrator.sportsfitness.ui.controller.ControllerCourseInfomation;
import com.example.administrator.sportsfitness.ui.controller.ControllerDynamicDetails;
import com.example.administrator.sportsfitness.ui.controller.ControllerForwarding;
import com.example.administrator.sportsfitness.ui.controller.ControllerFriendsCircle;
import com.example.administrator.sportsfitness.ui.controller.ControllerFriendsCircleRelated;
import com.example.administrator.sportsfitness.ui.controller.ControllerGeneralForm;
import com.example.administrator.sportsfitness.ui.controller.ControllerGymClass;
import com.example.administrator.sportsfitness.ui.controller.ControllerGymInfomation;
import com.example.administrator.sportsfitness.ui.controller.ControllerHome;
import com.example.administrator.sportsfitness.ui.controller.ControllerInfoCoachPrivate;
import com.example.administrator.sportsfitness.ui.controller.ControllerInfoCourse;
import com.example.administrator.sportsfitness.ui.controller.ControllerInfoGym;
import com.example.administrator.sportsfitness.ui.controller.ControllerIntroduce;
import com.example.administrator.sportsfitness.ui.controller.ControllerLogin;
import com.example.administrator.sportsfitness.ui.controller.ControllerMessage;
import com.example.administrator.sportsfitness.ui.controller.ControllerMine;
import com.example.administrator.sportsfitness.ui.controller.ControllerOrderForm;
import com.example.administrator.sportsfitness.ui.controller.ControllerPersonalContent;
import com.example.administrator.sportsfitness.ui.controller.ControllerQueryFriendsAbout;
import com.example.administrator.sportsfitness.ui.controller.ControllerReleaseNewDynamic;
import com.example.administrator.sportsfitness.ui.controller.ControllerReleaseNewVideoDynamic;
import com.example.administrator.sportsfitness.ui.controller.ControllerSearchType;
import com.example.administrator.sportsfitness.ui.controller.ControllerSelectDiversifiedForm;
import com.example.administrator.sportsfitness.ui.controller.ControllerShowGenera;
import com.example.administrator.sportsfitness.ui.controller.ControllerSingleConditionsSelect;
import com.example.administrator.sportsfitness.ui.controller.ControllerSocial;
import com.example.administrator.sportsfitness.ui.controller.ControllerTheDetailsInformation;
import com.example.administrator.sportsfitness.ui.controller.ControllerTheTagModify;

import dagger.Component;


/**
 * Created by Administrator on 2017/10/27.
 */
@ControllerScope
@Component(modules = ControllerModule.class, dependencies = AppComponent.class)
public interface ControllerComponent {
    ControllerClassObserver getController();

    void inject(ControllerLogin controllerLogin);

    void inject(ControllerArrange controllerArrange);

    void inject(ControllerCourse controllerCourse);

    void inject(ControllerSocial controllerSocial);

    void inject(ControllerMine controllerMine);

    void inject(ControllerFriendsCircle controllerFriendsCircle);

    void inject(ControllerMessage controllerMessage);

    void inject(ControllerDynamicDetails controllerDynamicDetails);

    void inject(ControllerReleaseNewDynamic controllerReleaseNewDynamic);

    void inject(ControllerHome controllerHome);

    void inject(ControllerSearchType controllerSearchType);

    void inject(ControllerIntroduce controllerIntroduce);

    void inject(ControllerTheTagModify controllerTheTagModify);

    void inject(ControllerGeneralForm controllerGeneralForm);

    void inject(ControllerInfoGym controllerInfoGym);

    void inject(ControllerSelectDiversifiedForm controllerSelectDiversifiedForm);

    void inject(ControllerGymInfomation controllerGymInfomation);

    void inject(ControllerCoachPrivateInformation controllerCoachPrivateInformation);

    void inject(ControllerFriendsCircleRelated controllerFriendsCircleRelated);

    void inject(ConreollerWallet conreollerWallet);

    void inject(ControllerCardPage controllerCardPage);

    void inject(ControllerShowGenera controllerShowGenera);

    void inject(ControllerOrderForm controllerOrderForm);

    void inject(ControllerSingleConditionsSelect controllerSingleConditionsSelect);

    void inject(ControllerTheDetailsInformation controllerTheDetailsInformation);

    void inject(ControllerForwarding controllerForwarding);

    void inject(ControllerCommentsForm controllerCommentsForm);

    void inject(ControllerPersonalContent controllerPersonalContent);

    void inject(ControllerCourseForm controllerCourseForm);

    void inject(ControllerInfoCoachPrivate controllerInfoCoachPrivate);

    void inject(ControllerGymClass controllerGymClass);

    void inject(ControllerCourseInfomation controllerCourseInfomation);

    void inject(ControllerInfoCourse controllerInfoCourse);

    void inject(ControllerCollection controllerCollection);

    void inject(ControllerQueryFriendsAbout controllerQueryFriendsAbout);

    void inject(ControllerReleaseNewVideoDynamic controllerReleaseNewVideoDynamic);

}
