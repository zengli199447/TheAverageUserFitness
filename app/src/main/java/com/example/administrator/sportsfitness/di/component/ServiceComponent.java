package com.example.administrator.sportsfitness.di.component;

import android.app.Service;

import com.example.administrator.sportsfitness.di.module.ServiceModule;
import com.example.administrator.sportsfitness.di.scope.ServiceScope;

import dagger.Component;


/**
 * Created by Administrator on 2017/10/27.
 */
@ServiceScope
@Component(modules = ServiceModule.class, dependencies = AppComponent.class)
public interface ServiceComponent {
    Service getService();

//    void inject(MusicService musicService);


}
