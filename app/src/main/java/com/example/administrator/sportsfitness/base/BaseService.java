package com.example.administrator.sportsfitness.base;


import android.app.Service;


import com.example.administrator.sportsfitness.di.component.DaggerServiceComponent;
import com.example.administrator.sportsfitness.di.component.ServiceComponent;
import com.example.administrator.sportsfitness.di.module.ServiceModule;
import com.example.administrator.sportsfitness.global.MyApplication;
import com.example.administrator.sportsfitness.model.DataManager;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.rxtools.RxBus;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.utils.ToastUtil;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Administrator on 2018/3/1.
 */

public abstract class BaseService extends Service {

    public String TAG = getClass().getSimpleName();

    @Inject
    public ToastUtil toastUtil;

    @Inject
    public DataManager dataManager;

    protected CompositeDisposable mCompositeDisposable;

    protected ServiceComponent getServiceComponent() {
        return DaggerServiceComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .serviceModule(new ServiceModule(this))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initInject();
        initRegisterEvent();
    }

    protected abstract void registerEvent(CommonEvent commonevent);

    protected abstract void initInject();

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    protected void initRegisterEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CommonEvent.class)
                .compose(RxUtil.<CommonEvent>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommonEvent>(toastUtil, null) {

                    @Override
                    public void onNext(CommonEvent commonevent) {
                        registerEvent(commonevent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }
}
