package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.plugin.MediaPlayActivity;
import com.example.administrator.sportsFitness.ui.controller.ControllerReleaseNewDynamic;
import com.example.administrator.sportsFitness.ui.controller.ControllerReleaseNewVideoDynamic;
import com.example.administrator.sportsFitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.utils.ToastUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.MultipartBuilder;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ReleaseNewVideoDynamicActivity extends BaseActivity implements MultipartBuilder.UpLoadFileListener, ControllerReleaseNewVideoDynamic.OnNewDynamicReleaseListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.input_content)
    EditText input_content;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.conditions_all)
    AppCompatCheckBox conditions_all;
    @BindView(R.id.conditions_only)
    AppCompatCheckBox conditions_only;
    @BindView(R.id.action)
    ImageView action;

    private ControllerReleaseNewVideoDynamic controllerReleaseNewVideoDynamic;
    private MultipartBuilder multipartBuilder;
    private ShowDialog instance;
    private int LookStatus = 0;
    private ProgressDialog progressDialog;
    private AlbumBuilder albumBuilder;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.DYNAMIC_SAVE:
                DataClass.VIDEODYNAMICCONTENT = input_content.getText().toString();
                finish();
                break;
            case EventCode.DYNAMIC_CANCLE:
                DataClass.VIDEODYNAMICCONTENT = "";
                DataClass.AlbumVideoFileList.clear();
                finish();
                break;
            case EventCode.DYNAMIC_RELEASE_SUCCESSFUL:
                finish();
                break;
            case EventCode.PHOTO_REFRESH:
                AlbumFile albumFile = DataClass.AlbumVideoFileList.get(0);
                Glide.with(this).load(albumFile.getThumbPath()).error(R.drawable.banner_off).into(img);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DataClass.AlbumVideoFileList.size() > 0) {
            img.setVisibility(View.VISIBLE);
            refreshLayoutParams(SystemUtil.dp2px(this, 30));
            action.setImageDrawable(getResources().getDrawable(R.drawable.play_icon_white));
            AlbumFile albumFile = DataClass.AlbumVideoFileList.get(0);
            Glide.with(this).load(albumFile.getThumbPath()).error(R.drawable.banner_off).into(img);
        } else {
            refreshLayoutParams(SystemUtil.dp2px(this, 150));
            action.setImageDrawable(getResources().getDrawable(R.drawable.add_icon));
            img.setVisibility(View.INVISIBLE);
        }
    }

    private void refreshLayoutParams(int width) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) action.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = width;
        action.setLayoutParams(layoutParams);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_releasenew_video_dynamic;
    }

    @Override
    protected void initClass() {
        controllerReleaseNewVideoDynamic = new ControllerReleaseNewVideoDynamic();
        multipartBuilder = new MultipartBuilder(this, 0, "file", DataClass.FILE_SAVE_SET);
        instance = ShowDialog.getInstance();
        progressDialog = instance.showProgressStatus(this);
        albumBuilder = new AlbumBuilder(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerReleaseNewVideoDynamic;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.release_new_dynamic));
        title_about_text.setText(getString(R.string.release));
        title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
        if (!DataClass.VIDEODYNAMICCONTENT.isEmpty())
            input_content.setText(DataClass.VIDEODYNAMICCONTENT);
    }

    @Override
    protected void initListener() {
        multipartBuilder.setUpLoadFileListener(this);
        controllerReleaseNewVideoDynamic.setOnNewDynamicReleaseListener(this);
    }

    @OnClick({R.id.title_about_text, R.id.conditions_all, R.id.conditions_only, R.id.img_btn_black, R.id.img, R.id.action})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.title_about_text:
                if (input_content.getText().toString().trim().isEmpty() && DataClass.AlbumVideoFileList.size() == 0) {
                    instance.showHelpfulHintsDialog(this, getString(R.string.dynamic_empty_error), EventCode.ONSTART);
                } else {
                    if (DataClass.AlbumVideoFileList.size() > 0) {
                        String path = DataClass.AlbumVideoFileList.get(0).getPath();
                        progressDialog.show();
                        multipartBuilder.commitFile(path);
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
            case R.id.img:
            case R.id.action:
                if (DataClass.AlbumVideoFileList.size() > 0) {
                    startActivity(new Intent(this, MediaPlayActivity.class));
                } else {
                    albumBuilder.VideoSingleSelection();
                }
                break;
        }
    }

    //是否保存当前编辑内容
    private void finishController() {
        if (input_content.getText().toString().isEmpty() && DataClass.AlbumVideoFileList.size() == 0) {
            finish();
        } else {
            controllerReleaseNewVideoDynamic.ShowOrSelect(EventCode.DYNAMIC_OR_SAVE);
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
        controllerReleaseNewVideoDynamic.NetNewDynamic("", url, input_content.getText().toString().trim(), LookStatus);
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
