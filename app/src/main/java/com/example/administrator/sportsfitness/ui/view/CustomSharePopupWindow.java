package com.example.administrator.sportsfitness.ui.view;

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

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/17.
 */

public class CustomSharePopupWindow extends PopupWindow implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();

    private View mPopView;
    private OnItemSelectClickListener mListener;
    private Context context;

    private int selectIndex = 0;
    private TextView wechat_friend;
    private TextView wechat_friend_circle;
    private TextView qq;

    public CustomSharePopupWindow(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        mPopView = LayoutInflater.from(context).inflate(R.layout.custom_share_popup_window, null);
        wechat_friend = mPopView.findViewById(R.id.wechat_friend);
        wechat_friend_circle = mPopView.findViewById(R.id.wechat_friend_circle);
        qq = mPopView.findViewById(R.id.qq);

        wechat_friend.setOnClickListener(this);
        wechat_friend_circle.setOnClickListener(this);
        qq.setOnClickListener(this);
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
        if (mListener != null)
            switch (v.getId()) {
                case R.id.wechat_friend:
                    mListener.setOnItemClick(0);
                    break;
                case R.id.wechat_friend_circle:
                    mListener.setOnItemClick(1);
                    break;
                case R.id.qq:
                    mListener.setOnItemClick(2);
                    break;
            }
        dismiss();
    }

}
