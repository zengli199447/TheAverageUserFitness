package com.example.administrator.sportsFitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerShowGenera;
import com.example.administrator.sportsFitness.ui.view.CustomSharePopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.QrBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ShowGeneraActivity extends BaseActivity implements CustomSharePopupWindow.OnItemSelectClickListener, CustomSharePopupWindow.OnDismissListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;

    @BindView(R.id.layout_treat)
    LinearLayout layout_treat;
    @BindView(R.id.fitness_treat)
    TextView fitness_treat;
    @BindView(R.id.share_friend)
    TextView share_friend;

    @BindView(R.id.layout_qr_code)
    RelativeLayout layout_qr_code;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.qr_code)
    ImageView qr_code;

    @BindView(R.id.layout_add_friends)
    RelativeLayout layout_add_friends;
    @BindView(R.id.add_user_img)
    ImageView add_user_img;
    @BindView(R.id.add_user_name)
    TextView add_user_name;
    @BindView(R.id.add_friend_validation)
    EditText add_friend_validation;


    private int flags;
    private CustomSharePopupWindow customSharePopupWindow;
    private ControllerShowGenera controllerShowGenera;
    private String addFriendsId;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_show_genera;
    }

    @Override
    protected void initClass() {
        flags = getIntent().getFlags();
        addFriendsId = getIntent().getStringExtra("addFriendsId");
        controllerShowGenera = new ControllerShowGenera(flags);
        customSharePopupWindow = new CustomSharePopupWindow(this);

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerShowGenera;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        switch (flags) {
            case EventCode.TREAT:
                title_name.setText(getString(R.string.treat));
                layout_treat.setVisibility(View.VISIBLE);
                SystemUtil.textMagicTool(this, fitness_treat, getString(R.string.fitness_treat),
                        getString(R.string.fitness_treat_content), R.dimen.dp15, R.dimen.dp12, R.color.black, R.color.black_overlay, "\n");
                SystemUtil.textMagicTool(this, share_friend, getString(R.string.share_friend),
                        getString(R.string.fitness_treat_content), R.dimen.dp15, R.dimen.dp12, R.color.black, R.color.black_overlay, "\n");
                break;
            case EventCode.MY_QR_CODE:
                title_name.setText(getString(R.string.my_qr_code));
                layout_qr_code.setVisibility(View.VISIBLE);
                Glide.with(this).load(DataClass.USERPHOTO).error(R.drawable.banner_off).into(user_img);
                user_name.setText(DataClass.UNAME);
                qr_code.setImageBitmap(QrBuilder.createQRCodeWithLogo("DataClass.USERID", 200, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
                break;
            case EventCode.ADD_VALIDATION:
                title_name.setText(getString(R.string.apply_for_friend));
                layout_add_friends.setVisibility(View.VISIBLE);
                Glide.with(this).load(DataClass.USERPHOTO).error(R.drawable.banner_off).into(add_user_img);
                add_user_name.setText(DataClass.UNAME);
                title_about_text.setText(getString(R.string.send));
                title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
                break;
        }
    }

    @Override
    protected void initListener() {
        customSharePopupWindow.setOnSelectItemClickListener(this);
        customSharePopupWindow.setOnDismissListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.select_site, R.id.select_share, R.id.title_about_text})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.select_site:
                startActivity(new Intent(this, GeneralFormActivity.class).setFlags(EventCode.GYM));
                break;
            case R.id.select_share:
                customSharePopupWindow.showAtLocation(title_name, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.title_about_text:
                switch (flags) {
                    case EventCode.ADD_VALIDATION:

                        break;
                }
                break;
        }
    }

    @Override
    public void setOnItemClick(int index) {
        toastUtil.showToast("index : " + index);
        switch (index) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

}
