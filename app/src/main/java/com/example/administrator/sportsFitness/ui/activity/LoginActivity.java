package com.example.administrator.sportsFitness.ui.activity;

import android.content.Intent;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.component.BindPhoneActivity;
import com.example.administrator.sportsFitness.ui.activity.component.ModifyThePassWordActivity;
import com.example.administrator.sportsFitness.ui.activity.component.RegisteredAccountActivity;
import com.example.administrator.sportsFitness.ui.controller.ControllerLogin;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.widget.CalendarBuilder;
import com.example.administrator.sportsFitness.widget.UmComprehensiveBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.text.ParseException;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class LoginActivity extends BaseActivity implements UmComprehensiveBuilder.onCompleteListener, ControllerLogin.LoginAndRegistereNetWorkListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.edit_user_name)
    EditText edit_user_name;
    @BindView(R.id.edit_pass_word)
    EditText edit_pass_word;
    @BindView(R.id.password_check)
    CheckBox password_check;

    private ControllerLogin controllerLogin;
    private UmComprehensiveBuilder umComprehensiveBuilder;
    private DataClass dataClass;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.ACTION_HOME:
                ActionStart();
                break;
            case EventCode.BIND_SUCCESSFUL:
                finish();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initClass() {
        controllerLogin = new ControllerLogin();
        umComprehensiveBuilder = new UmComprehensiveBuilder(this, toastUtil);
        dataClass = new DataClass(dataManager);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerLogin;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.login_));
        edit_user_name.setText("15972121636");
        edit_pass_word.setText("123456");
    }

    @Override
    protected void initListener() {
        umComprehensiveBuilder.setOnCompleteListener(this);
        controllerLogin.setLoginAndRegistereNetWorkListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.registered, R.id.forgot_password, R.id.password_check, R.id.login, R.id.qq, R.id.wechat})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.registered:
                startActivity(new Intent(this, RegisteredAccountActivity.class));
                break;
            case R.id.forgot_password:
                startActivity(new Intent(this, ModifyThePassWordActivity.class));
                break;
            case R.id.password_check:
                ViewBuilder.seeChecklListener(password_check, edit_pass_word);
                break;
            case R.id.login:
                if (!edit_user_name.getText().toString().isEmpty() && !edit_pass_word.getText().toString().isEmpty()) {
                    controllerLogin.NetLogin(edit_user_name.getText().toString(), edit_pass_word.getText().toString());
                } else {
                    startActivity(new Intent(this, HomeActivity.class));
                    toastUtil.showToast(getString(R.string.empty_error));
                }
                break;
            case R.id.wechat:
                DataClass.LOGINTYPE = 3;
                umComprehensiveBuilder.initUmLogin(1);
                break;
            case R.id.qq:
                DataClass.LOGINTYPE = 2;
                umComprehensiveBuilder.initUmLogin(0);
                break;
        }
    }

    @Override
    public void comlete(Map<String, String> data) {
        String qq = "";
        String Name = "";
        String wechatId = "";
        String photo = "";
        switch (DataClass.LOGINTYPE) {
            case 2:
                qq = data.get("openid");
                Name = data.get("screen_name");
                photo = data.get("profile_image_url");
                break;
            case 3:
                wechatId = data.get("unionid");
                Name = data.get("screen_name");
                photo = data.get("profile_image_url");
                break;
        }
        controllerLogin.NetOtherLogin(qq, wechatId, Name, photo);
    }

    @Override
    public void notReach() {

    }

    @Override
    public void onRegistereAndLoginNetWorkListener(LoginInfoNetBean.ResultBean result) {
        if (result.getPhone().isEmpty()) {
            Intent intent = new Intent(this, BindPhoneActivity.class);
            intent.putExtra("userId", result.getUserid());
            startActivity(intent);
        } else {
            try {
                DataClass.UNAME = result.getSecondname();
                DataClass.USERID = result.getUserid();
                DataClass.USERPHOTO = result.getPhoto();
                DataClass.GENDER = result.getSex();
                DataClass.PHONE = result.getPhone();
                DataClass.AGE = result.getBrithday();
                dataClass.initDBData();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ActionStart();
            }
        }
    }

    @Override
    public void onVerificationCodeNetWorkListener(String verificationCode) {

    }

    @Override
    public void onBindPhoneNetWorkListener() {

    }

    private void ActionStart() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

}
