package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.UserInfoNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerPersonalContent;
import com.example.administrator.sportsFitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomSingleChoicePopupWindow;
import com.example.administrator.sportsFitness.ui.view.CustomTimeChoicePopupWindow;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.CalendarBuilder;
import com.example.administrator.sportsFitness.widget.MultipartBuilder;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PersonalActivity extends BaseActivity implements CustomSingleChoicePopupWindow.OnItemClickListener, PopupWindow.OnDismissListener,
        CustomTimeChoicePopupWindow.OnItemClickListener, ControllerPersonalContent.OnPersonalListener, MultipartBuilder.UpLoadFileListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.user_photo)
    ImageView user_photo;
    @BindView(R.id.nick_name)
    EditText nick_name;

    @BindView(R.id.account_no_change)
    TextView account_no_change;
    @BindView(R.id.account)
    TextView account;

    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.dynamic_visible)
    TextView dynamic_visible;
    @BindView(R.id.face_recognition)
    ImageView face_recognition;

    private AlbumBuilder albumBuilder;

    private int SelectType = -1;
    private String userPhoto;
    private String faceRecognitionPhoto;
    private String selectGender;
    private String selectDynamicVisible;
    private CustomSingleChoicePopupWindow customSingleChoicePopupWindow;
    private CustomTimeChoicePopupWindow customTimeChoicePopupWindow;
    private ControllerPersonalContent controllerPersonalContent;
    private List<String> dynamicVisibleList;
    private int DynamicVisibleIndex = 1;
    private ArrayList<AlbumFile> list = new ArrayList<>();
    private MultipartBuilder multipartBuilder;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.PICTURE:
                switch (SelectType) {
                    case 0:
                        userPhoto = commonevent.getTemp_str();
                        Glide.with(this).load(commonevent.getTemp_str()).error(R.drawable.banner_off).into(user_photo);
                        break;
                    case 1:
                        faceRecognitionPhoto = commonevent.getTemp_str();
                        Glide.with(this).load(commonevent.getTemp_str()).error(R.drawable.banner_off).into(face_recognition);
                        break;
                }
                break;
            case EventCode.PERSONAL_CHANGE_REFRESH:
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
        return R.layout.activity_personal;
    }

    @Override
    protected void initClass() {
        albumBuilder = new AlbumBuilder(this);
        customSingleChoicePopupWindow = new CustomSingleChoicePopupWindow(this);
        customTimeChoicePopupWindow = new CustomTimeChoicePopupWindow(this, new CalendarBuilder(Calendar.getInstance()), false, false);
        controllerPersonalContent = new ControllerPersonalContent();
        multipartBuilder = new MultipartBuilder(this, 1, list);
        progressDialog = ShowDialog.getInstance().showProgressStatus(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerPersonalContent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        location.setText(DataClass.CITY);
    }

    @Override
    protected void initData() {
        dynamicVisibleList = Arrays.asList(getResources().getStringArray(R.array.dynamic_visible));

    }

    @Override
    protected void initView() {
        title_name.setText(getText(R.string.edit_data));
        SystemUtil.textMagicTool(this, account_no_change, getString(R.string.account), getString(R.string.account_no_change),
                R.dimen.dp14, R.dimen.dp14, R.color.black_overlay, R.color.gray_light_text, "");

    }

    @Override
    protected void initListener() {
        customSingleChoicePopupWindow.setOnItemClickListener(this);
        customSingleChoicePopupWindow.setOnDismissListener(this);
        customTimeChoicePopupWindow.setOnItemClickListener(this);
        customTimeChoicePopupWindow.setOnDismissListener(this);
        controllerPersonalContent.setOnPersonalListener(this);
        multipartBuilder.setUpLoadFileListener(this);

    }

    @OnClick({R.id.user_photo, R.id.nick_name, R.id.account, R.id.gender, R.id.birthday, R.id.location, R.id.dynamic_visible, R.id.face_recognition, R.id.save,
            R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.user_photo:
                SelectType = 0;
                albumBuilder.ImageSingleSelection();
                break;
            case R.id.nick_name:

                break;
            case R.id.account:

                break;
            case R.id.gender:
                customSingleChoicePopupWindow.selectType(0, gender.getText().toString());
                customSingleChoicePopupWindow.showAtLocation(title_name, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.birthday:
                customTimeChoicePopupWindow.refreshTitle(getString(R.string.birthday), 0);
                customTimeChoicePopupWindow.showAtLocation(title_name, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.location:
                startActivity(new Intent(this, CityScreeningActivity.class));
                break;
            case R.id.dynamic_visible:
                customSingleChoicePopupWindow.selectType(1, dynamic_visible.getText().toString());
                customSingleChoicePopupWindow.showAtLocation(title_name, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.face_recognition:
                SelectType = 1;
                albumBuilder.ImageSingleSelection();
                break;
            case R.id.save:
                if (userPhoto.isEmpty() || faceRecognitionPhoto.isEmpty()) {
                    toastUtil.showToast(getString(R.string.photo_img_error));
                } else if (nick_name.getText().toString().trim().isEmpty()) {
                    toastUtil.showToast(getString(R.string.nicek_name_empty_error));
                } else {
                    progressDialog.show();
                    list.clear();
                    AlbumFile userPhotoAlbum = new AlbumFile();
                    AlbumFile faceRecognitionPhotoAlbum = new AlbumFile();
                    userPhotoAlbum.setPath(userPhoto);
                    faceRecognitionPhotoAlbum.setPath(faceRecognitionPhoto);
                    list.add(userPhotoAlbum);
                    list.add(faceRecognitionPhotoAlbum);
                    multipartBuilder.arrangementUpLoad();
                }
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    public void setOnItemClick(int currentSelectType, int currentIndex) {
        switch (currentSelectType) {
            case 0:
                selectGender = Arrays.asList(getResources().getStringArray(R.array.gender)).get(currentIndex);
                gender.setText(selectGender);
                break;
            case 1:
                selectDynamicVisible = Arrays.asList(getResources().getStringArray(R.array.dynamic_visible)).get(currentIndex);
                dynamic_visible.setText(selectDynamicVisible);
                DynamicVisibleIndex = currentIndex + 1;
                break;
        }
    }

    @Override
    public void setOnItemClick(View v, String year, String month, String day, String hour, String minute, int selectType) {
        birthday.setText(new StringBuffer().append(year).append("-").append(month).append("-").append(day).toString());

    }


    @Override
    public void OnPersonalListener(UserInfoNetBean.ResultBean result) {
        account.setText(result.getPhone());
        gender.setText(result.getSex());
        nick_name.setText(result.getSecondname());
        birthday.setText(result.getBrithday());
        location.setText(result.getCity());

        userPhoto = result.getPhoto();
        faceRecognitionPhoto = result.getFace();

        DynamicVisibleIndex = Integer.valueOf(result.getNewsshow());
        dynamic_visible.setText(dynamicVisibleList.get(DynamicVisibleIndex - 1));
        Glide.with(this).load(SystemUtil.JudgeUrl(result.getPhoto())).error(R.drawable.banner_off).into(user_photo);
        Glide.with(this).load(SystemUtil.JudgeUrl(result.getFace())).error(R.drawable.banner_off).into(face_recognition);
    }

    @Override
    public void onUpLoadFileListener(String url, List<String> list) {
        LogUtil.e(TAG, "list.get(0) : " + list.get(0));
        LogUtil.e(TAG, "list.get(1) : " + list.get(1));
        progressDialog.dismiss();
        controllerPersonalContent.personalChangeNetWork(
                list.get(0),
                nick_name.getText().toString().trim(),
                gender.getText().toString().trim(),
                birthday.getText().toString().trim(),
                location.getText().toString().trim(),
                list.get(1),
                DynamicVisibleIndex);
    }

    @Override
    public void onUpLoadFileErrorListener() {
        progressDialog.dismiss();
    }


}
