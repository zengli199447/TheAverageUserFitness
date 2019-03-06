package com.example.administrator.sportsfitness.ui.activity.component;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.ui.controller.ControllerQueryFriendsAbout;
import com.example.administrator.sportsfitness.utils.LogUtil;
import com.example.administrator.sportsfitness.widget.QrBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/2/26.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class QueryFriendsActivity extends BaseActivity implements View.OnKeyListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private ControllerQueryFriendsAbout controllerQueryFriendsAbout;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_query_friends;
    }

    @Override
    protected void initClass() {
        controllerQueryFriendsAbout = new ControllerQueryFriendsAbout(empty_layout, recycler_view);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerQueryFriendsAbout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.add_friends_alias));
    }

    @Override
    protected void initListener() {
        search_edit.setOnKeyListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.qr_code})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.qr_code:
                QrBuilder.Integrator(this);
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            String inputContent = search_edit.getText().toString();
            if (inputContent.isEmpty())
                return true;
            controllerQueryFriendsAbout.NetFriendsCircleRelated(inputContent);
        }
        return false;
    }

}
