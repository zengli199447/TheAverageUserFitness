package com.example.administrator.sportsfitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxBus;
import com.example.administrator.sportsfitness.ui.controller.ControllerLogin;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.CalendarBuilder;
import com.example.administrator.sportsfitness.widget.ViewBuilder;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/18.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class RegisteredAccountActivity extends BaseActivity implements ControllerLogin.LoginAndRegistereNetWorkListener{

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_to_obtain_code)
    EditText edit_to_obtain_code;
    @BindView(R.id.to_obtain_code)
    TextView to_obtain_code;
    @BindView(R.id.edit_fitst_pass_word)
    EditText edit_fitst_pass_word;
    @BindView(R.id.fitst_password_check)
    AppCompatCheckBox fitst_password_check;
    @BindView(R.id.edit_pass_word)
    EditText edit_pass_word;
    @BindView(R.id.password_check)
    AppCompatCheckBox password_check;
    @BindView(R.id.agreed)
    AppCompatCheckBox agreed;
    @BindView(R.id.registered)
    TextView registered;
    private ControllerLogin controllerLogin;
    private String verificationCode;
    int time;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_registered_account;
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
        title_name.setText(getString(R.string.registered_solo));
        refreshAgreedView(false);
    }

    @Override
    protected void initListener() {
        controllerLogin.setLoginAndRegistereNetWorkListener(this);

    }

    @OnClick({R.id.registered, R.id.img_btn_black, R.id.agreed, R.id.user_agreement, R.id.to_obtain_code,
            R.id.fitst_password_check, R.id.password_check})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.registered:
                if (!SystemUtil.isPhoneNumberLegal(edit_phone_number.getText().toString().trim())) {
                    toastUtil.showToast(getString(R.string.prompt_phone_number_error));
                } else if (verificationCode.isEmpty() && !verificationCode.equals(edit_to_obtain_code.getText().toString().trim())) {
                    toastUtil.showToast(getString(R.string.prompt_verification_error));
                } else if (edit_fitst_pass_word.getText().toString().trim().isEmpty() || edit_pass_word.getText().toString().trim().isEmpty()) {
                    toastUtil.showToast(getString(R.string.prompt_empty_error));
                } else if (!edit_fitst_pass_word.getText().toString().trim().equals(edit_pass_word.getText().toString().trim())) {
                    toastUtil.showToast(getString(R.string.prompt_pass_word_error));
                } else {
                    controllerLogin.NetRegisteredLogin(edit_phone_number.getText().toString().trim(), edit_pass_word.getText().toString().trim());
                }
                break;
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.agreed:
                refreshAgreedView(agreed.isChecked());
                break;
            case R.id.user_agreement:

                break;
            case R.id.to_obtain_code:
                if (SystemUtil.isPhoneNumberLegal(edit_phone_number.getText().toString().trim())) {
                    controllerLogin.NetVerification(edit_phone_number.getText().toString().trim(), 0);
                } else {
                    toastUtil.showToast(getString(R.string.prompt_phone_number_error));
                }
                break;
            case R.id.fitst_password_check:
                ViewBuilder.seeChecklListener(fitst_password_check, edit_fitst_pass_word);
                break;
            case R.id.password_check:
                ViewBuilder.seeChecklListener(password_check, edit_pass_word);
                break;
        }
    }

    @Override
    public void onRegistereAndLoginNetWorkListener(LoginInfoNetBean.ResultBean result) {
        try {
            DataClass.UNAME = result.getSecondname();
            DataClass.USERID = result.getUserid();
            DataClass.USERPHOTO = result.getPhoto();
            DataClass.GENDER = result.getSex();
            DataClass.PHONE = result.getPhone();
            DataClass.AGE = String.valueOf(CalendarBuilder.getAgeByBirthday(result.getBrithday()));
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            RxBus.getDefault().post(new CommonEvent(EventCode.ACTION_HOME));
            finish();
        }
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

    private void refreshAgreedView(boolean status) {
        registered.setEnabled(status);
        if (status) {
            registered.setBackground(getResources().getDrawable(R.drawable.corners_soild_20_layout_blue));
        } else {
            registered.setBackground(getResources().getDrawable(R.drawable.corners_soild_20_layout_white));
        }
    }

}
