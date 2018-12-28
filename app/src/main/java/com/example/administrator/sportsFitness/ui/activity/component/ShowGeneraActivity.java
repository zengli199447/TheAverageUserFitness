package com.example.administrator.sportsFitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.view.CustomSharePopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;

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

    @BindView(R.id.layout_treat)
    LinearLayout layout_treat;
    @BindView(R.id.fitness_treat)
    TextView fitness_treat;
    @BindView(R.id.share_friend)
    TextView share_friend;


    private int flags;
    private CustomSharePopupWindow customSharePopupWindow;

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
        customSharePopupWindow = new CustomSharePopupWindow(this);

    }

    @Override
    protected void initData() {
        flags = getIntent().getFlags();
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
        }
    }

    @Override
    protected void initListener() {
        customSharePopupWindow.setOnSelectItemClickListener(this);
        customSharePopupWindow.setOnDismissListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.select_site, R.id.select_share})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.select_site:
                startActivity(new Intent(this, GeneralFormActivity.class).setFlags(EventCode.GYM));
                break;
            case R.id.select_share:
                customSharePopupWindow.showAtLocation(title_name, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                SystemUtil.windowToDark(this);
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
