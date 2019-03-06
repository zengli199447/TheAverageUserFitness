package com.example.administrator.sportsfitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.ui.controller.ControllerLogin;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/3.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ModifyThePassWordActivity extends BaseActivity implements ControllerLogin.LoginAndRegistereNetWorkListener {

    @BindView(R.id.title_name)
    TextView title_name;
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
    @BindView(R.id.to_obtain_code)
    TextView to_obtain_code;
    @BindView(R.id.change_password)
    TextView change_password;

    private ControllerLogin controllerLogin;
    private String verificationCode;
    int time;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.MODIFY_SUCCESSFUL:
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
        return R.layout.activity_modify_the_password;
    }

    @Override
    protected void initClass() {
        controllerLogin = new ControllerLogin();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerLogin;
    }

    @Override
    protected void initData() {
        time = getResources().getInteger(R.integer.validation_interval);
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.modify_the_password));
    }

    @Override
    protected void initListener() {
        controllerLogin.setLoginAndRegistereNetWorkListener(this);
    }

    @OnClick({R.id.first_password_check, R.id.commite_password_check, R.id.img_btn_black, R.id.to_obtain_code, R.id.change_password})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.first_password_check:
                ViewBuilder.seeChecklListener(first_password_check, edit_first_pass_word);
                break;
            case R.id.commite_password_check:
                ViewBuilder.seeChecklListener(commite_password_check, edit_commite_pass_word);
                break;
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.to_obtain_code:
                if (SystemUtil.isPhoneNumberLegal(edit_you_phone.getText().toString().trim())) {
                    controllerLogin.NetVerification(edit_you_phone.getText().toString().trim(), 2);
                } else {
                    toastUtil.showToast(getString(R.string.prompt_phone_number_error));
                }
                break;
            case R.id.change_password:
                if (!SystemUtil.isPhoneNumberLegal(edit_you_phone.getText().toString().trim())) {
                    toastUtil.showToast(getString(R.string.prompt_phone_number_error));
                } else if (verificationCode.isEmpty() && !verificationCode.equals(edit_verification_code.getText().toString().trim())) {
                    toastUtil.showToast(getString(R.string.prompt_verification_error));
                } else if (edit_first_pass_word.getText().toString().trim().isEmpty() || edit_commite_pass_word.getText().toString().trim().isEmpty()) {
                    toastUtil.showToast(getString(R.string.prompt_empty_error));
                } else if (!edit_first_pass_word.getText().toString().trim().equals(edit_commite_pass_word.getText().toString().trim())) {
                    toastUtil.showToast(getString(R.string.prompt_pass_word_error));
                } else {
                    controllerLogin.NetModifyThePassWord(edit_you_phone.getText().toString().trim(), edit_commite_pass_word.getText().toString().trim());
                }
                break;
        }
    }

    @Override
    public void onRegistereAndLoginNetWorkListener(LoginInfoNetBean.ResultBean result) {

    }

    @Override
    public void onVerificationCodeNetWorkListener(String verificationCode) {
        this.verificationCode = verificationCode;
        to_obtain_code.setEnabled(false);
        to_obtain_code.setTextColor(getResources().getColor(R.color.gray_light_text));
        refreshVerificationCodeView();
    }

    @Override
    public void onBindPhoneNetWorkListener() {

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    if (to_obtain_code != null)
                        refreshVerificationCodeView();
                    break;
            }
        }
    };

    private void refreshVerificationCodeView() {
        if (time > 0) {
            time = time - 1;
            to_obtain_code.setText(new StringBuffer().append(time).append(getString(R.string.seconds_about)));
            handler.sendEmptyMessageDelayed(0, 999);
        } else {
            to_obtain_code.setEnabled(true);
            to_obtain_code.setTextColor(getResources().getColor(R.color.blue_bar));
            to_obtain_code.setText(getString(R.string.to_obtain_code));
            time = getResources().getInteger(R.integer.validation_interval);
        }
    }

}
