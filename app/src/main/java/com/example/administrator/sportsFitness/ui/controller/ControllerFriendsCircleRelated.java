package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.FriendsCircleRelatedNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsFitness.ui.activity.component.WebConcentratedActivity;
import com.example.administrator.sportsFitness.ui.adapter.FriendsCircleRelatedAdapter;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerFriendsCircleRelated extends ControllerClassObserver implements SwipeRefreshLayout.OnRefreshListener, FriendsCircleRelatedAdapter.OnFriendClickListener {

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;
    private FriendsCircleRelatedAdapter friendsCircleRelatedAdapter;
    private int pageNumber = 1;
    private int type;
    int newDataSize = 0;
    List<FriendsCircleRelatedNetBean.ResultBean.UserlistBean> list = new ArrayList<>();

    public ControllerFriendsCircleRelated(SwipeRefreshLayout swipe_layout, TextView empty_layout, RecyclerView recycler_view, int type) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
        this.type = type;
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
        NetFriendsCircleRelated();
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
        swipe_layout.setOnRefreshListener(this);
        recycler_view.addOnScrollListener(scrollListener);
        friendsCircleRelatedAdapter.setOnFriendClickListener(this);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (friendsCircleRelatedAdapter != null) {
                friendsCircleRelatedAdapter.setLoadState(friendsCircleRelatedAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow || list.size() == DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    NetFriendsCircleRelated();
                } else {
                    // 显示加载到底的提示
                    friendsCircleRelatedAdapter.setLoadState(friendsCircleRelatedAdapter.LOADING_END);
                }
                if (newDataSize == 0 || newDataSize < DataClass.DefaultInformationFlow) {
                    friendsCircleRelatedAdapter.setLoadState(friendsCircleRelatedAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {

        }
    };

    @Override
    public void onRefresh() {
        pageNumber = 1;
        NetFriendsCircleRelated();
    }

    private void refreshView() {
        friendsCircleRelatedAdapter.notifyDataSetChanged();
        if (pageNumber != 1) {
            friendsCircleRelatedAdapter.notifyItemRangeInserted(list.size() - newDataSize, newDataSize);
        } else {
            friendsCircleRelatedAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onFriendClickListener(int position) {
        FriendsCircleRelatedNetBean.ResultBean.UserlistBean userlistBean = list.get(position);
        switch (type) {
            case 0:
                Intent webIntent = new Intent(context, WebConcentratedActivity.class);
                webIntent.putExtra("link_all", new StringBuffer().append(DataClass.URL).append("chat/chat.php?fromuid=").append(DataClass.USERID).append("&touid=").append(userlistBean.getUserid()).toString());
                webIntent.putExtra("titleName", userlistBean.getSecondname());
                context.startActivity(webIntent);
                break;
            case 1:
            case 2:
                Intent theDetailsInformationIntent = new Intent(context, TheDetailsInformationActivity.class);
                theDetailsInformationIntent.putExtra("userId", userlistBean.getUserid());
                theDetailsInformationIntent.putExtra("userName", userlistBean.getSecondname());
                context.startActivity(theDetailsInformationIntent);
                break;
        }
    }

    //好友列表
    private void NetFriendsCircleRelated() {
        swipe_layout.setRefreshing(true);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.FRIEND_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", type + 1);
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
                            newDataSize = userlist.size();
                            if (pageNumber == 1) {
                                list.clear();
                                if (userlist.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            list.addAll(userlist);
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


}
