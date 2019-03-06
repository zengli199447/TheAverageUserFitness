package com.example.administrator.sportsfitness.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.ui.activity.component.QueryFriendsActivity;
import com.example.administrator.sportsfitness.ui.activity.component.ReleaseNewDynamicActivity;
import com.example.administrator.sportsfitness.ui.activity.component.ReleaseNewVideoDynamicActivity;
import com.example.administrator.sportsfitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsfitness.ui.controller.ControllerHome;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.ui.fragment.CourseFragment;
import com.example.administrator.sportsfitness.ui.fragment.HomeFragment;
import com.example.administrator.sportsfitness.ui.fragment.MineFragment;
import com.example.administrator.sportsfitness.ui.fragment.SearchFragment;
import com.example.administrator.sportsfitness.ui.fragment.SocialFragment;
import com.example.administrator.sportsfitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsfitness.ui.view.FlowLayout;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.Constants;
import com.example.administrator.sportsfitness.widget.QrBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener ,CustomConditionsPopupWindow.OnItemClickListener,
        PopupWindow.OnDismissListener{

    @BindView(R.id.group_view)
    RadioGroup group_view;
    @BindView(R.id.layout_add_action)
    RelativeLayout layout_add_action;
    @BindView(R.id.layout_search)
    RelativeLayout layout_search;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.history_search_layout)
    FlowLayout history_search_layout;
    @BindView(R.id.fl_layout)
    FrameLayout frameLayout;

    private int showFragment = Constants.HOME;
    private int hideFragment = Constants.HOME;
    private HomeFragment homeFragment;
    private SocialFragment socialFragment;
    private CourseFragment courseFragment;
    private MineFragment mineFragment;
    private long exitTime = 0;
    private boolean returnStatus;
    private ControllerHome controllerHome;
    private FragmentTransaction fragmentTransaction;
    private ShowDialog showDialog;
    private CustomConditionsPopupWindow customConditionsPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
            hideFragment = showFragment;
        }
    }

    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.HOME:
                return homeFragment;
            case Constants.SOCIAL:
                return socialFragment;
            case Constants.COURSE:
                return courseFragment;
            case Constants.MINE:
                return mineFragment;
        }
        return homeFragment;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.OPEN_SEARCH:
                layout_search.setVisibility(View.VISIBLE);
                returnStatus = true;
                break;
            case EventCode.FINISH:
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
        return R.layout.activity_home;
    }

    @Override
    protected void initClass() {
        controllerHome = new ControllerHome(search_edit, history_search_layout, frameLayout);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        showDialog = ShowDialog.getInstance();
        customConditionsPopupWindow = new CustomConditionsPopupWindow(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerHome;
    }

    @Override
    protected void initData() {
        SystemUtil.initWindowsManagerWidth(this);
    }

    @Override
    protected void initView() {
        homeFragment = new HomeFragment();
        socialFragment = new SocialFragment();
        courseFragment = new CourseFragment();
        mineFragment = new MineFragment();
        loadMultipleRootFragment(R.id.layout_fl, 0, homeFragment, socialFragment, courseFragment, mineFragment);
        fragmentTransaction.replace(R.id.fl_layout, new SearchFragment());
        Bundle data = new Bundle();
        data.putInt("flagStatus", EventCode.COURSE);
        courseFragment.setArguments(data);
        fragmentTransaction.commit();
        customConditionsPopupWindow.refreshView();
    }

    @Override
    protected void initListener() {
        group_view.setOnCheckedChangeListener(this);
        customConditionsPopupWindow.setOnItemClickListener(this);
        customConditionsPopupWindow.setOnDismissListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.dynamic_add, R.id.close, R.id.dynamic_release, R.id.add_friends, R.id.action_qr, R.id.layout_add_action,
            R.id.layout_search, R.id.cancle, R.id.clear_search})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.dynamic_add:
                returnStatus = true;
                layout_add_action.setVisibility(View.VISIBLE);
                break;
            case R.id.close:
                refreshViewStatus();
                break;
            case R.id.dynamic_release:
                customConditionsPopupWindow.showAtLocation(frameLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                refreshViewStatus();
                break;
            case R.id.add_friends:
                startActivity(new Intent(this, QueryFriendsActivity.class));
                refreshViewStatus();
                break;
            case R.id.action_qr:
                QrBuilder.Integrator(this);
                refreshViewStatus();
                break;
            case R.id.layout_search:
                break;
            case R.id.cancle:
                refreshViewStatus();
                break;
            case R.id.clear_search:
                showDialog.showConfirmOrNoDialog(this, getString(R.string.or_empty_serach_history), EventCode.ONSTART, EventCode.SEARCH_CLEAR_ALL_COMMITE, EventCode.ONSTART);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.home:
                showFragment = Constants.HOME;
                break;
            case R.id.social:
                showFragment = Constants.SOCIAL;
                break;
            case R.id.course:
                showFragment = Constants.COURSE;
                break;
            case R.id.mine:
                showFragment = Constants.MINE;
                break;
        }
        group_view.check(checkedId);
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        hideFragment = showFragment;
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.release_img:
                startActivity(new Intent(this, ReleaseNewDynamicActivity.class));
                break;
            case R.id.release_video:
                startActivity(new Intent(this, ReleaseNewVideoDynamicActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                toastUtil.showToast(getString(R.string.content_null));
            } else {
                String ScanResult = intentResult.getContents();
                String[] split = ScanResult.split("-");
                if (split.length > 1 && split[0].equals(getString(R.string.fitness))) {
                    Intent theDetailsInformationIntent = new Intent(this, TheDetailsInformationActivity.class);
                    theDetailsInformationIntent.putExtra("userId", split[1]);
                    theDetailsInformationIntent.putExtra("userName", getString(R.string.new_friends));
                    startActivity(theDetailsInformationIntent);
                } else {
                    toastUtil.showToast(getString(R.string.scan_error));
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void refreshViewStatus() {
        if (frameLayout.getVisibility() != View.GONE) {
            frameLayout.setVisibility(View.GONE);
        } else {
            layout_search.setVisibility(View.GONE);
            layout_add_action.setVisibility(View.GONE);
            returnStatus = !returnStatus;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!returnStatus) {
                exit();
            } else {
                refreshViewStatus();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toastUtil.showToast(getString(R.string.finish));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
