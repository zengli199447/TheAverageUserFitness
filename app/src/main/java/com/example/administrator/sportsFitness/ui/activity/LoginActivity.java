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
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.activity.component.RegisteredAccountActivity;
import com.example.administrator.sportsFitness.ui.controller.ControllerLogin;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.widget.UmComprehensiveBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class LoginActivity extends BaseActivity implements UmComprehensiveBuilder.onCompleteListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.layout_login_content)
    RelativeLayout layout_login_content;
    @BindView(R.id.layout_forget_pass_word)
    LinearLayout layout_forget_pass_word;

    @BindView(R.id.edit_user_name)
    EditText edit_user_name;
    @BindView(R.id.edit_pass_word)
    EditText edit_pass_word;
    @BindView(R.id.password_check)
    CheckBox password_check;

    @BindView(R.id.edit_first_pass_word)
    EditText edit_first_pass_word;
    @BindView(R.id.first_password_check)
    CheckBox first_password_check;
    @BindView(R.id.edit_commite_pass_word)
    EditText edit_commite_pass_word;
    @BindView(R.id.commite_password_check)
    CheckBox commite_password_check;

    @BindView(R.id.edit_you_phone)
    EditText edit_you_phone;
    @BindView(R.id.edit_verification_code)
    EditText edit_verification_code;


    private boolean returnStatus;
    private ControllerLogin controllerLogin;
    private UmComprehensiveBuilder umComprehensiveBuilder;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

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
        swichTexttitle(0);
    }

    @Override
    protected void initListener() {
        umComprehensiveBuilder.setOnCompleteListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.registered, R.id.forgot_password, R.id.password_check,
            R.id.first_password_check, R.id.commite_password_check, R.id.login, R.id.qq, R.id.wechat})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                if (!returnStatus) {
                    finish();
                } else {
                    swichTexttitle(0);
                }
                break;
            case R.id.registered:
                startActivity(new Intent(this, RegisteredAccountActivity.class));
                break;
            case R.id.forgot_password:
                swichTexttitle(1);
                break;
            case R.id.password_check:
                ViewBuilder.seeChecklListener(password_check, edit_pass_word);
                break;
            case R.id.first_password_check:
                ViewBuilder.seeChecklListener(first_password_check, edit_first_pass_word);
                break;
            case R.id.commite_password_check:
                ViewBuilder.seeChecklListener(commite_password_check, edit_commite_pass_word);
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

    private void swichTexttitle(int type) {
        switch (type) {
            case 0:
                title_name.setText(getString(R.string.login_));
                layout_login_content.setVisibility(View.VISIBLE);
                layout_forget_pass_word.setVisibility(View.GONE);
                returnStatus = false;
                break;
            case 1:
                title_name.setText(getString(R.string.modify_the_password));
                layout_login_content.setVisibility(View.GONE);
                layout_forget_pass_word.setVisibility(View.VISIBLE);
                returnStatus = true;
                break;
        }
    }

    @Override
    public void comlete(Map<String, String> data) {
        String qq = "";
        String qqName = "";
        String wechatId = "";
        String wechatName = "";
        String photo = "";
        switch (DataClass.LOGINTYPE) {
            case 2:
                qq = data.get("openid");
                qqName = data.get("screen_name");
                photo = data.get("profile_image_url");
                break;
            case 3:
                wechatId = data.get("unionid");
                wechatName = data.get("screen_name");
                photo = data.get("profile_image_url");
                break;
        }

    }

    @Override
    public void notReach() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (returnStatus) {
                swichTexttitle(0);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
