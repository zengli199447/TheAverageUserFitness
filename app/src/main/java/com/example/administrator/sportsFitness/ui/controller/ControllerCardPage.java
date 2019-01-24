package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.PopupWindow;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.CardFormNetBean;
import com.example.administrator.sportsFitness.model.bean.PayObject;
import com.example.administrator.sportsFitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.adapter.CardPageAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomPayPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCardPage extends ControllerClassObserver implements CardPageAdapter.OnClickListener, CustomPayPopupWindow.OnItemSelectClickListener, PopupWindow.OnDismissListener {

    RecyclerView recycler_view;
    private CardPageAdapter cardPageAdapter;
    private ShowDialog instance;
    List<CardFormNetBean.ResultBean.CardBean> list = new ArrayList<>();
    private CustomPayPopupWindow customPayPopupWindow;
    private String orderCode;

    public ControllerCardPage(RecyclerView recycler_view) {
        this.recycler_view = recycler_view;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.HANDLE_CARD:
                instance.showHelpfulHintsDialog(context, context.getString(R.string.handle_card_succesful), EventCode.ONSTART);
                cardFormNetWork();
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
        initAdapter();
        initListenr();
        cardFormNetWork();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        cardPageAdapter = new CardPageAdapter(context, list);
        recycler_view.setAdapter(cardPageAdapter);
    }

    private void initListenr() {
        customPayPopupWindow.setOnDismissListener(this);
        customPayPopupWindow.setOnSelectItemClickListener(this);
        cardPageAdapter.setOnClickListener(this);
    }

    private void refreshView() {
        cardPageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListener(int position) {
        CardFormNetBean.ResultBean.CardBean cardBean = list.get(position);
        NetHandleCard(cardBean);
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(context);
    }

    @Override
    public void setOnItemClick(int index) {
        PayObject payObject = new PayObject(index, EventCode.HANDLE_CARD, orderCode);
        RxBus.getDefault().post(new CommonEvent(EventCode.PAY_ACTION, payObject));
    }

    /**
     * 健身卡列表
     */
    public void cardFormNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.CARD_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.CardForm(map)
                .compose(RxUtil.<CardFormNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CardFormNetBean>(toastUtil) {
                    @Override
                    public void onNext(CardFormNetBean cardFormNetBean) {
                        if (cardFormNetBean.getStatus() == 1) {
                            CardFormNetBean.ResultBean result = cardFormNetBean.getResult();
                            list.clear();
                            list.addAll(result.getCard());
                            refreshView();
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
     * 办理健身卡
     */
    public void NetHandleCard(final CardFormNetBean.ResultBean.CardBean cardBean) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.CARD_BUY_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("cardsettingid", cardBean.getCardsettingid());
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.TopUp(map)
                .compose(RxUtil.<TopUpNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TopUpNetBean>(toastUtil) {
                    @Override
                    public void onNext(TopUpNetBean topUpNetBean) {
                        if (topUpNetBean.getStatus() == 1) {
                            orderCode = topUpNetBean.getResult().getOrdercode();
                            customPayPopupWindow.refreshPageView(Double.valueOf(cardBean.getPrice()), Double.valueOf(DataClass.MONEY));
                            customPayPopupWindow.showAtLocation(recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                            SystemUtil.windowToDark(context);
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
