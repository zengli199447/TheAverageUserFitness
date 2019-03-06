package com.example.administrator.sportsfitness.ui.controller;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.OrderFormNetBean;
import com.example.administrator.sportsfitness.model.bean.PayObject;
import com.example.administrator.sportsfitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxBus;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.activity.component.WebConcentratedActivity;
import com.example.administrator.sportsfitness.ui.adapter.OrderFormAdapter;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsfitness.ui.view.CustomPayPopupWindow;
import com.example.administrator.sportsfitness.utils.LogUtil;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.example.administrator.sportsfitness.widget.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/3.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerOrderForm extends ControllerClassObserver implements SwipeRefreshLayout.OnRefreshListener, OrderFormAdapter.OnOrderFormClickListener,
        CustomPayPopupWindow.OnItemSelectClickListener, PopupWindow.OnDismissListener {

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;
    String typeId;
    private OrderFormAdapter orderFormAdapter;
    List<OrderFormNetBean.ResultBean.OrderBean> list = new ArrayList<>();
    int pageNumber = 1;
    int newDataSize = 0;
    private ShowDialog instance;
    private OrderFormNetBean.ResultBean.OrderBean orderBean;
    int currentIndex = 0;
    private CustomPayPopupWindow customPayPopupWindow;

    public ControllerOrderForm(SwipeRefreshLayout swipe_layout, TextView empty_layout, RecyclerView recycler_view, String typeId) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
        this.typeId = typeId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.OR_THE_DELET_ORDER:
                NetCancleOrder(orderBean.getOrderheadid());
                break;
            case EventCode.ORDER_PAY:
                instance.showHelpfulHintsDialog(context, context.getString(R.string.order_pay_successful), EventCode.ORDER_PAY_SUCCESSFUL);
                break;
            case EventCode.ORDER_PAY_SUCCESSFUL:
                pageNumber = 1;
                NetOrder();
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
        instance = ShowDialog.getInstance();
        customPayPopupWindow = new CustomPayPopupWindow(context);
        if (typeId.equals(context.getString(R.string.all)))
            typeId = "";
        initAdapter();
        initListener();
        NetOrder();

    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        orderFormAdapter = new OrderFormAdapter(context, list);
        recycler_view.setAdapter(orderFormAdapter);
    }

    private void initListener() {
        recycler_view.addOnScrollListener(scrollListener);
        swipe_layout.setOnRefreshListener(this);
        orderFormAdapter.setOnOrderFormClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
        customPayPopupWindow.setOnSelectItemClickListener(this);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (orderFormAdapter != null) {
                orderFormAdapter.setLoadState(orderFormAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow || list.size() == DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    NetOrder();
                } else {
                    // 显示加载到底的提示
                    orderFormAdapter.setLoadState(orderFormAdapter.LOADING_END);
                }
                if (newDataSize == 0 || newDataSize < DataClass.DefaultInformationFlow) {
                    orderFormAdapter.setLoadState(orderFormAdapter.LOADING_END);
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
        NetOrder();
    }

    private void refreshView() {
        if (pageNumber != 1) {
            orderFormAdapter.notifyItemRangeInserted(list.size() - newDataSize, newDataSize);
        } else {
            orderFormAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onOrderFormClickListener(View view, int position) {
        try {
            orderBean = list.get(position);
            this.currentIndex = position;
            switch (view.getId()) {
                case -1:
                    Intent orderIntent = new Intent(context, WebConcentratedActivity.class);
                    String parameter = new StringBuffer().append("do=order&").append("userid=").append(DataClass.USERID).append("&orderheadid=").append(orderBean.getOrderheadid()).toString();
                    orderIntent.putExtra("link", parameter);
                    orderIntent.putExtra("titleName", context.getString(R.string.details));
                    context.startActivity(orderIntent);
                    break;
                case R.id.controller_life:
                    TextView life = (TextView) view;
                    if (life.getText().toString().equals(context.getString(R.string.cancle_order))) {
                        instance.showConfirmOrNoDialog(context, context.getString(R.string.or_the_cancle_order), EventCode.ONSTART, EventCode.OR_THE_DELET_ORDER, EventCode.ONSTART);
                    } else if (life.getText().toString().equals(context.getString(R.string.refunding))) {

                    }
                    break;
                case R.id.controller_right:
                    TextView right = (TextView) view;
                    if (right.getText().toString().equals(context.getString(R.string.complete_order))) {

                    } else if (right.getText().toString().equals(context.getString(R.string.take_payment))) {
                        customPayPopupWindow.refreshPageView(Double.valueOf(orderBean.getProducttotalmoney()), Double.valueOf(DataClass.MONEY));
                        customPayPopupWindow.showAtLocation(swipe_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                        SystemUtil.windowToDark(context);
                    } else if (right.getText().toString().equals(context.getString(R.string.evaluation)) || right.getText().toString().equals(context.getString(R.string.additional_evaluation))) {
                        Intent webIntent = new Intent(context, WebConcentratedActivity.class);
                        webIntent.putExtra("link", new StringBuffer().append("do=pinjia&userid=").append(DataClass.USERID).append("&orderheadid=").append(orderBean.getOrderheadid()).toString());
                        webIntent.putExtra("titleName", context.getString(R.string.evaluation));
                        context.startActivity(webIntent);
                    }
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
    public void setOnItemClick(int index) {
        PayObject payObject = new PayObject(index, EventCode.ORDER_PAY, orderBean.getOrdercode());
        RxBus.getDefault().post(new CommonEvent(EventCode.PAY_ACTION, payObject));
    }

    /**
     * 订单
     */
    private void NetOrder() {
        swipe_layout.setRefreshing(true);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("state", typeId);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.OrderForm(map)
                .compose(RxUtil.<OrderFormNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<OrderFormNetBean>(toastUtil) {
                    @Override
                    public void onNext(OrderFormNetBean orderFormNetBean) {
                        if (orderFormNetBean.getStatus() == 1) {
                            OrderFormNetBean.ResultBean result = orderFormNetBean.getResult();
                            List<OrderFormNetBean.ResultBean.OrderBean> order = result.getOrder();
                            newDataSize = order.size();
                            if (pageNumber == 1) {
                                list.clear();
                                if (order.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            list.addAll(order);
                            refreshView();
                        } else {
                            toastUtil.showToast(orderFormNetBean.getMessage());
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
     * 取消订单
     *
     * @param Id
     */
    public void NetCancleOrder(String Id) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_CANCEL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("orderheadid", Id);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            orderBean.setState(context.getString(R.string.already_cancle));
                            orderBean.setState_pay(context.getString(R.string.refunding));
                            orderFormAdapter.notifyItemChanged(currentIndex);
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.cancle_successful), EventCode.ONSTART);
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


}
