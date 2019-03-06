package com.example.administrator.sportsfitness.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CustomSingleChoicePopupWindow extends PopupWindow {

    private String TAG = getClass().getSimpleName();
    private Context context;
    private View mPopView;
    private LoopView loop_view;
    private TextView confirm;

    private int currentSelectIndex = 0;
    private int currentSelectType = 0;
    private String currentSelectContent;
    private TextView title_name;
    private int currentIndex = 0;
    private List<String> list = new ArrayList<>();

    public CustomSingleChoicePopupWindow(Context context) {
        super(context);
        this.context = context;
        init();
        setPopupWindow();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.custom_single_choice_popup_window, null);
        loop_view = mPopView.findViewById(R.id.loop_view);
        confirm = mPopView.findViewById(R.id.confirm);
        title_name = mPopView.findViewById(R.id.title_name);
        loop_view.setNotLoop();
        loop_view.setDividerColor(context.getResources().getColor(R.color.transparent));
        loop_view.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentIndex = index;
                switch (currentSelectType) {
                    case 0:
                        currentSelectContent = list.get(index);
                        break;
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    SystemUtil.windowToLight(context);
                    mListener.setOnItemClick(currentSelectType, currentIndex);
                    dismiss();
                }
            }
        });
    }

    /**
     * 选择类型
     *
     * @param type    0 年龄  1 性别
     * @param content 当前选中内容
     */
    public void selectType(int type, String content) {
        currentSelectType = type;
        list.clear();
        switch (type) {
            case 0:
                list.addAll(Arrays.asList(context.getResources().getStringArray(R.array.gender)));
                loop_view.setItems(Arrays.asList(context.getResources().getStringArray(R.array.gender)));
                currentSelectIndex = list.indexOf(content);
                title_name.setText(context.getString(R.string.gender));
                break;
            case 1:
                list.addAll(Arrays.asList(context.getResources().getStringArray(R.array.dynamic_visible)));
                loop_view.setItems(Arrays.asList(context.getResources().getStringArray(R.array.dynamic_visible)));
                currentSelectIndex = list.indexOf(content);
                title_name.setText(context.getString(R.string.dynamic_visible));
                break;
        }
        loop_view.refreshDrawableState();
        loop_view.setCurrentPosition(currentSelectIndex);
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
        void setOnItemClick(int currentSelectType, int currentIndex);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}
