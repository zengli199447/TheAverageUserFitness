package com.example.administrator.sportsFitness.ui.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.FriendsCircleNetBean;
import com.example.administrator.sportsFitness.model.bean.PraiseStatusNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.activity.component.DynamicDetailsActivity;
import com.example.administrator.sportsFitness.ui.activity.component.ForwardingActivity;
import com.example.administrator.sportsFitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsFitness.ui.adapter.ControllerFriendsAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerFriendsCircle extends ControllerClassObserver implements ControllerFriendsAdapter.DynamicParentClickListener,
        CustomConditionsPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;
    int type;
    String userId;
    private ControllerFriendsAdapter controllerFriendsAdapter;
    private ArrayList<String> photoList = new ArrayList<>();
    List<FriendsCircleNetBean.ResultBean.NewsBean> list = new ArrayList<>();
    private AlbumBuilder albumBuilder;
    private CustomConditionsPopupWindow customConditionsPopupWindow;
    private int pageNumber = 1;
    private int newDataSize;
    private int selectIndex;
    private ShowDialog instance;
    private FriendsCircleNetBean.ResultBean.NewsBean newsBean;

    public ControllerFriendsCircle(SwipeRefreshLayout swipe_layout, TextView empty_layout, RecyclerView recycler_view, int type, String userId) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
        this.type = type;
        this.userId = userId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.REPORT_INPUT:
                NetCodeViolation(commonevent.getTemp_str());
                break;
            case EventCode.DYNAMIC_REFRESH:
            case EventCode.FORWARDING_DYNAMIC_SUCCESSFUL:
                pageNumber = 1;
                NetFriendsCircle();
                break;
            case EventCode.DELET_DYNAMIC:
                NetDeletDynamic();
                break;
            case EventCode.DYNAMIC_SELECT:
                pageNumber = 1;
                if (commonevent.getTemp_str().contains(context.getString(R.string.friends_circle))) {
                    type = 2;
                } else {
                    type = 1;
                }
                NetFriendsCircle();
                break;
        }
    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        albumBuilder = new AlbumBuilder(context);
        customConditionsPopupWindow = new CustomConditionsPopupWindow(context);
        instance = ShowDialog.getInstance();
        initAdapter();
        initListener();
        NetFriendsCircle();
    }

    private void initListener() {
        customConditionsPopupWindow.setOnItemClickListener(this);
        customConditionsPopupWindow.setOnDismissListener(this);
        swipe_layout.setOnRefreshListener(this);
        recycler_view.addOnScrollListener(scrollListener);
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        controllerFriendsAdapter = new ControllerFriendsAdapter(context, list, false);
        recycler_view.setAdapter(controllerFriendsAdapter);
        controllerFriendsAdapter.setDynamicParentClickListener(this);

    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (controllerFriendsAdapter != null) {
                controllerFriendsAdapter.setLoadState(controllerFriendsAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow || list.size() == DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    NetFriendsCircle();
                } else {
                    // 显示加载到底的提示
                    controllerFriendsAdapter.setLoadState(controllerFriendsAdapter.LOADING_END);
                }
                if (newDataSize == 0 || newDataSize < DataClass.DefaultInformationFlow) {
                    controllerFriendsAdapter.setLoadState(controllerFriendsAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {

        }
    };

    @Override
    public void onChildClickListener(int position, int parentIndex) {
        photoList.clear();
        for (FriendsCircleNetBean.ResultBean.NewsBean.ImgpathjsonBean imgpathjsonBean : list.get(parentIndex).getImgpathjson()) {
            photoList.add(SystemUtil.JudgeUrl(imgpathjsonBean.getImgpath()));
        }
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCheckClickListener(int position, int id) {
        this.selectIndex = position;
        newsBean = list.get(position);
        try {
            switch (id) {
                case -1:
                case R.id.comments:
                    Intent dynamicDetailsIntent = new Intent(context, DynamicDetailsActivity.class);
                    dynamicDetailsIntent.putExtra("userId", newsBean.getUserid());
                    dynamicDetailsIntent.putExtra("dynamicId", newsBean.getNewsid());
                    context.startActivity(dynamicDetailsIntent);
                    break;
                case R.id.about:
                    customConditionsPopupWindow.showAtLocation(recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    SystemUtil.windowToDark(context);
                    break;
                case R.id.user_img:
                    Intent theDetailsInformationIntent = new Intent(context, TheDetailsInformationActivity.class);
                    theDetailsInformationIntent.putExtra("userId", newsBean.getUserid());
                    theDetailsInformationIntent.putExtra("userName", newsBean.getSecondname());
                    context.startActivity(theDetailsInformationIntent);
                    break;
                case R.id.forwarding:
                    Intent forwardingIntent = new Intent(context, ForwardingActivity.class);
                    forwardingIntent.putExtra("forwardingId", newsBean.getNewsid());
                    context.startActivity(forwardingIntent);
                    break;
                case R.id.support_check:
                    NetOrSupport();
                    break;
                case R.id.clear_dynamic:
                    instance.showConfirmOrNoDialog(context, context.getString(R.string.clear_dynamic), EventCode.ONSTART, EventCode.DELET_DYNAMIC, EventCode.ONSTART);
                    break;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(context);
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.report:
                ShowDialog.getInstance().showInputDialog(context, EventCode.REPORT_INPUT);
                break;
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        NetFriendsCircle();
    }

    //刷新
    private void refreshView() {
        if (pageNumber != 1) {
            controllerFriendsAdapter.notifyItemRangeInserted(list.size() - newDataSize, newDataSize);
        } else {
            controllerFriendsAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
    }

    /**
     * 好友圈
     *
     * @param type   查看类型 1.表示所有 2.表示好友 3.表示特定某人的动态
     * @param userId type=3 需如对应用户Id
     */
    public void NetFriendsCircle() {
        swipe_layout.setRefreshing(true);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", type);
        linkedHashMap.put("userid_view", userId);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.FriendsCircle(map)
                .compose(RxUtil.<FriendsCircleNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<FriendsCircleNetBean>(toastUtil) {
                    @Override
                    public void onNext(FriendsCircleNetBean friendsCircleNetBean) {
                        if (friendsCircleNetBean.getStatus() == 1) {
                            FriendsCircleNetBean.ResultBean result = friendsCircleNetBean.getResult();
                            List<FriendsCircleNetBean.ResultBean.NewsBean> news = result.getNews();
                            newDataSize = news.size();
                            if (pageNumber == 1) {
                                list.clear();
                                if (news.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            list.addAll(news);
                            refreshView();
                        } else {
                            toastUtil.showToast(friendsCircleNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //点赞、取消点赞
    public void NetOrSupport() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_ZAN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", newsBean.getNewsid());
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.Praise(map)
                .compose(RxUtil.<PraiseStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PraiseStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(PraiseStatusNetBean praiseStatusNetBean) {
                        if (praiseStatusNetBean.getStatus() == 1) {
                            int total_zan = newsBean.getTotal_zan();
                            if (praiseStatusNetBean.getIfzan_cleck() == 1) {
                                newsBean.setIfzan_cleck("1");
                                newsBean.setTotal_zan(total_zan + 1);
                            } else {
                                newsBean.setIfzan_cleck("0");
                                newsBean.setTotal_zan(total_zan - 1);
                            }
                        } else {
                            toastUtil.showToast(praiseStatusNetBean.getMessage());
                        }
                        controllerFriendsAdapter.notifyItemChanged(selectIndex);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        controllerFriendsAdapter.notifyItemChanged(selectIndex);
                        super.onError(e);
                    }
                }));
    }

    //举报违规动态
    private void NetCodeViolation(String content) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_REPORT_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", newsBean.getNewsid());
        linkedHashMap.put("content", content);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.to_report_success), EventCode.ONSTART);
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //删除动态
    private void NetDeletDynamic() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_DEL_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", newsBean.getNewsid());
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            list.remove(selectIndex);
                            if (list.size() > 0) {
                                controllerFriendsAdapter.notifyItemRemoved(selectIndex);
                                controllerFriendsAdapter.notifyItemRangeChanged(0, list.size());
                            } else {
                                controllerFriendsAdapter.notifyDataSetChanged();
                            }
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.delet_dynamic_successful), EventCode.DELET_DYNAMIC_SUCCESSFUL);
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OnFriendsCircleRefreshListener {
        void onFriendsCircleRefreshListener(Object object);
    }

    private OnFriendsCircleRefreshListener onFriendsCircleRefreshListener;

    public void setOnFriendsCircleRefreshListener(OnFriendsCircleRefreshListener onFriendsCircleRefreshListener) {
        this.onFriendsCircleRefreshListener = onFriendsCircleRefreshListener;
    }

}
