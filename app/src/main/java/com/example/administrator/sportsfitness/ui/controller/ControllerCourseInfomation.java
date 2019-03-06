package com.example.administrator.sportsfitness.ui.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.ViewGroup;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.BusSelectDicersifiedBean;
import com.example.administrator.sportsfitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsfitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.activity.component.SelectDiversifiedFormActivity;
import com.example.administrator.sportsfitness.ui.adapter.GymInfomationAdapter;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCourseInfomation extends ControllerClassObserver implements SwipeMenuItemClickListener, SwipeMenuCreator, GymInfomationAdapter.OnAddClickListener {

    SwipeMenuRecyclerView recycler_view;
    Bundle bundle;
    private GymInfomationAdapter InfomationAdapter;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();
    private String stringValueId = "";
    private String stringValueText = "";

    public ControllerCourseInfomation(SwipeMenuRecyclerView recycler_view, Bundle bundle) {
        this.recycler_view = recycler_view;
        this.bundle = bundle;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.INVITE_FRIENDS:
                BusSelectDicersifiedBean busSelectDicersifiedBean = commonevent.getBusSelectDicersifiedBean();
                stringValueId = busSelectDicersifiedBean.getStringValueId();
                stringValueText = busSelectDicersifiedBean.getStringValueText();
                formatForm();
                break;
        }
    }

    private void formatForm() {
        list.clear();
        idList.clear();
        list.add(DataClass.UNAME);
        idList.add(DataClass.USERID);
        if (!stringValueText.isEmpty()) {
            String[] split = stringValueText.split(",");
            String[] idSplit = stringValueId.split(",");
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
                idList.add(idSplit[i]);
            }
        }
        refreshView();
        joint();
    }

    private void joint() {
        String line = "";
        String theComma = "";
        for (int i = 0; i < idList.size(); i++) {
            if (i > 0) {
                theComma = ",";
            }
            line = new StringBuffer().append(line).append(theComma).append(idList.get(i)).toString();
        }
        stringValueId = line;
        if (onRefreshCountListener != null)
            onRefreshCountListener.onRefreshCountListener(idList.size());
    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        stringValueId = DataClass.USERID;
        list.add(DataClass.UNAME);
        initAdapter();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        recycler_view.setSwipeMenuCreator(this);
        recycler_view.setSwipeMenuItemClickListener(this);

        InfomationAdapter = new GymInfomationAdapter(context, list);
        recycler_view.setAdapter(InfomationAdapter);
        InfomationAdapter.setOnAddClickListener(this);
    }

    private void refreshView() {
        InfomationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
        SwipeMenuItem deleteItem = new SwipeMenuItem(context)
                .setBackground(R.drawable.red_text)
                .setTextColor(context.getResources().getColor(R.color.white))
                .setText(R.string.delete)
                .setWidth(context.getResources().getDimensionPixelSize(R.dimen.dp80))
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        if (viewType == 0)
            swipeRightMenu.addMenuItem(deleteItem);
    }

    @Override
    public void onItemClick(SwipeMenuBridge menuBridge) {
        menuBridge.closeMenu();
        int direction = menuBridge.getDirection();
        int adapterPosition = menuBridge.getAdapterPosition();
        int position = menuBridge.getPosition();
        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            list.remove(adapterPosition);//删除item
            idList.remove(adapterPosition);
            InfomationAdapter.notifyDataSetChanged();
            joint();
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onAddClickListener() {
        Intent intent = new Intent(context, SelectDiversifiedFormActivity.class);
        intent.setFlags(EventCode.INVITE_FRIENDS);
        intent.putExtra("stringValueId", stringValueId);
        context.startActivity(intent);
    }

    /**
     * 预约
     *
     * @param datatype  1.场馆 2.课程 3.私教
     * @param shopid    场馆ID
     * @param coursesid 课程ID或私教ID
     * @param userids   参与人
     */
    public void NetConvention() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GROUP_JOIN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", 2);
        linkedHashMap.put("shopid", "");
        linkedHashMap.put("coursesid", bundle.getString("Coursesid"));
        linkedHashMap.put("userids", stringValueId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.TopUp(map)
                .compose(RxUtil.<TopUpNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TopUpNetBean>(toastUtil) {
                    @Override
                    public void onNext(TopUpNetBean topUpNetBean) {
                        if (topUpNetBean.getStatus() == 1) {
                            if (onRefreshCountListener != null)
                                onRefreshCountListener.onPayActionListener(topUpNetBean.getResult());
                        } else {
                            toastUtil.showToast(topUpNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }


    public interface OnRefreshCountListener {
        void onRefreshCountListener(int count);

        void onPayActionListener(TopUpNetBean.ResultBean result);
    }

    private OnRefreshCountListener onRefreshCountListener;

    public void setOnRefreshCountListener(OnRefreshCountListener onRefreshCountListener) {
        this.onRefreshCountListener = onRefreshCountListener;
    }


}
