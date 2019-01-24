package com.example.administrator.sportsFitness.di.component;


import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.di.module.ControllerModule;
import com.example.administrator.sportsFitness.di.scope.ControllerScope;
import com.example.administrator.sportsFitness.ui.controller.ConreollerWallet;
import com.example.administrator.sportsFitness.ui.controller.ControllerArrange;
import com.example.administrator.sportsFitness.ui.controller.ControllerCardPage;
import com.example.administrator.sportsFitness.ui.controller.ControllerCoachPrivateInformation;
import com.example.administrator.sportsFitness.ui.controller.ControllerCollection;
import com.example.administrator.sportsFitness.ui.controller.ControllerCommentsForm;
import com.example.administrator.sportsFitness.ui.controller.ControllerCourse;
import com.example.administrator.sportsFitness.ui.controller.ControllerCourseForm;
import com.example.administrator.sportsFitness.ui.controller.ControllerCourseInfomation;
import com.example.administrator.sportsFitness.ui.controller.ControllerDynamicDetails;
import com.example.administrator.sportsFitness.ui.controller.ControllerForwarding;
import com.example.administrator.sportsFitness.ui.controller.ControllerFriendsCircle;
import com.example.administrator.sportsFitness.ui.controller.ControllerFriendsCircleRelated;
import com.example.administrator.sportsFitness.ui.controller.ControllerGeneralForm;
import com.example.administrator.sportsFitness.ui.controller.ControllerGymClass;
import com.example.administrator.sportsFitness.ui.controller.ControllerGymInfomation;
import com.example.administrator.sportsFitness.ui.controller.ControllerHome;
import com.example.administrator.sportsFitness.ui.controller.ControllerInfoCoachPrivate;
import com.example.administrator.sportsFitness.ui.controller.ControllerInfoCourse;
import com.example.administrator.sportsFitness.ui.controller.ControllerInfoGym;
import com.example.administrator.sportsFitness.ui.controller.ControllerIntroduce;
import com.example.administrator.sportsFitness.ui.controller.ControllerLogin;
import com.example.administrator.sportsFitness.ui.controller.ControllerMessage;
import com.example.administrator.sportsFitness.ui.controller.ControllerMine;
import com.example.administrator.sportsFitness.ui.controller.ControllerOrderForm;
import com.example.administrator.sportsFitness.ui.controller.ControllerPersonalContent;
import com.example.administrator.sportsFitness.ui.controller.ControllerReleaseNewDynamic;
import com.example.administrator.sportsFitness.ui.controller.ControllerSearchType;
import com.example.administrator.sportsFitness.ui.controller.ControllerSelectDiversifiedForm;
import com.example.administrator.sportsFitness.ui.controller.ControllerShowGenera;
import com.example.administrator.sportsFitness.ui.controller.ControllerSingleConditionsSelect;
import com.example.administrator.sportsFitness.ui.controller.ControllerSocial;
import com.example.administrator.sportsFitness.ui.controller.ControllerTheDetailsInformation;
import com.example.administrator.sportsFitness.ui.controller.ControllerTheTagModify;

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

}
