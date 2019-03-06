package com.example.administrator.sportsfitness.ui.controller;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.MyTripNetBean;
import com.example.administrator.sportsfitness.model.bean.StudentFormNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;

import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.WebConcentratedActivity;
import com.example.administrator.sportsfitness.ui.adapter.MyTripAdapter;
import com.example.administrator.sportsfitness.ui.adapter.StudentFormAdapter;
import com.example.administrator.sportsfitness.widget.AlbumBuilder;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.example.administrator.sportsfitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerGeneralForm extends ControllerClassObserver implements MyTripAdapter.OnTripClickListener, StudentFormAdapter.OnStudentClickListener {

    RecyclerView recycler_view;

    private int flags;
    private String relatedId;
    private AlbumBuilder albumBuilder;
    private MyTripAdapter myTripAdapter;
    private int pageNumber = 1;
    private int newDataSize;
    private ArrayList<Object> list = new ArrayList<>();
    private int dataType = -1;
    private StudentFormAdapter studentFormAdapter;


    public ControllerGeneralForm(RecyclerView recycler_view, int flags, String relatedId) {
        this.recycler_view = recycler_view;
        this.flags = flags;
        this.relatedId = relatedId;
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
        switch (flags) {
            case EventCode.MY_TRIP:
                NetMyTrip(pageNumber);
                break;
            case EventCode.STUDENT_FORM:
                dataType = 2;
                NetStudentForm(pageNumber);
                break;
            case EventCode.COACH_STUDENT_FORM:
                dataType = 3;
                NetStudentForm(pageNumber);
                break;
        }
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        switch (flags) {
            case EventCode.MY_TRIP:
                myTripAdapter = new MyTripAdapter(context, list);
                recycler_view.setAdapter(myTripAdapter);
                myTripAdapter.setOnTripClickListener(this);
                break;
            case EventCode.STUDENT_FORM:
            case EventCode.COACH_STUDENT_FORM:
                studentFormAdapter = new StudentFormAdapter(context, list);
                recycler_view.setAdapter(studentFormAdapter);
                studentFormAdapter.setOnStudentClickListener(this);
                break;
        }
    }

    private void refreshView() {
        switch (flags) {
            case EventCode.MY_TRIP:
                if (pageNumber != 1) {
                    myTripAdapter.notifyItemRangeInserted(list.size() - newDataSize, newDataSize);
                } else {
                    myTripAdapter.notifyDataSetChanged();
                }
                break;
            case EventCode.STUDENT_FORM:
            case EventCode.COACH_STUDENT_FORM:
                if (pageNumber != 1) {
                    studentFormAdapter.notifyItemRangeInserted(list.size() - newDataSize, newDataSize);
                } else {
                    studentFormAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    //行程事件
    @Override
    public void OnTripClickListener(int position) {
        MyTripNetBean.ResultBean.NeeddoBean needdoBean=(MyTripNetBean.ResultBean.NeeddoBean) list.get(position);
        Intent helpCenterIntent = new Intent(context, WebConcentratedActivity.class);
        String parameter = new StringBuffer().append("do=order&").append("userid=").append(DataClass.USERID).append("&orderheadid=").append(needdoBean.getOrderheadid()).toString();
        helpCenterIntent.putExtra("link", parameter);
        helpCenterIntent.putExtra("titleName", context.getString(R.string.details));
        context.startActivity(helpCenterIntent);
    }

    @Override
    public void onStudentClickListener(int position) {
        StudentFormNetBean.ResultBean.StudentBean studentBean = (StudentFormNetBean.ResultBean.StudentBean) list.get(position);
        Intent Intent = new Intent(context, TheDetailsInformationActivity.class);
        Intent.putExtra("userId", studentBean.getUserid());
        Intent.putExtra("userName", studentBean.getSecondname());
        context.startActivity(Intent);
    }


    /**
     * 我的行程
     *
     * @param pageNumber
     */
    public void NetMyTrip(final int pageNumber) {
        this.pageNumber = pageNumber;
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.EVENT_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.MyTrip(map)
                .compose(RxUtil.<MyTripNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<MyTripNetBean>(toastUtil) {
                    @Override
                    public void onNext(MyTripNetBean myTripNetBean) {
                        if (myTripNetBean.getStatus() == 1) {
                            MyTripNetBean.ResultBean result = myTripNetBean.getResult();
                            List<MyTripNetBean.ResultBean.NeeddoBean> needdo = result.getNeeddo();
                            newDataSize = needdo.size();
                            if (pageNumber == 1)
                                list.clear();
                            list.addAll(needdo);
                            refreshView();
                            if (onGeneralFormRefreshListener != null)
                                onGeneralFormRefreshListener.onGeneralFormRefreshListener(result);
                        } else {
                            toastUtil.showToast(myTripNetBean.getMessage());
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
     * 学员列表
     *
     * @param pageNumber
     */
    public void NetStudentForm(final int pageNumber) {
        this.pageNumber = pageNumber;
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.STUDENT_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", dataType);
        linkedHashMap.put("refid", relatedId);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.StudentForm(map)
                .compose(RxUtil.<StudentFormNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<StudentFormNetBean>(toastUtil) {
                    @Override
                    public void onNext(StudentFormNetBean studentFormNetBean) {
                        if (studentFormNetBean.getStatus() == 1) {
                            StudentFormNetBean.ResultBean result = studentFormNetBean.getResult();
                            List<StudentFormNetBean.ResultBean.StudentBean> student = result.getStudent();
                            newDataSize = student.size();
                            if (pageNumber == 1)
                                list.clear();
                            list.addAll(student);
                            refreshView();
                            if (onGeneralFormRefreshListener != null)
                                onGeneralFormRefreshListener.onGeneralFormRefreshListener(result);
                        } else {
                            toastUtil.showToast(studentFormNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OnGeneralFormRefreshListener {
        void onGeneralFormRefreshListener(Object object);
    }

    private OnGeneralFormRefreshListener onGeneralFormRefreshListener;

    public void setOnGeneralFormRefreshListener(OnGeneralFormRefreshListener onGeneralFormRefreshListener) {
        this.onGeneralFormRefreshListener = onGeneralFormRefreshListener;
    }

}
