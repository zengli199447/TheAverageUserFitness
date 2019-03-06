package com.example.administrator.sportsfitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
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
import com.example.administrator.sportsfitness.ui.controller.ControllerLogin;
import com.example.administrator.sportsfitness.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class BindPhoneActivity extends BaseActivity implements ControllerLogin.LoginAndRegistereNetWorkListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_obtain)
    EditText edit_obtain;
    @BindView(R.id.to_obtain_code)
    TextView to_obtain_code;

    private String userId;
    private ControllerLogin controllerLogin;
    private int selectLoginType = -1;
    private String verificationCode = "";
    int time;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
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
        return R.layout.activity_bind_phone;
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
        userId = getIntent().getStringExtra("userId");
        time = getResources().getInteger(R.integer.validation_interval);
        switch (DataClass.LOGINTYPE) {
            case 2:
                selectLoginType = 4;
                break;
            case 3:
                selectLoginType = 5;
                break;
        }
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.bind_phone));
    }

    @Override
    protected void initListener() {
        controllerLogin.setLoginAndRegistereNetWorkListener(this);
    }

    @OnClick({R.id.to_obtain_code, R.id.bind, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.to_obtain_code:
                if (SystemUtil.isPhoneNumberLegal(edit_phone_number.getText().toString().trim())) {
                    controllerLogin.NetVerification(edit_phone_number.getText().toString().trim(), selectLoginType);
                } else {
                    toastUtil.showToast(getString(R.string.prompt_phone_number_error));
                }
                break;
            case R.id.bind:
                if (verificationCode.equals(edit_obtain.getText().toString().trim())) {
                    controllerLogin.NetBindPhone(edit_phone_number.getText().toString().trim(), userId);
                } else {
                    toastUtil.showToast(getString(R.string.prompt_obtain_code_error));
                }
                break;
            case R.id.img_btn_black:
                finish();
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
