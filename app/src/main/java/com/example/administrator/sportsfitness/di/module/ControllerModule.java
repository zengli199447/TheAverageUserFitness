package com.example.administrator.sportsfitness.di.module;


import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.di.scope.ControllerScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/3/2 0002.
 */
@Module
public class ControllerModule {
    private ControllerClassObserver controllerClassObserver;

    public ControllerModule(ControllerClassObserver controllerClassObserver) {
        this.controllerClassObserver = controllerClassObserver;
    }

    @Provides
    @ControllerScope
    public ControllerClassObserver provideControllerClassObserver() {
        return controllerClassObserver;
    }
}
