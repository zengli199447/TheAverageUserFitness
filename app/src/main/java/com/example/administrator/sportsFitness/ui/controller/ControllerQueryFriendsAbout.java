package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.FriendsCircleRelatedNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsFitness.ui.adapter.FriendsCircleRelatedAdapter;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/2/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerQueryFriendsAbout extends ControllerClassObserver implements FriendsCircleRelatedAdapter.OnFriendClickListener {

    TextView empty_layout;
    RecyclerView recycler_view;
    private FriendsCircleRelatedAdapter friendsCircleRelatedAdapter;
    List<FriendsCircleRelatedNetBean.ResultBean.UserlistBean> list = new ArrayList<>();

    public ControllerQueryFriendsAbout(TextView empty_layout, RecyclerView recycler_view) {
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
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
        initAdpater();
        initListener();
        NetFriendsCircleRelated("");
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdpater() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        friendsCircleRelatedAdapter = new FriendsCircleRelatedAdapter(context, list);
        recycler_view.setAdapter(friendsCircleRelatedAdapter);
    }

    private void initListener() {
        friendsCircleRelatedAdapter.setOnFriendClickListener(this);
    }

    @Override
    public void onFriendClickListener(int position) {
        FriendsCircleRelatedNetBean.ResultBean.UserlistBean userlistBean = list.get(position);
        Intent theDetailsInformationIntent = new Intent(context, TheDetailsInformationActivity.class);
        theDetailsInformationIntent.putExtra("userId", userlistBean.getUserid());
        theDetailsInformationIntent.putExtra("userName", userlistBean.getSecondname());
        context.startActivity(theDetailsInformationIntent);
    }

    //好友列表
    public void NetFriendsCircleRelated(String content) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.FRIEND_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", "3");
        linkedHashMap.put("pagenum", 1);
        linkedHashMap.put("keyword", content);
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
                            if (userlist.size() == 0) {
                                empty_layout.setVisibility(View.VISIBLE);
                            } else {
                                empty_layout.setVisibility(View.GONE);
                            }
                            list.clear();
                            list.addAll(userlist);
                            friendsCircleRelatedAdapter.notifyDataSetChanged();
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


}
