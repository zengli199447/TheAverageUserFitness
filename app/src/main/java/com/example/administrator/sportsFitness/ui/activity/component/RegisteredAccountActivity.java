package com.example.administrator.sportsFitness.ui.activity.component;

import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.controller.ControllerLogin;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/18.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class RegisteredAccountActivity extends BaseActivity {

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
    @BindView(R.id.save)
    AppCompatCheckBox save;
    private ControllerLogin controllerLogin;

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
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.registered_solo));
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.login, R.id.img_btn_black, R.id.save, R.id.user_agreement, R.id.to_obtain_code})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.login:

                break;
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.save:

                break;
            case R.id.user_agreement:

                break;
            case R.id.to_obtain_code:

                break;
        }
    }


}
