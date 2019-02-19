package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.CommentsNetBean;
import com.example.administrator.sportsFitness.model.bean.FriendsCircleNetBean;
import com.example.administrator.sportsFitness.model.bean.HomePageInfoNetBean;
import com.example.administrator.sportsFitness.model.bean.PraiseStatusNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.activity.component.DynamicDetailsActivity;
import com.example.administrator.sportsFitness.ui.activity.component.ForwardingActivity;
import com.example.administrator.sportsFitness.ui.activity.component.TheTagModifyActivity;
import com.example.administrator.sportsFitness.ui.adapter.ControllerCommentsAdapter;
import com.example.administrator.sportsFitness.ui.adapter.ControllerFriendsAdapter;
import com.example.administrator.sportsFitness.ui.dialog.HelpfulHintsDialog;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsFitness.ui.view.FlowLayout;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.FlowLayoutBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerIntroduce extends ControllerClassObserver implements ControllerFriendsAdapter.DynamicParentClickListener, CustomConditionsPopupWindow.OnItemClickListener,
        PopupWindow.OnDismissListener, FlowLayoutBuilder.FlowClickListener, ControllerCommentsAdapter.CommentsParentClickListener {

    RecyclerView dynamic_recycler_view;
    RecyclerView comments_recycler_view;
    FlowLayout flow_layout;
    private String userId;
    private AlbumBuilder albumBuilder;
    private CustomConditionsPopupWindow customConditionsPopupWindow;
    private ControllerFriendsAdapter controllerFriendsAdapter;
    private ArrayList<String> photoList = new ArrayList<>();
    private FlowLayoutBuilder flowLayoutBuilder;
    private ControllerCommentsAdapter controllerCommentsAdapter;
    private ShowDialog instance;
    private FriendsCircleNetBean.ResultBean.NewsBean dynamicBean;
    private CommentsNetBean.ResultBean.CommentBean commentInfoBean;
    ArrayList<FriendsCircleNetBean.ResultBean.NewsBean> dynamicList = new ArrayList<>();
    ArrayList<CommentsNetBean.ResultBean.CommentBean> commentsList = new ArrayList<>();
    private String tags;
    private List<String> tagList = new ArrayList<>();
    private HelpfulHintsDialog helpfulHintsDialog;

    public ControllerIntroduce(RecyclerView dynamic_recycler_view, RecyclerView comments_recycler_view, FlowLayout flow_layout, String userId) {
        this.dynamic_recycler_view = dynamic_recycler_view;
        this.comments_recycler_view = comments_recycler_view;
        this.flow_layout = flow_layout;
        this.userId = userId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.REPORT_INPUT_DETAILS:
                NetCodeViolation(commonevent.getTemp_str());
                break;
            case EventCode.DELET_DYNAMIC_DETAILS:
                NetDeletDynamic();
                break;
            case EventCode.TAG_REFRSH:
            case EventCode.DELET_DYNAMIC_SUCCESSFUL:
                NetHomePage();
                if (helpfulHintsDialog != null)
                    helpfulHintsDialog.dismiss();
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
        instance = ShowDialog.getInstance();
        flowLayoutBuilder = new FlowLayoutBuilder(context, flow_layout, 0);
        customConditionsPopupWindow = new CustomConditionsPopupWindow(context);
        initAdapter();
        initListener();
        NetHomePage();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();

    }

    private void initAdapter() {
        dynamic_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        controllerFriendsAdapter = new ControllerFriendsAdapter(context, dynamicList, true, 1);
        dynamic_recycler_view.setAdapter(controllerFriendsAdapter);
        controllerFriendsAdapter.setDynamicParentClickListener(this);

        comments_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        controllerCommentsAdapter = new ControllerCommentsAdapter(context, commentsList, true, 1);
        comments_recycler_view.setAdapter(controllerCommentsAdapter);
        controllerCommentsAdapter.setCommentsParentClickListener(this);

    }

    private void initListener() {
        customConditionsPopupWindow.setOnItemClickListener(this);
        customConditionsPopupWindow.setOnDismissListener(this);
        flowLayoutBuilder.setFlowClickListener(this);
    }

    private void refreshView(int type) {
        switch (type) {
            case 0:
                controllerFriendsAdapter.notifyDataSetChanged();
                controllerCommentsAdapter.notifyDataSetChanged();
                break;
            case 1:
                tagList.clear();
                if (tags != null && !tags.isEmpty()) {
                    String[] split = tags.split(",");
                    for (int i = 0; i < split.length; i++) {
                        tagList.add(split[i]);
                    }
                }
                if (DataClass.USERID.equals(userId))
                    tagList.add(context.getString(R.string.add_tag));
                flowLayoutBuilder.initLayout(tagList, DataClass.USERID.equals(userId) ? true : false, false);
                if (netWorkResLisrtener != null)
                    netWorkResLisrtener.onNetWorkResLisrtener();
                break;
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(context);
    }

    @Override
    public void onChildClickListener(int position, int parentIndex) {
        photoList.clear();
        List<FriendsCircleNetBean.ResultBean.NewsBean.ImgpathjsonBean> imgpathjson = dynamicBean.getImgpathjson();
        for (FriendsCircleNetBean.ResultBean.NewsBean.ImgpathjsonBean imgpathjsonBean : imgpathjson) {
            photoList.add(SystemUtil.JudgeUrl(imgpathjsonBean.getImgpath()));
        }
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    //动态事件
    @Override
    public void onCheckClickListener(int position, int id) {
        switch (id) {
            case -1:
            case R.id.comments:
                Intent dynamicDetailsIntent = new Intent(context, DynamicDetailsActivity.class);
                dynamicDetailsIntent.putExtra("userId", dynamicBean.getUserid());
                dynamicDetailsIntent.putExtra("dynamicId", dynamicBean.getNewsid());
                context.startActivity(dynamicDetailsIntent);
                break;
            case R.id.about:
                customConditionsPopupWindow.showAtLocation(dynamic_recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(context);
                break;
            case R.id.forwarding:
                Intent forwardingIntent = new Intent(context, ForwardingActivity.class);
                forwardingIntent.putExtra("forwardingId", dynamicBean.getNewsid());
                context.startActivity(forwardingIntent);
                break;
            case R.id.support_check:
                NetOrSupport();
                break;
            case R.id.clear_dynamic:
                instance.showConfirmOrNoDialog(context, context.getString(R.string.clear_dynamic), EventCode.ONSTART, EventCode.DELET_DYNAMIC_DETAILS, EventCode.ONSTART);
                break;
        }
    }

    //pop事件
    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.report:
                ShowDialog.getInstance().showInputDialog(context, EventCode.REPORT_INPUT_DETAILS);
                break;
        }
    }

    //标签事件
    @Override
    public void onFlowClickListener(View view, int position, int type) {
        switch (view.getId()) {
            case R.id.contentTextView:
                if (DataClass.USERID.equals(userId))
                    context.startActivity(new Intent(context, TheTagModifyActivity.class));
                break;
        }
    }

    //评论事件
    @Override
    public void onCommentsChildClickListener(int position, int parentIndex) {
        photoList.clear();
        List<CommentsNetBean.ResultBean.CommentBean.ImgBean> imgBeans = commentInfoBean.getImg();
        for (CommentsNetBean.ResultBean.CommentBean.ImgBean imgBean : imgBeans) {
            photoList.add(SystemUtil.JudgeUrl(imgBean.getImgpath()));
        }
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    @Override
    public void onCommentsCheckClickListener(int position, int id) {
        switch (id) {
            case -1:
                toastUtil.showToast("position : " + position);
                break;
            case R.id.user_img:
                toastUtil.showToast("position : " + position);
                break;
        }
    }

    //用户主页
    public void NetHomePage() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_HOMEPAGE_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("userid_view", userId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.HomePageInfo(map)
                .compose(RxUtil.<HomePageInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HomePageInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(HomePageInfoNetBean homePageInfoNetBean) {
                        if (homePageInfoNetBean.getStatus() == 1) {
                            HomePageInfoNetBean.ResultBean result = homePageInfoNetBean.getResult();
                            tags = result.getUser().getTags();

                            List<HomePageInfoNetBean.ResultBean.NewsBean> news = result.getNews();
                            List<HomePageInfoNetBean.ResultBean.CommentBean> comment = result.getComment();

                            List<FriendsCircleNetBean.ResultBean.NewsBean.ImgpathjsonBean> imgpathjsonBeanList = new ArrayList<>();
                            List<FriendsCircleNetBean.ResultBean.NewsBean.UserZhuanBean> userZhuanBeanList = new ArrayList<>();

                            dynamicList.clear();
                            commentsList.clear();

                            if (news.size() > 0) {
                                HomePageInfoNetBean.ResultBean.NewsBean newsBean = news.get(0);
                                for (HomePageInfoNetBean.ResultBean.NewsBean.ImgpathjsonBean imgpathjson : newsBean.getImgpathjson()) {
                                    FriendsCircleNetBean.ResultBean.NewsBean.ImgpathjsonBean imgpathjsonBean = new FriendsCircleNetBean.ResultBean.NewsBean.ImgpathjsonBean(imgpathjson.getImgpath());
                                    imgpathjsonBeanList.add(imgpathjsonBean);
                                }
                                for (HomePageInfoNetBean.ResultBean.NewsBean.UserZhuanBean userZhuanjson : newsBean.getUser_zhuan()) {
                                    FriendsCircleNetBean.ResultBean.NewsBean.UserZhuanBean userZhuanBean = new FriendsCircleNetBean.ResultBean.NewsBean.UserZhuanBean(userZhuanjson.getSecondname(), userZhuanjson.getUserid());
                                    userZhuanBeanList.add(userZhuanBean);
                                }
                                dynamicBean = new FriendsCircleNetBean.ResultBean.NewsBean(newsBean.getContent(),
                                        newsBean.getCreatedate(),
                                        newsBean.getCreatedate_show(),
                                        newsBean.getIfzan_cleck(),
                                        newsBean.getLimits(),
                                        newsBean.getNewsid(),
                                        newsBean.getNewsid_source(),
                                        newsBean.getPhoto(),
                                        newsBean.getSecondname(),
                                        newsBean.getState(),
                                        newsBean.getTotal_ping(),
                                        newsBean.getTotal_zan(),
                                        newsBean.getTotal_zhuan(),
                                        newsBean.getUserid(),
                                        imgpathjsonBeanList,
                                        userZhuanBeanList);
                                dynamicList.add(dynamicBean);
                            }

                            List<CommentsNetBean.ResultBean.CommentBean.ImgBean> commentsImgBeanList = new ArrayList<>();

                            if (comment.size() > 0) {
                                HomePageInfoNetBean.ResultBean.CommentBean commentBean = comment.get(0);
                                for (CommentsNetBean.ResultBean.CommentBean.ImgBean imgBean : commentBean.getImg()) {
                                    CommentsNetBean.ResultBean.CommentBean.ImgBean imgpathjsonBean = new CommentsNetBean.ResultBean.CommentBean.ImgBean(imgBean.getImgid(), imgBean.getImgpath());
                                    commentsImgBeanList.add(imgpathjsonBean);
                                }
                                commentInfoBean = new CommentsNetBean.ResultBean.CommentBean(commentBean.getCommentid(),
                                        commentBean.getUserid(),
                                        commentBean.getScore(),
                                        commentBean.getRemark(),
                                        commentBean.getReftype(),
                                        commentBean.getRefid(),
                                        commentBean.getCreatedate(),
                                        commentBean.getDatatype(),
                                        commentBean.getRemark_again(),
                                        commentBean.getUserid_teacher(),
                                        commentBean.getOrderheadid(),
                                        commentBean.getSecondname(),
                                        commentBean.getPhoto(),
                                        commentsImgBeanList);
                                commentsList.add(commentInfoBean);
                            }
                            refreshView(0);
                            refreshView(1);
                            if (netWorkResLisrtener != null)
                                netWorkResLisrtener.onHomePageDataListener(result);
                        } else {
                            toastUtil.showToast(homePageInfoNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
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
        linkedHashMap.put("newsid", dynamicBean.getNewsid());
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
        linkedHashMap.put("newsid", dynamicBean.getNewsid());
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            NetHomePage();
                            helpfulHintsDialog = instance.showHelpfulHintsDialog(context, context.getString(R.string.delet_dynamic), EventCode.DELET_DYNAMIC_SUCCESSFUL);
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

    //点赞、取消点赞
    public void NetOrSupport() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_ZAN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", dynamicBean.getNewsid());
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.Praise(map)
                .compose(RxUtil.<PraiseStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PraiseStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(PraiseStatusNetBean praiseStatusNetBean) {
                        if (praiseStatusNetBean.getStatus() == 1) {
                            int totalSupport = dynamicBean.getTotal_zan();
                            if (praiseStatusNetBean.getIfzan_cleck() == 1) {
                                totalSupport = totalSupport + 1;
                                dynamicBean.setIfzan_cleck("1");
                            } else {
                                totalSupport = totalSupport - 1;
                                dynamicBean.setIfzan_cleck("0");
                            }
                            dynamicBean.setTotal_zan(totalSupport);
                        } else {
                            toastUtil.showToast(praiseStatusNetBean.getMessage());
                        }
                        refreshView(0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        refreshView(0);
                        super.onError(e);
                    }
                }));
    }

    public interface NetWorkResLisrtener {
        void onNetWorkResLisrtener();

        void onHomePageDataListener(HomePageInfoNetBean.ResultBean result);
    }

    private NetWorkResLisrtener netWorkResLisrtener;

    public void setNetWorkResLisrtener(NetWorkResLisrtener netWorkResLisrtener) {
        this.netWorkResLisrtener = netWorkResLisrtener;
    }

}
