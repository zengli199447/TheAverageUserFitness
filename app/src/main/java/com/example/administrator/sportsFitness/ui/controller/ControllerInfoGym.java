package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.CommentsNetBean;
import com.example.administrator.sportsFitness.model.bean.CourseFormNetBean;
import com.example.administrator.sportsFitness.model.bean.InfoGymNetBean;
import com.example.administrator.sportsFitness.model.bean.PraiseStatusNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.adapter.ControllerCommentsAdapter;
import com.example.administrator.sportsFitness.ui.adapter.FitnessCourseAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/26.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerInfoGym extends ControllerClassObserver implements FitnessCourseAdapter.onFitnessCourseClickListener, ControllerCommentsAdapter.CommentsParentClickListener {

    RecyclerView course_recycler_view;
    RecyclerView evaluation_recycler_view;
    private FitnessCourseAdapter fitnessCourseAdapter;
    private ControllerCommentsAdapter controllerCommentsAdapter;
    private AlbumBuilder albumBuilder;
    private ArrayList<String> photoList = new ArrayList<>();
    private List<Object> fitnessRelatedList = new ArrayList<>();
    List<CommentsNetBean.ResultBean.CommentBean> commentsList = new ArrayList<>();
    String gymId;
    private CommentsNetBean.ResultBean.CommentBean commentInfoBean;
    private ShowDialog instance;


    public ControllerInfoGym(RecyclerView course_recycler_view, RecyclerView evaluation_recycler_view, String gymId) {
        this.course_recycler_view = course_recycler_view;
        this.evaluation_recycler_view = evaluation_recycler_view;
        this.gymId = gymId;
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
        InfoGymNet();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        course_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        fitnessCourseAdapter = new FitnessCourseAdapter(context, fitnessRelatedList, true, 2);
        course_recycler_view.setAdapter(fitnessCourseAdapter);
        fitnessCourseAdapter.setOnFitnessCourseClickListener(this);

        evaluation_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        controllerCommentsAdapter = new ControllerCommentsAdapter(context, commentsList, true, 1);
        evaluation_recycler_view.setAdapter(controllerCommentsAdapter);
        controllerCommentsAdapter.setCommentsParentClickListener(this);

    }

    private void refreshView() {
        fitnessCourseAdapter.notifyDataSetChanged();
        controllerCommentsAdapter.notifyDataSetChanged();
    }

    //课程事件
    @Override
    public void onFitnessCourseClickListener(View view, int position) {
        CourseFormNetBean.ResultBean.CoursesBean coursesBean = (CourseFormNetBean.ResultBean.CoursesBean) fitnessRelatedList.get(position);
        switch (view.getId()) {
            case -1:
                toastUtil.showToast("position : " + position);
                break;
            case R.id.sign_up:
                toastUtil.showToast("position : " + position);
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
            case R.id.sign_up:
                toastUtil.showToast("position : " + position);
                break;
        }
    }

    //健身房详情
    private void InfoGymNet() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOP_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("shopid", gymId);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.InfoGym(map)
                .compose(RxUtil.<InfoGymNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<InfoGymNetBean>(toastUtil) {
                    @Override
                    public void onNext(InfoGymNetBean infoGymNetBean) {
                        if (infoGymNetBean.getStatus() == 1) {
                            InfoGymNetBean.ResultBean result = infoGymNetBean.getResult();
                            InfoGymNetBean.ResultBean.DetailBean detail = result.getDetail();

                            List<InfoGymNetBean.ResultBean.DetailBean.CoursesBean> coursesBeanList = detail.getCourses();
                            List<InfoGymNetBean.ResultBean.DetailBean.CommentBean> comment = detail.getComment();

                            if (coursesBeanList.size() > 0) {
                                for (int i = 0; i < 2; i++) {
                                    InfoGymNetBean.ResultBean.DetailBean.CoursesBean courses = coursesBeanList.get(0);
                                    CourseFormNetBean.ResultBean.CoursesBean coursesBean = new CourseFormNetBean.ResultBean.CoursesBean(
                                            courses.getCoursesid(),
                                            courses.getListimg(),
                                            courses.getCoursesname(),
                                            courses.getDate_start(),
                                            courses.getDate_end(),
                                            courses.getTime_start(),
                                            courses.getTime_end(),
                                            courses.getPrice(),
                                            courses.getShopname(),
                                            courses.getTotal(),
                                            courses.getYuyuetotal(),
                                            courses.getIf_jinzhang(),
                                            courses.getDistance(),
                                            courses.getState());
                                    fitnessRelatedList.add(coursesBean);
                                }

                            }

                            List<CommentsNetBean.ResultBean.CommentBean.ImgBean> commentsImgBeanList = new ArrayList<>();

                            if (comment.size() > 0) {
                                InfoGymNetBean.ResultBean.DetailBean.CommentBean commentBean = comment.get(0);
                                for (InfoGymNetBean.ResultBean.DetailBean.CommentBean.ImgBean imgBean : commentBean.getImg()) {
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
                            if (onInfoGymListener != null)
                                onInfoGymListener.onInfoGymListener(detail);
                        } else {
                            toastUtil.showToast(infoGymNetBean.getMessage());
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
        linkedHashMap.put("datatype", 1);
        linkedHashMap.put("refid", gymId);
        map.put("version", "v1");
        map.put("vars", new Gson().toJson(linkedHashMap));
        addSubscribe(dataManager.Praise(map)
                .compose(RxUtil.<PraiseStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PraiseStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(PraiseStatusNetBean praiseStatusNetBean) {
                        if (praiseStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, praiseStatusNetBean.getMessage(), EventCode.ONSTART);
                            if (onInfoGymListener != null)
                                onInfoGymListener.onCollectionSuccessfulListener(praiseStatusNetBean.getIfcollect_cleck() == 0 ? false : true);
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

    public interface OnInfoGymListener {
        void onInfoGymListener(InfoGymNetBean.ResultBean.DetailBean detail);

        void onCollectionSuccessfulListener(boolean status);
    }

    private OnInfoGymListener onInfoGymListener;

    public void setOnInfoGymListener(OnInfoGymListener onInfoGymListener) {
        this.onInfoGymListener = onInfoGymListener;
    }

}
