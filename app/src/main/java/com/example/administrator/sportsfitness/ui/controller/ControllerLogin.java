package com.example.administrator.sportsfitness.ui.controller;

import android.content.Intent;
import android.util.Log;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsfitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsfitness.model.bean.UserInfoNetBean;
import com.example.administrator.sportsfitness.model.bean.VerificationCodeNetWork;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerLogin extends ControllerClassObserver {

    private ShowDialog instance;
    private DataClass dataClass;
    private ProgressDialog progressDialog;

    public ControllerLogin() {
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        instance = ShowDialog.getInstance();
        dataClass = new DataClass(dataManager);
        progressDialog = instance.showProgressStatus(context);
    }

    /**
     * 登录
     *
     * @param userName
     * @param Password
     */
    public void NetLogin(String userName, String Password) {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.LOGIN);
        linkedHashMap.put("phone", userName);
        linkedHashMap.put("pwd", Password);
        linkedHashMap.put("role", DataClass.AUDIT_STATUS_LOGIN_TYPE);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.fetchLogin(map)
                .compose(RxUtil.<LoginInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoNetBean loginInfoNetBean) {
                        progressDialog.dismiss();
                        if (loginInfoNetBean.getStatus() == 1) {
                            LoginInfoNetBean.ResultBean result = loginInfoNetBean.getResult();
                            if (loginAndRegistereNetWorkListener != null)
                                loginAndRegistereNetWorkListener.onRegistereAndLoginNetWorkListener(result);
                        } else {
                            toastUtil.showToast(loginInfoNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    /**
     * 获取验证码
     *
     * @param phoneNumber
     * @param type        1.注册 2.修改密码/忘记密码 4.qq登陆绑定手机 5.微信登陆绑定手机
     */
    public void NetVerification(String phoneNumber, int type) {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GET_CODE);
        linkedHashMap.put("phone", phoneNumber);
        linkedHashMap.put("type", type);
        linkedHashMap.put("role", DataClass.AUDIT_STATUS_LOGIN_TYPE);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.Verification(map)
                .compose(RxUtil.<VerificationCodeNetWork>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<VerificationCodeNetWork>(toastUtil) {
                    @Override
                    public void onNext(VerificationCodeNetWork verificationCodeNetWork) {
                        if (verificationCodeNetWork.getStatus() == 1) {
                            String verificationCode = verificationCodeNetWork.getResult().getSmscode();
                            if (loginAndRegistereNetWorkListener != null)
                                loginAndRegistereNetWorkListener.onVerificationCodeNetWorkListener(verificationCode);
                        } else {
                            toastUtil.showToast(verificationCodeNetWork.getMessage());
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        progressDialog.dismiss();
                        super.onError(e);
                    }
                }));

    }

    /**
     * 修改密码
     *
     * @param phoneNumber
     * @param Password
     */
    public void NetModifyThePassWord(String phoneNumber, String Password) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_PWD_SET);
        linkedHashMap.put("phone", phoneNumber);
        linkedHashMap.put("pwd", Password);
        linkedHashMap.put("role", DataClass.AUDIT_STATUS_LOGIN_TYPE);
        map.put("version", "v1");
        map.put("vars", new Gson().toJson(linkedHashMap));
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.modify_successful), EventCode.MODIFY_SUCCESSFUL);
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));

    }

    /**
     * 注册
     *
     * @param phoneNumber
     * @param Password
     */
    public void NetRegisteredLogin(String phoneNumber, String Password) {
        progressDialog.show();
        HashMap registeredMap = new HashMap<>();
        LinkedHashMap registeredLinkedHashMap = new LinkedHashMap();
        registeredLinkedHashMap.put("action", DataClass.USER_REGISTER_SET);
        registeredLinkedHashMap.put("phone", phoneNumber);
        registeredLinkedHashMap.put("pwd", Password);
        registeredLinkedHashMap.put("role", DataClass.AUDIT_STATUS_LOGIN_TYPE);
        registeredMap.put("version", "v1");
        registeredMap.put("vars", new Gson().toJson(registeredLinkedHashMap));
        addSubscribe(dataManager.fetchLogin(registeredMap)
                .compose(RxUtil.<LoginInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoNetBean loginInfoNetBean) {
                        if (loginInfoNetBean.getStatus() == 1) {
                            LoginInfoNetBean.ResultBean result = loginInfoNetBean.getResult();
                            if (!loginInfoNetBean.getMessage().isEmpty())
                                toastUtil.showToast(loginInfoNetBean.getMessage());
                            if (loginAndRegistereNetWorkListener != null) {
                                loginAndRegistereNetWorkListener.onRegistereAndLoginNetWorkListener(result);
                            }
                            progressDialog.dismiss();
                        } else {
                            toastUtil.showToast(loginInfoNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        progressDialog.dismiss();
                        super.onError(e);
                    }
                }));
    }

    /**
     * 第三方登陆
     *
     * @param qq
     * @param wechatId
     * @param Name
     * @param photo
     */
    public void NetOtherLogin(String qq, String wechatId, String Name, String photo) {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_THIRDLOGIN_SET);
        linkedHashMap.put("qq", qq);
        linkedHashMap.put("qqname", Name);
        linkedHashMap.put("weixinid", wechatId);
        linkedHashMap.put("weixinname", Name);
        linkedHashMap.put("photo", photo);
        linkedHashMap.put("role", DataClass.AUDIT_STATUS_LOGIN_TYPE);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.fetchLogin(map)
                .compose(RxUtil.<LoginInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoNetBean loginInfoNetBean) {
                        if (loginInfoNetBean.getStatus() == 1) {
                            LoginInfoNetBean.ResultBean result = loginInfoNetBean.getResult();
                            if (loginAndRegistereNetWorkListener != null)
                                loginAndRegistereNetWorkListener.onRegistereAndLoginNetWorkListener(result);
                            progressDialog.dismiss();
                        } else {
                            toastUtil.showToast(loginInfoNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    /**
     * 绑定手机
     *
     * @param phoneNumber
     * @param userId
     */
    public void NetBindPhone(String phoneNumber, final String userId) {
        int thirdLoginType = -1;
        switch (DataClass.LOGINTYPE) {
            case 2:
                thirdLoginType = 1;
                break;
            case 3:
                thirdLoginType = 2;
                break;
        }
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.PHONE_BIND_SET);
        linkedHashMap.put("userid", userId);
        linkedHashMap.put("phone", phoneNumber);
        linkedHashMap.put("thirdlogintype", thirdLoginType);
        linkedHashMap.put("role", DataClass.AUDIT_STATUS_LOGIN_TYPE);
        map.put("version", "v1");
        map.put("vars", new Gson().toJson(linkedHashMap));
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        instance.showHelpfulHintsDialog(context, context.getString(R.string.bind_successful), EventCode.BIND_SUCCESSFUL);
                        NetPersonalContent(userId);
                        if (loginAndRegistereNetWorkListener != null)
                            loginAndRegistereNetWorkListener.onBindPhoneNetWorkListener();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //获取用户信息
    public void NetPersonalContent(String userId) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_DETAIL_GET);
        linkedHashMap.put("userid", userId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UserInfo(map)
                .compose(RxUtil.<UserInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UserInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(UserInfoNetBean userInfoNetBean) {
                        if (userInfoNetBean.getStatus() == 1) {
                            UserInfoNetBean.ResultBean result = userInfoNetBean.getResult();
                            DataClass.USERID = result.getUserid();
                            DataClass.USERPHOTO = result.getPhoto();
                            DataClass.GENDER = result.getSex();
                            DataClass.UNAME = result.getSecondname();
                            DataClass.BRITHDAY = result.getBrithday();
                            DataClass.AGE = result.getAge();
                            dataClass.initDBData();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface LoginAndRegistereNetWorkListener {

        void onRegistereAndLoginNetWorkListener(LoginInfoNetBean.ResultBean result);

        void onVerificationCodeNetWorkListener(String verificationCode);

        void onBindPhoneNetWorkListener();
    }

    private LoginAndRegistereNetWorkListener loginAndRegistereNetWorkListener;

    public void setLoginAndRegistereNetWorkListener(LoginAndRegistereNetWorkListener loginAndRegistereNetWorkListener) {
        this.loginAndRegistereNetWorkListener = loginAndRegistereNetWorkListener;
    }


}
