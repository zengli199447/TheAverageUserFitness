package com.example.administrator.sportsFitness.di.component;

import android.app.Service;

import com.example.administrator.sportsFitness.di.module.ServiceModule;
import com.example.administrator.sportsFitness.di.scope.ServiceScope;

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
