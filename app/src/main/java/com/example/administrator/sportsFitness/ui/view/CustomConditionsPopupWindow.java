package com.example.administrator.sportsFitness.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/10/17.
 */

public class CustomConditionsPopupWindow extends PopupWindow implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();

    private TextView confirm;
    private TextView cancle;
    private TextView release_img;
    private TextView release_video;
    private View line_release;
    private View mPopView;
    private OnItemClickListener mListener;
    private Context context;


    public CustomConditionsPopupWindow(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.custom_conditions_popup_window, null);
        confirm = mPopView.findViewById(R.id.report);
        cancle = mPopView.findViewById(R.id.cancle);

        release_img = mPopView.findViewById(R.id.release_img);
        release_video = mPopView.findViewById(R.id.release_video);
        line_release = mPopView.findViewById(R.id.line_release);

        confirm.setOnClickListener(this);
        cancle.setOnClickListener(this);
        release_img.setOnClickListener(this);
        release_video.setOnClickListener(this);
        setPopupWindow();
    }

    public void refreshView() {
        release_img.setVisibility(View.VISIBLE);
        release_video.setVisibility(View.VISIBLE);
        line_release.setVisibility(View.VISIBLE);
        confirm.setVisibility(View.GONE);
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

    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            SystemUtil.windowToLight(context);
            mListener.setOnItemClick(v);
        }
        dismiss();
    }


}
