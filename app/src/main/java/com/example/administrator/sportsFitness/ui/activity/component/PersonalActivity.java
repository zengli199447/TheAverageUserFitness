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
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.view.CustomSingleChoicePopupWindow;
import com.example.administrator.sportsFitness.ui.view.CustomTimeChoicePopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.CalendarBuilder;

import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PersonalActivity extends BaseActivity implements CustomSingleChoicePopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, CustomTimeChoicePopupWindow.OnItemClickListener {

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        location.setText(DataClass.CITY);
    }

    @Override
    protected void initData() {

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
        customTimeChoicePopupWindow.setOnItemClickListener(this);

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

                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    private void refreshView() {

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
                break;
        }
    }

    @Override
    public void setOnItemClick(View v, String year, String month, String day, String hour, String minute, int selectType) {


    }


}
