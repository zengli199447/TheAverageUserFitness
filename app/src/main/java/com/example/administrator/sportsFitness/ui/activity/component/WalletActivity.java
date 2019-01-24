package com.example.administrator.sportsFitness.ui.activity.component;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.PayObject;
import com.example.administrator.sportsFitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsFitness.model.bean.WalletLogNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.ui.controller.ConreollerWallet;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomPayPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class WalletActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, ConreollerWallet.OnWalletLogListener,
        CustomPayPopupWindow.OnItemSelectClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.top_up_recycler_view)
    RecyclerView top_up_recycler_view;
    @BindView(R.id.confirm_top_up)
    TextView confirm_top_up;
    @BindView(R.id.billing_recycler_view)
    RecyclerView billing_recycler_view;
    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;
    @BindView(R.id.footer_layout)
    RelativeLayout footer_layout;


    private ConreollerWallet conreollerWallet;

    private int pageNumber = 1;
    private String moneytotal;
    private CustomPayPopupWindow customPayPopupWindow;
    private String orderCode;
    private int orderheadId;
    private ShowDialog instance;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.WALLET_TOP_UP_PAY:
                instance.showHelpfulHintsDialog(this, getString(R.string.pay_successful), EventCode.ONSTART);
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initClass() {
        instance = ShowDialog.getInstance();
        conreollerWallet = new ConreollerWallet(top_up_recycler_view, billing_recycler_view);
        customPayPopupWindow = new CustomPayPopupWindow(this);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return conreollerWallet;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        billing_recycler_view.setFocusable(false);
        title_name.setText(getString(R.string.money_page));
        Glide.with(this).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(user_img);
        SystemUtil.textMagicTool(this, user_content, DataClass.UNAME, DataClass.PHONE, R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n");
    }

    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(this);
        conreollerWallet.setOnWalletLogListener(this);
        customPayPopupWindow.setOnSelectItemClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
    }

    @OnClick({R.id.confirm_top_up, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.confirm_top_up:
                conreollerWallet.TopUpNetWork();
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumber = pageNumber + 1;
            conreollerWallet.NetWallet(pageNumber);
        }
    }

    @Override
    public void OnWalletLogListener(WalletLogNetBean.ResultBean result) {
        moneytotal = result.getMoneytotal();

        if (pageNumber == 1)
            SystemUtil.textMagicToolTypeFace(this, money, getString(R.string.money), result.getMoneytotal(), R.dimen.dp10, R.dimen.dp20, R.color.white, R.color.white, "", false);

        footer_layout.setVisibility(View.VISIBLE);
        switch (result.getMoneydetaillist().size() == 0 ? 0 : 1) {
            case 0:
                progress_bar.setVisibility(View.GONE);
                if (pageNumber == 1) {
                    footer_layout.setVisibility(View.GONE);
                }
                break;
            case 1:
                if (result.getMoneydetaillist().size() < DataClass.DefaultInformationFlow) {
                    progress_bar.setVisibility(View.GONE);
                } else {
                    progress_bar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void OnTopUpListener(TopUpNetBean.ResultBean result) {
        orderCode = result.getOrdercode();
        orderheadId = result.getOrderheadid();
        customPayPopupWindow.refreshPageView(Double.valueOf(conreollerWallet.getPrice()), -1.0);
        customPayPopupWindow.showAtLocation(top_up_recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        SystemUtil.windowToDark(this);
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    public void setOnItemClick(int index) {
        PayObject payObject = new PayObject(index, EventCode.WALLET_TOP_UP_PAY, orderCode);
        RxBus.getDefault().post(new CommonEvent(EventCode.PAY_ACTION, payObject));
    }

}
