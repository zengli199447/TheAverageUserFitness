package com.example.administrator.sportsFitness.di.component;


import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.di.module.ControllerModule;
import com.example.administrator.sportsFitness.di.scope.ControllerScope;
import com.example.administrator.sportsFitness.ui.controller.ControllerArrange;
import com.example.administrator.sportsFitness.ui.controller.ControllerCourse;
import com.example.administrator.sportsFitness.ui.controller.ControllerDynamicDetails;
import com.example.administrator.sportsFitness.ui.controller.ControllerFriendsCircle;
import com.example.administrator.sportsFitness.ui.controller.ControllerGeneralForm;
import com.example.administrator.sportsFitness.ui.controller.ControllerHome;
import com.example.administrator.sportsFitness.ui.controller.ControllerInfoGym;
import com.example.administrator.sportsFitness.ui.controller.ControllerIntroduce;
import com.example.administrator.sportsFitness.ui.controller.ControllerLogin;
import com.example.administrator.sportsFitness.ui.controller.ControllerMessage;
import com.example.administrator.sportsFitness.ui.controller.ControllerMine;
import com.example.administrator.sportsFitness.ui.controller.ControllerReleaseNewDynamic;
import com.example.administrator.sportsFitness.ui.controller.ControllerSearchType;
import com.example.administrator.sportsFitness.ui.controller.ControllerSocial;
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

}
