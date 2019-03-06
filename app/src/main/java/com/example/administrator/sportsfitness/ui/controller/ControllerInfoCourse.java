package com.example.administrator.sportsfitness.ui.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.CommentsNetBean;
import com.example.administrator.sportsfitness.model.bean.InfoCourseNetBean;
import com.example.administrator.sportsfitness.model.bean.PraiseStatusNetBean;
import com.example.administrator.sportsfitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.adapter.ControllerCommentsAdapter;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.utils.LogUtil;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.AlbumBuilder;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.example.administrator.sportsfitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerInfoCourse extends ControllerClassObserver implements ControllerCommentsAdapter.CommentsParentClickListener {

    RecyclerView recyclerView;
    String coursesId;
    private CommentsNetBean.ResultBean.CommentBean commentInfoBean;
    List<CommentsNetBean.ResultBean.CommentBean> commentsList = new ArrayList<>();
    private ArrayList<String> photoList = new ArrayList<>();
    private AlbumBuilder albumBuilder;
    private ShowDialog instance;
    private ControllerCommentsAdapter controllerCommentsAdapter;

    public ControllerInfoCourse(RecyclerView recyclerView, String coursesId) {
        this.recyclerView = recyclerView;
        this.coursesId = coursesId;
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
        instance = ShowDialog.getInstance();
        initAdapter();
        InfoCourseNet();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        controllerCommentsAdapter = new ControllerCommentsAdapter(context, commentsList, true, 1);
        recyclerView.setAdapter(controllerCommentsAdapter);
        controllerCommentsAdapter.setCommentsParentClickListener(this);

    }

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
            case R.id.sign_up:
                toastUtil.showToast("position : " + position);
                break;
        }
    }

    private void refreshView() {
        controllerCommentsAdapter.notifyDataSetChanged();
    }


    //课程详情
    private void InfoCourseNet() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.KECHENG_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("coursesid", coursesId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.InfoCourse(map)
                .compose(RxUtil.<InfoCourseNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<InfoCourseNetBean>(toastUtil) {
                    @Override
                    public void onNext(InfoCourseNetBean infoCourseNetBean) {
                        if (infoCourseNetBean.getStatus() == 1) {
                            InfoCourseNetBean.ResultBean result = infoCourseNetBean.getResult();
                            InfoCourseNetBean.ResultBean.DetailBean detail = result.getDetail();
                            List<InfoCourseNetBean.ResultBean.DetailBean.StudentBean> student = detail.getStudent();
                            List<InfoCourseNetBean.ResultBean.DetailBean.CommentBean> comment = detail.getComment();

                            List<CommentsNetBean.ResultBean.CommentBean.ImgBean> commentsImgBeanList = new ArrayList<>();

                            if (comment.size() > 0) {
                                InfoCourseNetBean.ResultBean.DetailBean.CommentBean commentBean = comment.get(0);
                                for (InfoCourseNetBean.ResultBean.DetailBean.CommentBean.ImgBean imgBean : commentBean.getImg()) {
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
                            refreshView();
                            if (onInfoCourseListener != null)
                                onInfoCourseListener.onInfoCourseListener(detail);
                        } else {
                            toastUtil.showToast(infoCourseNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    /**
     * 收藏
     *
     * @param datatype 1.场馆 2.课程 3.教练
     * @param refid    1.场馆id 2.课程id 3.教练id
     */
    public void NetCollection() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.COLLECT_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", 2);
        linkedHashMap.put("refid", coursesId);
        map.put("version", "v1");
        map.put("vars", new Gson().toJson(linkedHashMap));
        addSubscribe(dataManager.Praise(map)
                .compose(RxUtil.<PraiseStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PraiseStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(PraiseStatusNetBean praiseStatusNetBean) {
                        if (praiseStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, praiseStatusNetBean.getMessage(), EventCode.ONSTART);
                            if (onInfoCourseListener != null)
                                onInfoCourseListener.onCollectionSuccessfulListener(praiseStatusNetBean.getIfcollect_cleck() == 1 ? true : false);
                        } else {
                            toastUtil.showToast(praiseStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));

    }

    public interface OnInfoCourseListener {
        void onInfoCourseListener(InfoCourseNetBean.ResultBean.DetailBean detail);

        void onCollectionSuccessfulListener(boolean status);
    }

    private OnInfoCourseListener onInfoCourseListener;

    public void setOnInfoCourseListener(OnInfoCourseListener onInfoCourseListener) {
        this.onInfoCourseListener = onInfoCourseListener;
    }

}
