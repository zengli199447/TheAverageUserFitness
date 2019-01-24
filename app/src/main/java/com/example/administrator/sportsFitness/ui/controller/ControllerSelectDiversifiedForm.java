package com.example.administrator.sportsFitness.ui.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.BusSelectDicersifiedBean;
import com.example.administrator.sportsFitness.model.bean.CourseTypeNetBean;
import com.example.administrator.sportsFitness.model.bean.FriendsCircleRelatedNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.adapter.SelectDiversifiedFormAdapter;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerSelectDiversifiedForm extends ControllerClassObserver implements SelectDiversifiedFormAdapter.CheckItemListener {

    RecyclerView recycler_view;
    String typeId;
    int flags;
    private String stringValueId;
    private SelectDiversifiedFormAdapter selectDiversifiedFormAdapter;
    private List<Object> list = new ArrayList<>();
    private List<String> textType = new ArrayList<>();
    private List<String> IdType = new ArrayList<>();
    int pageNumber = 1;
    String idChain = "";

    public ControllerSelectDiversifiedForm(RecyclerView recycler_view, String typeId, int flags, String stringValueId) {
        this.recycler_view = recycler_view;
        this.typeId = typeId;
        this.flags = flags;
        this.stringValueId = stringValueId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        initAdapter();
        switch (flags) {
            case EventCode.INVITE_FRIENDS:
                NetFriendsCircleRelated(pageNumber);
                break;
        }
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        selectDiversifiedFormAdapter = new SelectDiversifiedFormAdapter(context, list);
        recycler_view.setAdapter(selectDiversifiedFormAdapter);
        selectDiversifiedFormAdapter.setCheckItemListener(this);
    }

    private void refreshView() {
        selectDiversifiedFormAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCheckItemListener(int position, boolean status) {
        Object object = list.get(position);
        switch (flags) {
            case EventCode.INVITE_FRIENDS:
                FriendsCircleRelatedNetBean.ResultBean.UserlistBean userlistBean = (FriendsCircleRelatedNetBean.ResultBean.UserlistBean) object;
                userlistBean.setSelectStatus(status);
                break;
        }
    }

    //过滤选中
    public void SendLineData() {
        textType.clear();
        IdType.clear();
        switch (flags) {
            case EventCode.INVITE_FRIENDS:
                for (Object object : list) {
                    FriendsCircleRelatedNetBean.ResultBean.UserlistBean userlistBean = (FriendsCircleRelatedNetBean.ResultBean.UserlistBean) object;
                    if (userlistBean.isSelectStatus()) {
                        textType.add(userlistBean.getSecondname());
                        IdType.add(userlistBean.getFriendid());
                    }
                }
                break;
        }
        RxBus.getDefault().post(new CommonEvent(flags, new BusSelectDicersifiedBean(joint(IdType, ","), joint(textType, ","))));
    }

    //拼接
    private String joint(List<String> list, String symbol) {
        String line = "";
        String theComma = "";
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                theComma = symbol;
            }
            line = new StringBuffer().append(line).append(theComma).append(list.get(i)).toString();
        }
        return line;
    }

    //刷新已选中项
    private void repetition(Object object) {
        String[] split = stringValueId.split(",");
        switch (flags) {
            case EventCode.INVITE_FRIENDS:
                for (int i = 0; i < split.length; i++) {
                    for (Object o : list) {
                        FriendsCircleRelatedNetBean.ResultBean.UserlistBean userlistBean = (FriendsCircleRelatedNetBean.ResultBean.UserlistBean) o;
                        if (userlistBean.getFriendid().equals(split[i])) {
                            userlistBean.setSelectStatus(true);
                        }
                    }
                }
                break;
            case EventCode.ONSTART:

                break;
        }
        if (onSelectDiversifiedFormRefreshListener != null)
            onSelectDiversifiedFormRefreshListener.onSelectDiversifiedFormRefreshListener(object);
    }

    //好友列表
    public void NetFriendsCircleRelated(final int pageNumber) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.FRIEND_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", 1);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.FriendsCircleRelated(map)
                .compose(RxUtil.<FriendsCircleRelatedNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<FriendsCircleRelatedNetBean>(toastUtil) {
                    @Override
                    public void onNext(FriendsCircleRelatedNetBean friendsCircleRelatedNetBean) {
                        if (friendsCircleRelatedNetBean.getStatus() == 1) {
                            FriendsCircleRelatedNetBean.ResultBean result = friendsCircleRelatedNetBean.getResult();
                            List<FriendsCircleRelatedNetBean.ResultBean.UserlistBean> userlist = result.getUserlist();
                            if (pageNumber == 1)
                                list.clear();
                            list.addAll(userlist);
                            if (!stringValueId.isEmpty()) {
                                repetition(result);
                            } else {
                                if (onSelectDiversifiedFormRefreshListener != null)
                                    onSelectDiversifiedFormRefreshListener.onSelectDiversifiedFormRefreshListener(result);
                            }
                            refreshView();
                        } else {
                            toastUtil.showToast(friendsCircleRelatedNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }


    public interface OnSelectDiversifiedFormRefreshListener {
        void onSelectDiversifiedFormRefreshListener(Object object);
    }

    private OnSelectDiversifiedFormRefreshListener onSelectDiversifiedFormRefreshListener;

    public void setOnSelectDiversifiedFormRefreshListener(OnSelectDiversifiedFormRefreshListener onSelectDiversifiedFormRefreshListener) {
        this.onSelectDiversifiedFormRefreshListener = onSelectDiversifiedFormRefreshListener;
    }


}
