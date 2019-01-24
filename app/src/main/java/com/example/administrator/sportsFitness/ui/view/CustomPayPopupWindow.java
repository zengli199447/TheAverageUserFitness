package com.example.administrator.sportsFitness.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/17.
 */

public class CustomPayPopupWindow extends PopupWindow implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();

    private View mPopView;
    private OnItemSelectClickListener mListener;
    private Context context;
    private TextView zfb_pay;
    private AppCompatCheckBox zfb_pay_check;
    private TextView wechat_pay;
    private AppCompatCheckBox wechat_pay_check;
    private TextView page_pay;
    private AppCompatCheckBox page_pay_check;
    private TextView confirm;
    private RelativeLayout zfb_pay_layout;
    private RelativeLayout wechat_pay_layout;
    private RelativeLayout page_pay_layout;

    private List<TextView> textViews = new ArrayList<>();
    private List<AppCompatCheckBox> appCompatCheckBoxes = new ArrayList<>();

    private int selectIndex = 0;

    public CustomPayPopupWindow(Context context) {
        super(context);
        this.context = context;
        RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_MONEY_ABOUT));
        initView();
    }

    private void initView() {
        mPopView = LayoutInflater.from(context).inflate(R.layout.custom_pay_popup_window, null);
        zfb_pay = mPopView.findViewById(R.id.zfb_pay);
        zfb_pay_check = mPopView.findViewById(R.id.zfb_pay_check);
        wechat_pay = mPopView.findViewById(R.id.wechat_pay);
        wechat_pay_check = mPopView.findViewById(R.id.wechat_pay_check);
        page_pay = mPopView.findViewById(R.id.page_pay);
        page_pay_check = mPopView.findViewById(R.id.page_pay_check);
        confirm = mPopView.findViewById(R.id.confirm);

        zfb_pay_layout = mPopView.findViewById(R.id.zfb_pay_layout);
        wechat_pay_layout = mPopView.findViewById(R.id.wechat_pay_layout);
        page_pay_layout = mPopView.findViewById(R.id.page_pay_layout);

        textViews.add(zfb_pay);
        textViews.add(wechat_pay);
        textViews.add(page_pay);

        appCompatCheckBoxes.add(zfb_pay_check);
        appCompatCheckBoxes.add(wechat_pay_check);
        appCompatCheckBoxes.add(page_pay_check);

        confirm.setOnClickListener(this);
        zfb_pay_layout.setOnClickListener(this);
        wechat_pay_layout.setOnClickListener(this);
        page_pay_layout.setOnClickListener(this);

        setPopupWindow();
    }


    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.popwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mPopView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        SystemUtil.windowToLight(context);
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public interface OnItemSelectClickListener {
        void setOnItemClick(int index);
    }

    public void setOnSelectItemClickListener(OnItemSelectClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zfb_pay_layout:
                refreshView(0);
                break;
            case R.id.wechat_pay_layout:
                refreshView(1);
                break;
            case R.id.page_pay_layout:
                refreshView(2);
                break;
            case R.id.confirm:
                if (mListener != null)
                    mListener.setOnItemClick(selectIndex);
                dismiss();
                break;
        }

    }

    //刷新view状态
    private void refreshView(int index) {
        selectIndex = index;
        for (int i = 0; i < textViews.size(); i++) {
            if (index == i) {
                appCompatCheckBoxes.get(i).setChecked(true);
                textViews.get(i).setTextColor(context.getResources().getColor(R.color.blue_bar));
            } else {
                appCompatCheckBoxes.get(i).setChecked(false);
                textViews.get(i).setTextColor(context.getResources().getColor(R.color.black_overlay));
            }
        }
    }

    //余额状态
    public void refreshPageView(Double currentPay, Double containsPay) {
        if (containsPay != -1) {
            if (currentPay > containsPay) {
                page_pay_layout.setEnabled(false);
                SystemUtil.textMagicTool(context, page_pay, new StringBuffer().append(context.getString(R.string.page_pay))
                                .append("(").append(context.getString(R.string.remaining)).append(context.getString(R.string.money))
                                .append(containsPay).append(")").toString(), new StringBuffer().append("  ").append(context.getString(R.string.lack_of_balance)).toString(),
                        R.dimen.dp14, R.dimen.dp12, R.color.gray_light_text, R.color.red_text, "");
            } else {
                page_pay.setTextColor(context.getResources().getColor(R.color.black_overlay));
                page_pay.setText(context.getString(R.string.page_pay));
                page_pay_layout.setEnabled(true);
            }
            page_pay_layout.setVisibility(View.VISIBLE);
        }else {
            page_pay_layout.setVisibility(View.GONE);
        }
    }

}
