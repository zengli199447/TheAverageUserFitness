package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.DynamicDetailsNetBean;
import com.example.administrator.sportsFitness.model.bean.PraiseStatusNetBean;
import com.example.administrator.sportsFitness.model.bean.ReplySendNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.adapter.DynamicCommentsAdapter;
import com.example.administrator.sportsFitness.ui.adapter.RecyclerChildAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
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
public class ControllerDynamicDetails extends ControllerClassObserver implements RecyclerChildAdapter.ChildClickListener, DynamicCommentsAdapter.ReplyClickListener {

    RecyclerView recycler_view;
    RecyclerView recycler_view_comments;
    EditText input_reply;
    TextView send;
    private ArrayList<String> photoList = new ArrayList<>();
    private AlbumBuilder albumBuilder;
    private ArrayList<DynamicDetailsNetBean.ResultBean.ReplyBean> replyList = new ArrayList<>();

    private String userId;
    private String dynamicId;
    private DynamicCommentsAdapter dynamicCommentsAdapter;
    private String relpyId;
    private String relpyName;
    private String relpyBasisId;
    private String relpyBasisName;
    private int newDataSize;
    private int pageNumber = 1;
    private DynamicDetailsNetBean.ResultBean.DetailBean detail;
    private int totalSupport;
    private ShowDialog instance;

    public ControllerDynamicDetails(RecyclerView recycler_view, RecyclerView recycler_view_comments, EditText input_reply, TextView send, String userId, String dynamicId) {
        this.recycler_view = recycler_view;
        this.recycler_view_comments = recycler_view_comments;
        this.input_reply = input_reply;
        this.send = send;
        this.userId = userId;
        this.dynamicId = dynamicId;
        this.relpyId = userId;
        this.relpyBasisId = userId;

    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.REPORT_INPUT:
                NetCodeViolation(commonevent.getTemp_str());
                break;
            case EventCode.DELET_DYNAMIC:
                NetDeletDynamic();
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
        initAdapter();
        initListener();
        NetDynamicDetails(1);

    }

    private void initAdapter() {
        dynamicCommentsAdapter = new DynamicCommentsAdapter(context, replyList);
        recycler_view_comments.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        recycler_view_comments.setAdapter(dynamicCommentsAdapter);
    }

    private void initListener() {
        dynamicCommentsAdapter.setReplyClickListener(this);
        input_reply.addTextChangedListener(TextWatcherListener);
    }

    //类图模式(单张、两张、更多)
    private void initRecyclerView() {
        int spanCount = 0;
        if (photoList.size() == 1) {
            spanCount = 1;
        } else if (photoList.size() == 2) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
        recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, spanCount));
        RecyclerChildAdapter recyclerChildAdapter = new RecyclerChildAdapter(context, photoList, false, 0);
        recycler_view.setAdapter(recyclerChildAdapter);
        recyclerChildAdapter.notifyDataSetChanged();
        recyclerChildAdapter.setChildClickListener(this);
    }

    private void refreshView() {
        if (pageNumber != 1) {
            dynamicCommentsAdapter.notifyItemRangeInserted(replyList.size() - newDataSize, newDataSize);
        } else {
            dynamicCommentsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private TextWatcher TextWatcherListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty()) {
                send.setEnabled(false);
                send.setTextColor(context.getResources().getColor(R.color.gray_light));
                relpyId = relpyBasisName;
                relpyName = relpyBasisName;
            } else {
                send.setEnabled(true);
                send.setTextColor(context.getResources().getColor(R.color.blue_bar));
            }
            if (!s.toString().isEmpty() && relpyId != null && !relpyId.equals(relpyBasisId)) {
                if (!s.toString().contains(new StringBuffer().append("@").append(relpyName).append(" : ").toString())) {
                    input_reply.setText("");
                }
            }
            LogUtil.e(TAG, "relpyId : " + relpyId);
            LogUtil.e(TAG, "relpyName : " + relpyName);
        }
    };

    @Override
    public void onChildClickListener(int position, int parentIndex) {
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    //评论对象分选
    @Override
    public void onReplyClickListener(int position, int type) {
        DynamicDetailsNetBean.ResultBean.ReplyBean replyBean = replyList.get(position);
        switch (type) {
            case 0:
                relpyId = replyBean.getUserid();
                relpyName = replyBean.getUsername_from();
                break;
            case 1:
                relpyId = replyBean.getUserid_to();
                relpyName = replyBean.getUsername_to();
                break;
        }
        if (!relpyId.equals(relpyBasisId)) {
            refreshHint(relpyName);
        }
    }

    //刷新@项
    private void refreshHint(String relpyName) {
        String content = new StringBuffer().append("@").append(relpyName).append(" : ").toString();
        input_reply.setText(content);
        input_reply.setSelection(content.length());
    }

    private String JudgeComments(String content) {
        String text = "";
        text = content;
        if (!relpyId.equals(relpyBasisId)) {
            String[] split = content.split(":");
            text = split[1].toString().trim();
            LogUtil.e(TAG, "sendCommentsNetWork -- no");
        } else {
            relpyId = "";
        }
        return text;
    }

    /**
     * 动态详情
     */
    public void NetDynamicDetails(final int pageNumber) {
        this.pageNumber = pageNumber;
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_DETAIL_GET);
        linkedHashMap.put("userid", userId);
        linkedHashMap.put("newsid", dynamicId);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.DynamicDetails(map)
                .compose(RxUtil.<DynamicDetailsNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<DynamicDetailsNetBean>(toastUtil) {
                    @Override
                    public void onNext(DynamicDetailsNetBean dynamicDetailsNetBean) {
                        if (dynamicDetailsNetBean.getStatus() == 1) {
                            DynamicDetailsNetBean.ResultBean result = dynamicDetailsNetBean.getResult();
                            detail = result.getDetail();
                            relpyName = detail.getSecondname();
                            relpyBasisName = detail.getSecondname();
                            totalSupport = detail.getTotal_zan();
                            List<DynamicDetailsNetBean.ResultBean.DetailBean.ImgpathjsonBean> imgpathjson = detail.getImgpathjson();
                            if (photoList.size() == 0 && imgpathjson != null) {
                                for (DynamicDetailsNetBean.ResultBean.DetailBean.ImgpathjsonBean imgpathjsonBean : imgpathjson) {
                                    photoList.add(SystemUtil.JudgeUrl(imgpathjsonBean.getImgpath()));
                                }
                                initRecyclerView();
                            }
                            List<DynamicDetailsNetBean.ResultBean.ReplyBean> reply = result.getReply();
                            newDataSize = reply.size();
                            if (pageNumber == 1)
                                replyList.clear();
                            replyList.addAll(reply);
                            refreshView();
                            if (onDynamicDetailsListener != null)
                                onDynamicDetailsListener.onDynamicDetailsListener(result, pageNumber);
                        } else {
                            toastUtil.showToast(dynamicDetailsNetBean.getMessage());
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
     * 发送评论
     *
     * @param selectCommentsId
     * @param content
     */
    public void NetSendComments(String content) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.REPLY_ADD_SET);
        linkedHashMap.put("newsid", dynamicId);
        linkedHashMap.put("content", JudgeComments(content));
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("userid_to", relpyId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.ReplySend(map)
                .compose(RxUtil.<ReplySendNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<ReplySendNetBean>(toastUtil) {
                    @Override
                    public void onNext(ReplySendNetBean replySendNetBean) {
                        if (replySendNetBean.getStatus() == 1) {
                            ReplySendNetBean.ResultBean result = replySendNetBean.getResult();
                            DynamicDetailsNetBean.ResultBean.ReplyBean replyBean = new DynamicDetailsNetBean.ResultBean.ReplyBean(
                                    result.getReplyid(),
                                    result.getUserid(),
                                    result.getNewsid(),
                                    result.getContent(),
                                    result.getUserid_to(),
                                    result.getCreatedate(),
                                    result.getUsername_from(),
                                    result.getUsername_to());
                            replyList.add(replyBean);
                            dynamicCommentsAdapter.notifyItemInserted(replyList.size() - 1);
                            if (onDynamicDetailsListener != null)
                                onDynamicDetailsListener.onSendCommentsListener();
                        } else {
                            toastUtil.showToast(replySendNetBean.getMessage());
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
    public void NetOrSupport(final AppCompatCheckBox support_check) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_ZAN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", dynamicId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.Praise(map)
                .compose(RxUtil.<PraiseStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PraiseStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(PraiseStatusNetBean praiseStatusNetBean) {
                        if (praiseStatusNetBean.getStatus() == 1) {
                            if (praiseStatusNetBean.getIfzan_cleck() == 1) {
                                totalSupport = totalSupport + 1;
                                support_check.setChecked(true);
                            } else {
                                totalSupport = totalSupport - 1;
                                support_check.setChecked(false);
                            }
                            support_check.setText(new StringBuffer().append(" ").append(totalSupport).toString());
                        } else {
                            toastUtil.showToast(praiseStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        if (detail.getIfzan_cleck().equals("1")) {
                            support_check.setChecked(true);
                        } else {
                            support_check.setChecked(false);
                        }
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
        linkedHashMap.put("newsid", dynamicId);
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
        linkedHashMap.put("newsid", dynamicId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        instance.showHelpfulHintsDialog(context, upLoadStatusNetBean.getMessage(), EventCode.DELET_DYNAMIC_SUCCESSFUL);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OnDynamicDetailsListener {
        void onDynamicDetailsListener(DynamicDetailsNetBean.ResultBean result, int pageNumber);

        void onSendCommentsListener();
    }

    private OnDynamicDetailsListener onDynamicDetailsListener;

    public void setOnDynamicDetailsListener(OnDynamicDetailsListener onDynamicDetailsListener) {
        this.onDynamicDetailsListener = onDynamicDetailsListener;
    }

}
