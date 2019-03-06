package com.example.administrator.sportsfitness.ui.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.CommentsNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.adapter.ControllerCommentsAdapter;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.AlbumBuilder;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.example.administrator.sportsfitness.widget.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/17.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCommentsForm extends ControllerClassObserver implements SwipeRefreshLayout.OnRefreshListener, ControllerCommentsAdapter.CommentsParentClickListener {

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;
    private ArrayList<String> photoList = new ArrayList<>();
    List<CommentsNetBean.ResultBean.CommentBean> list = new ArrayList<>();
    private int pageNumber = 1;
    private int newDataSize;
    int dataType;
    String aboutId;
    private ControllerCommentsAdapter controllerCommentsAdapter;
    private AlbumBuilder albumBuilder;

    public ControllerCommentsForm(SwipeRefreshLayout swipe_layout, TextView empty_layout, RecyclerView recycler_view, int dataType, String aboutId) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
        this.dataType = dataType;
        this.aboutId = aboutId;
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
        albumBuilder = new AlbumBuilder(context);
        initAdapter();
        initListener();
        NetCommentsForm();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        controllerCommentsAdapter = new ControllerCommentsAdapter(context, list, false);
        recycler_view.setAdapter(controllerCommentsAdapter);

    }

    private void initListener() {
        controllerCommentsAdapter.setCommentsParentClickListener(this);
        recycler_view.addOnScrollListener(scrollListener);
        swipe_layout.setOnRefreshListener(this);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (controllerCommentsAdapter != null) {
                controllerCommentsAdapter.setLoadState(controllerCommentsAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow || list.size() == DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    NetCommentsForm();
                } else {
                    // 显示加载到底的提示
                    controllerCommentsAdapter.setLoadState(controllerCommentsAdapter.LOADING_END);
                }
                if (newDataSize == 0 || newDataSize < DataClass.DefaultInformationFlow) {
                    controllerCommentsAdapter.setLoadState(controllerCommentsAdapter.LOADING_END);
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
        NetCommentsForm();
    }

    private void refreshView() {
        if (pageNumber != 1) {
            controllerCommentsAdapter.notifyItemRangeInserted(list.size() - newDataSize, newDataSize);
        } else {
            controllerCommentsAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onCommentsChildClickListener(int position, int parentIndex) {
        CommentsNetBean.ResultBean.CommentBean commentBean = list.get(parentIndex);
        photoList.clear();
        List<CommentsNetBean.ResultBean.CommentBean.ImgBean> imgBeans = commentBean.getImg();
        for (CommentsNetBean.ResultBean.CommentBean.ImgBean imgBean : imgBeans) {
            photoList.add(SystemUtil.JudgeUrl(imgBean.getImgpath()));
        }
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    @Override
    public void onCommentsCheckClickListener(int position, int id) {

    }


    //评论列表
    private void NetCommentsForm() {
        swipe_layout.setRefreshing(true);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.COMMENT_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", dataType);
        linkedHashMap.put("refid", aboutId);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.CommentsForm(map)
                .compose(RxUtil.<CommentsNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommentsNetBean>(toastUtil) {
                    @Override
                    public void onNext(CommentsNetBean commentsNetBean) {
                        if (commentsNetBean.getStatus() == 1) {
                            CommentsNetBean.ResultBean result = commentsNetBean.getResult();
                            List<CommentsNetBean.ResultBean.CommentBean> comment = result.getComment();
                            newDataSize = comment.size();
                            if (pageNumber == 1) {
                                list.clear();
                                if (comment.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            list.addAll(comment);
                            refreshView();
                        } else {
                            toastUtil.showToast(commentsNetBean.getMessage());
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

