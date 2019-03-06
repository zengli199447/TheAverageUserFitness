package com.example.administrator.sportsfitness.ui.activity.component;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.DynamicDetailsNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.ui.controller.ControllerForwarding;
import com.example.administrator.sportsfitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.StickyBoContentTextBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/2.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ForwardingActivity extends BaseActivity implements StickyBoContentTextBuilder.OnStickyBoContentTextClickListener, ControllerForwarding.OnDynamicDetailsListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.insights)
    TextView insights;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.text_content)
    TextView text_content;

    private String forwardingId;
    private StickyBoContentTextBuilder stickyBoContentTextBuilder;
    private ControllerForwarding controllerForwarding;
    private ShowDialog instance;
    private Map<String, String> ForwardingMap = new HashMap<>();
    private String text;
    private List<DynamicDetailsNetBean.ResultBean.DetailBean.UserZhuanBean> user_zhuan;
    private DynamicDetailsNetBean.ResultBean.DetailBean detail;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.FORWARDING_DYNAMIC_SUCCESSFUL:
            case EventCode.FORWARDING_ERROR:
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
        return R.layout.activity_forward;
    }

    @Override
    protected void initClass() {
        forwardingId = getIntent().getStringExtra("forwardingId");
        controllerForwarding = new ControllerForwarding(forwardingId);
        stickyBoContentTextBuilder = new StickyBoContentTextBuilder();
        instance = ShowDialog.getInstance();
        progressDialog = instance.showProgressStatus(this);
        progressDialog.show();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerForwarding;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.forwarding));
        title_about_text.setText(getString(R.string.release));
        title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));

    }

    @Override
    protected void initListener() {
        stickyBoContentTextBuilder.setOnStickyBoContentTextClickListener(this);
        controllerForwarding.setOnDynamicDetailsListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.title_about_text})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.title_about_text:
                if (user_zhuan != null && user_zhuan.size() == 0 && detail.getUserid().equals(DataClass.USERID)) {
                    instance.showHelpfulHintsDialog(this, getString(R.string.forwarding_error), EventCode.FORWARDING_ERROR);
                } else if (ForwardingMap.containsValue(DataClass.USERID)) {
                    instance.showHelpfulHintsDialog(this, getString(R.string.forwarding_error), EventCode.FORWARDING_ERROR);
                } else {
                    controllerForwarding.NetForwarding();
                }
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onStickyBoContentTextClickListener(String content, int index) {
        Intent theDetailsInformationIntent = new Intent(this, TheDetailsInformationActivity.class);
        theDetailsInformationIntent.putExtra("userId", ForwardingMap.get(new StringBuffer().append(content).append(" ").toString()));
        theDetailsInformationIntent.putExtra("userName", content);
        startActivity(theDetailsInformationIntent);
    }

    @Override
    public void onDynamicDetailsListener(DynamicDetailsNetBean.ResultBean result) {
        progressDialog.dismiss();
        detail = result.getDetail();
        List<DynamicDetailsNetBean.ResultBean.DetailBean.ImgpathjsonBean> imgpathjson = detail.getImgpathjson();
        if (imgpathjson.size() > 0) {
            img.setVisibility(View.VISIBLE);
            Glide.with(this).load(SystemUtil.JudgeUrl(imgpathjson.get(0).getImgpath())).error(R.drawable.banner_off).into(img);
        }

        user_zhuan = detail.getUser_zhuan();
        if (user_zhuan.size() > 0) {
            String sizeLine = "";
            for (int i = 0; i < detail.getUser_zhuan().size(); i++) {
                String content = new StringBuffer().append(DataClass.FORWARDING_TAG).append(user_zhuan.get(i).getSecondname()).append(" ").toString();
                sizeLine = new StringBuffer().append(sizeLine).append(content).toString();
                ForwardingMap.put(content, user_zhuan.get(i).getUserid());
            }
            String currentText = new StringBuffer().append(DataClass.FORWARDING_TAG).append(detail.getSecondname()).append(" ").toString();
            ForwardingMap.put(currentText, detail.getUserid());
            text = new StringBuffer().append(sizeLine).append(currentText).append(detail.getContent()).toString();
        } else {
            text = new StringBuffer().append(DataClass.FORWARDING_TAG).append(detail.getSecondname()).append(" ").append(detail.getContent()).toString();
        }
        text_content.setText(stickyBoContentTextBuilder.getWeiBoContent(text, this, text_content, -1));
    }


}
