package com.example.administrator.sportsfitness.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.model.bean.FriendsCircleSelectBean;
import com.example.administrator.sportsfitness.ui.adapter.FriendsCircleSelectAdapter;
import com.example.administrator.sportsfitness.utils.LogUtil;
import com.example.administrator.sportsfitness.utils.SystemUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Administrator on 2017/10/17.
 */

public class CustomSelectFriendsPopupWindow extends PopupWindow implements View.OnClickListener, FriendsCircleSelectAdapter.FriendsCircleSelectListener {

    private String TAG = getClass().getSimpleName();

    private View mPopView;
    private OnItemSelectClickListener mListener;
    private Context context;

    private RecyclerView recycler_view;
    private List<FriendsCircleSelectBean> list = new ArrayList<>();
    private FriendsCircleSelectAdapter friendsCircleSelectAdapter;
    private int height;

    public CustomSelectFriendsPopupWindow(Context context) {
        super(context);
        this.context = context;
        initData();
        initView();
        initAdapter();
    }

    private void initData() {
        List<String> strings = Arrays.asList(context.getResources().getStringArray(R.array.friends_circle));
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).equals(context.getString(R.string.all))) {
                list.add(new FriendsCircleSelectBean(strings.get(i), true));
            } else {
                list.add(new FriendsCircleSelectBean(strings.get(i), false));
            }
        }
    }

    private void initView() {
        mPopView = LayoutInflater.from(context).inflate(R.layout.custom_select_friends_popup_window, null);
        recycler_view = mPopView.findViewById(R.id.recycler_view);
        height = mPopView.findViewById(R.id.pop_layout).getTop();
        setPopupWindow();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        friendsCircleSelectAdapter = new FriendsCircleSelectAdapter(context, list);
        recycler_view.setAdapter(friendsCircleSelectAdapter);
        friendsCircleSelectAdapter.setFriendsCircleSelectListener(this);
        friendsCircleSelectAdapter.notifyDataSetChanged();
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
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        try {
            mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁
                @Override
                public boolean onTouch(View v, MotionEvent event) {
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
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        } finally {
            dismiss();
        }

    }

    @Override
    public void onFriendsCircleSelectListener(String content, int position) {
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                list.get(i).setStatus(true);
            } else {
                list.get(i).setStatus(false);
            }
        }
        friendsCircleSelectAdapter.notifyDataSetChanged();
        if (mListener != null)
            mListener.setOnItemClick(content);
        SystemUtil.windowToLight(context);
        dismiss();
    }

    public interface OnItemSelectClickListener {
        void setOnItemClick(String content);
    }

    public void setOnSelectItemClickListener(OnItemSelectClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

}
