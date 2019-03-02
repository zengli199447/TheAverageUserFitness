package com.example.administrator.sportsFitness.ui.activity.component;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerReleaseNewDynamic;
import com.example.administrator.sportsFitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.widget.MultipartBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ReleaseNewDynamicActivity extends BaseActivity implements MultipartBuilder.UpLoadFileListener, ControllerReleaseNewDynamic.OnNewDynamicReleaseListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.input_content)
    EditText input_content;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.conditions_all)
    AppCompatCheckBox conditions_all;
    @BindView(R.id.conditions_only)
    AppCompatCheckBox conditions_only;
    private ControllerReleaseNewDynamic controllerReleaseNewDynamic;
    private MultipartBuilder multipartBuilder;
    private ShowDialog instance;
    private int LookStatus = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.DYNAMIC_SAVE:
                DataClass.DYNAMICCONTENT = input_content.getText().toString();
                finish();
                break;
            case EventCode.DYNAMIC_CANCLE:
                DataClass.DYNAMICCONTENT = "";
                DataClass.AlbumFileList.clear();
                finish();
                break;
            case EventCode.DYNAMIC_RELEASE_SUCCESSFUL:
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
        return R.layout.activity_releasenew_dynamic;
    }

    @Override
    protected void initClass() {
        controllerReleaseNewDynamic = new ControllerReleaseNewDynamic(recycler_view);
        multipartBuilder = new MultipartBuilder(this, 1, DataClass.AlbumFileList);
        instance = ShowDialog.getInstance();
        progressDialog = instance.showProgressStatus(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerReleaseNewDynamic;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.release_new_dynamic));
        title_about_text.setText(getString(R.string.release));
        title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
        if (!DataClass.DYNAMICCONTENT.isEmpty())
            input_content.setText(DataClass.DYNAMICCONTENT);
    }

    @Override
    protected void initListener() {
        multipartBuilder.setUpLoadFileListener(this);
        controllerReleaseNewDynamic.setOnNewDynamicReleaseListener(this);
    }

    @OnClick({R.id.title_about_text, R.id.conditions_all, R.id.conditions_only, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.title_about_text:
                if (input_content.getText().toString().trim().isEmpty() && DataClass.AlbumFileList.size() == 0) {
                    instance.showHelpfulHintsDialog(this, getString(R.string.dynamic_empty_error), EventCode.ONSTART);
                } else {
                    if (DataClass.AlbumFileList.size() > 0) {
                        progressDialog.show();
                        multipartBuilder.arrangementUpLoad();
                    } else {
                        upLoadReleaseNewDynamic("");
                    }
                }
                break;
            case R.id.conditions_all:
                conditions_all.setChecked(true);
                conditions_only.setChecked(false);
                LookStatus = 0;
                break;
            case R.id.conditions_only:
                conditions_only.setChecked(true);
                conditions_all.setChecked(false);
                LookStatus = 1;
                break;
            case R.id.img_btn_black:
                finishController();
                break;
        }
    }

    //是否保存当前编辑内容
    private void finishController() {
        if (input_content.getText().toString().isEmpty() && DataClass.AlbumFileList.size() == 0) {
            finish();
        } else {
            controllerReleaseNewDynamic.ShowOrSelect(EventCode.DYNAMIC_OR_SAVE);
        }
    }

    @Override
    public void onNewDynamicReleaseListener() {

    }

    @Override
    public void onUpLoadFileListener(String url, List<String> list) {
        progressDialog.dismiss();
        upLoadReleaseNewDynamic(url);
    }

    @Override
    public void onUpLoadFileErrorListener() {
        progressDialog.dismiss();
    }

    private void upLoadReleaseNewDynamic(String url) {
        controllerReleaseNewDynamic.NetNewDynamic("", url, input_content.getText().toString().trim(), LookStatus);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishController();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
