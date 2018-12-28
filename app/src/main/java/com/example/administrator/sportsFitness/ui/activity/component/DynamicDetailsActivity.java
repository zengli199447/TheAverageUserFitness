package com.example.administrator.sportsFitness.ui.activity.component;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerDynamicDetails;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DynamicDetailsActivity extends BaseActivity implements CustomConditionsPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.about)
    ImageView about;
    @BindView(R.id.dynamic_content)
    TextView dynamic_content;
    @BindView(R.id.recycler_view_layout)
    RelativeLayout recycler_view_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.support_check)
    AppCompatCheckBox support_check;
    @BindView(R.id.comments)
    TextView comments;
    @BindView(R.id.forwarding)
    TextView forwarding;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.recycler_view_comments)
    RecyclerView recycler_view_comments;
    @BindView(R.id.all_comments)
    TextView all_comments;
    @BindView(R.id.input_reply)
    EditText input_reply;
    @BindView(R.id.send)
    TextView send;

    private ControllerDynamicDetails controllerDynamicDetails;
    private CustomConditionsPopupWindow customConditionsPopupWindow;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_dynamic_details;
    }

    @Override
    protected void initClass() {
        controllerDynamicDetails = new ControllerDynamicDetails(recycler_view, recycler_view_comments);
        customConditionsPopupWindow = new CustomConditionsPopupWindow(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerDynamicDetails;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(ViewBuilder.getGlobalLayoutListener(decorView, findViewById(Window.ID_ANDROID_CONTENT)));
        title_name.setText(getString(R.string.dynamic_body));
        line.setVisibility(View.GONE);
        RefreshView();
    }

    @Override
    protected void initListener() {
        customConditionsPopupWindow.setOnItemClickListener(this);
        customConditionsPopupWindow.setOnDismissListener(this);
        input_reply.addTextChangedListener(TextWatcherListener);
    }

    @OnClick({R.id.user_img, R.id.about, R.id.comments, R.id.forwarding, R.id.support_check, R.id.all_comments, R.id.img_btn_black,
            R.id.send})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.user_img:

                break;
            case R.id.about:
                customConditionsPopupWindow.showAtLocation(recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                SystemUtil.windowToDark(this);
                break;
            case R.id.support_check:

                break;
            case R.id.comments:

                break;
            case R.id.forwarding:

                break;
            case R.id.all_comments:

                break;
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.send:
                toastUtil.showToast(input_reply.getText().toString().trim());
                break;
        }

    }

    private void RefreshView() {
        Glide.with(this).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(user_img);

        SystemUtil.textMagicTool(this, user_name, DataClass.UNAME, "今天  15:20", R.dimen.dp15, R.dimen.dp10,
                R.color.blue_bar, R.color.gray_light_text, "\n");

        dynamic_content.setText("一组超棒的按摩指南，累了、睡前都可以做，做完非常舒服~~ get");

        comments.setText(new StringBuffer().append(this.getString(R.string.comments)).append(" 1000").toString());

        forwarding.setText(new StringBuffer().append(this.getString(R.string.forwarding)).append(" 1021").toString());

        support_check.setText(" 1002");

        recycler_view_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.report:
                ShowDialog.getInstance().showInputDialog(this, EventCode.REPORT_INPUT);
                break;
            case R.id.cancle:
                break;
        }
    }

    private TextWatcher TextWatcherListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().isEmpty()) {
                send.setEnabled(false);
                send.setTextColor(getResources().getColor(R.color.gray_light));
            } else {
                send.setEnabled(true);
                send.setTextColor(getResources().getColor(R.color.blue_bar));
            }
        }
    };

}
