package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
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
import com.example.administrator.sportsFitness.model.bean.DynamicDetailsNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerDynamicDetails;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.StickyBoContentTextBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DynamicDetailsActivity extends BaseActivity implements CustomConditionsPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener,
        ControllerDynamicDetails.OnDynamicDetailsListener, NestedScrollView.OnScrollChangeListener, StickyBoContentTextBuilder.OnStickyBoContentTextClickListener {

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
    @BindView(R.id.clear_dynamic)
    ImageView clear_dynamic;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;
    @BindView(R.id.footer_layout)
    RelativeLayout footer_layout;

    private ControllerDynamicDetails controllerDynamicDetails;
    private CustomConditionsPopupWindow customConditionsPopupWindow;
    private String userId;
    private String dynamicId;
    private int pageNumber = 1;
    private ShowDialog instance;
    private StickyBoContentTextBuilder stickyBoContentTextBuilder;
    private List<DynamicDetailsNetBean.ResultBean.DetailBean.UserZhuanBean> user_zhuan;
    private Map<String, String> ForwardingMap = new HashMap<>();
    private String secondname;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.DELET_DYNAMIC_SUCCESSFUL:
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
        return R.layout.activity_dynamic_details;
    }

    @Override
    protected void initClass() {
        stickyBoContentTextBuilder = new StickyBoContentTextBuilder();
        userId = getIntent().getStringExtra("userId");
        dynamicId = getIntent().getStringExtra("dynamicId");
        controllerDynamicDetails = new ControllerDynamicDetails(recycler_view, recycler_view_comments, input_reply, send, userId, dynamicId);
        customConditionsPopupWindow = new CustomConditionsPopupWindow(this);
        instance = ShowDialog.getInstance();
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
        footer_layout.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        customConditionsPopupWindow.setOnItemClickListener(this);
        customConditionsPopupWindow.setOnDismissListener(this);
        controllerDynamicDetails.setOnDynamicDetailsListener(this);
        scrollView.setOnScrollChangeListener(this);
        stickyBoContentTextBuilder.setOnStickyBoContentTextClickListener(this);
    }

    @OnClick({R.id.user_img, R.id.about, R.id.comments, R.id.forwarding, R.id.support_check, R.id.all_comments, R.id.img_btn_black,
            R.id.send, R.id.clear_dynamic})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.user_img:
                Intent theDetailsInformationIntent = new Intent(this, TheDetailsInformationActivity.class);
                theDetailsInformationIntent.putExtra("userId", userId);
                theDetailsInformationIntent.putExtra("userName", secondname);
                startActivity(theDetailsInformationIntent);
                break;
            case R.id.about:
                customConditionsPopupWindow.showAtLocation(recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.support_check:
                controllerDynamicDetails.NetOrSupport(support_check);
                break;
            case R.id.comments:

                break;
            case R.id.forwarding:
                Intent forwardingIntent = new Intent(this, ForwardingActivity.class);
                forwardingIntent.putExtra("forwardingId", dynamicId);
                startActivity(forwardingIntent);
                break;
            case R.id.all_comments:

                break;
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.send:
                toastUtil.showToast(input_reply.getText().toString().trim());
                controllerDynamicDetails.NetSendComments(input_reply.getText().toString().trim());
                break;
            case R.id.clear_dynamic:
                instance.showConfirmOrNoDialog(this, getString(R.string.clear_dynamic), EventCode.ONSTART, EventCode.DELET_DYNAMIC_DETAILS, EventCode.ONSTART);
                break;
        }

    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.report:
                instance.showInputDialog(this, EventCode.REPORT_INPUT_DETAILS);
                break;
            case R.id.cancle:
                break;
        }
    }

    @Override
    public void onDynamicDetailsListener(DynamicDetailsNetBean.ResultBean resultBean, int pageNumber) {

        if (pageNumber == 1) {
            this.pageNumber = pageNumber;

            DynamicDetailsNetBean.ResultBean.DetailBean detail = resultBean.getDetail();

            secondname = detail.getSecondname();

            Glide.with(this).load(SystemUtil.JudgeUrl(detail.getPhoto())).error(R.drawable.banner_off).into(user_img);

            SystemUtil.textMagicTool(this, user_name, secondname, detail.getCreatedate_show(), R.dimen.dp15, R.dimen.dp10,
                    R.color.blue_bar, R.color.gray_light_text, "\n");

            if (detail.getContent().isEmpty() && detail.getUser_zhuan().size() == 0) {
                dynamic_content.setVisibility(View.GONE);
            } else {
                dynamic_content.setVisibility(View.VISIBLE);
                user_zhuan = detail.getUser_zhuan();
                if (user_zhuan.size() > 0) {
                    String sizeLine = "";
                    for (int i = 0; i < user_zhuan.size(); i++) {
                        String content = new StringBuffer().append(DataClass.FORWARDING_TAG).append(user_zhuan.get(i).getSecondname()).append(" ").toString();
                        ForwardingMap.put(content, user_zhuan.get(i).getUserid());
                        sizeLine = new StringBuffer().append(sizeLine).append(content).toString();
                    }
                    dynamic_content.setText(stickyBoContentTextBuilder.getWeiBoContent(new StringBuffer().append(sizeLine).append(detail.getContent()).toString(), this, dynamic_content, -1));
                } else {
                    dynamic_content.setText(detail.getContent());
                }
            }

            comments.setText(new StringBuffer().append(this.getString(R.string.comments)).append(" ").append(detail.getTotal_ping()).toString());

            forwarding.setText(new StringBuffer().append(this.getString(R.string.forwarding)).append(" ").append(detail.getTotal_zhuan()).toString());

            if (detail.getIfzan_cleck().equals("1")) {
                support_check.setChecked(true);
            } else {
                support_check.setChecked(false);
            }
            support_check.setText(new StringBuffer().append(" ").append(detail.getTotal_zan()).toString());

            if (DataClass.USERID.equals(userId)) {
                clear_dynamic.setVisibility(View.VISIBLE);
            } else {
                about.setVisibility(View.VISIBLE);
            }
        }

        footer_layout.setVisibility(View.VISIBLE);
        switch (resultBean.getReply().size() == 0 ? 0 : 1) {
            case 0:
                progress_bar.setVisibility(View.GONE);
                if (pageNumber == 1)
                    footer_layout.setVisibility(View.GONE);
                break;
            case 1:
                if (resultBean.getReply().size() < DataClass.DefaultInformationFlow) {
                    progress_bar.setVisibility(View.GONE);
                } else {
                    progress_bar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onSendCommentsListener() {
        input_reply.setText("");
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumber = pageNumber + 1;
            controllerDynamicDetails.NetDynamicDetails(pageNumber);
        }
    }

    @Override
    public void onStickyBoContentTextClickListener(String content, int index) {
        Intent theDetailsInformationIntent = new Intent(this, TheDetailsInformationActivity.class);
        theDetailsInformationIntent.putExtra("userId", ForwardingMap.get(new StringBuffer().append(content).append(" ").toString()));
        theDetailsInformationIntent.putExtra("userName", content);
        startActivity(theDetailsInformationIntent);
    }
}
