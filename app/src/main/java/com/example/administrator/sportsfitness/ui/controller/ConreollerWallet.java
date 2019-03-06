package com.example.administrator.sportsfitness.ui.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsfitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsfitness.model.bean.VipTopUpSelectNetBean;
import com.example.administrator.sportsfitness.model.bean.WalletLogNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxBus;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.adapter.BillingAdapter;
import com.example.administrator.sportsfitness.ui.adapter.TopUpSelectAdapter;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.utils.LogUtil;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.example.administrator.sportsfitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ConreollerWallet extends ControllerClassObserver implements TopUpSelectAdapter.OnSelectClickListener {

    RecyclerView top_up_recycler_view;
    RecyclerView billing_recycler_view;
    private TopUpSelectAdapter topUpSelectAdapter;
    private List<WalletLogNetBean.ResultBean.MoneyinConfigBean> vipTopUpSelectNetBeanList = new ArrayList<>();
    private BillingAdapter billingAdapter;
    List<WalletLogNetBean.ResultBean.MoneydetaillistBean> list = new ArrayList<>();
    private int selectIndex = 0;
    private ShowDialog instance;
    private int pageNumber;
    private int newDataSize;

    public ConreollerWallet(RecyclerView top_up_recycler_view, RecyclerView billing_recycler_view) {
        this.top_up_recycler_view = top_up_recycler_view;
        this.billing_recycler_view = billing_recycler_view;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.WALLET_TOP_UP_PAY:
                NetWallet(1);
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
        initAdapter();
        NetWallet(1);
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        top_up_recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, 2));
        topUpSelectAdapter = new TopUpSelectAdapter(context, vipTopUpSelectNetBeanList);
        top_up_recycler_view.setAdapter(topUpSelectAdapter);
        topUpSelectAdapter.setOnSelectClickListener(this);

        billing_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        billingAdapter = new BillingAdapter(context, list);
        billing_recycler_view.setAdapter(billingAdapter);

    }

    private void refreshView(int type) {
        switch (type) {
            case 0:
                topUpSelectAdapter.notifyDataSetChanged();
                break;
            case 1:
                if (pageNumber != 1) {
                    billingAdapter.notifyItemRangeInserted(list.size() - newDataSize, newDataSize);
                } else {
                    billingAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void OnSelectClickListener(int position) {
        this.selectIndex = position;
        for (int i = 0; i < vipTopUpSelectNetBeanList.size(); i++) {
            if (i == position) {
                vipTopUpSelectNetBeanList.get(i).setSelectStatus(true);
            } else {
                vipTopUpSelectNetBeanList.get(i).setSelectStatus(false);
            }
        }
        topUpSelectAdapter.notifyDataSetChanged();
    }

    public String getPrice() {
        return vipTopUpSelectNetBeanList.get(selectIndex).getMoney_in();
    }

    //钱包
    public void NetWallet(final int pageNumber) {
        this.pageNumber = pageNumber;
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MONEY_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.WalletLog(map)
                .compose(RxUtil.<WalletLogNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<WalletLogNetBean>(toastUtil) {
                    @Override
                    public void onNext(WalletLogNetBean walletLogNetBean) {
                        if (walletLogNetBean.getStatus() == 1) {
                            WalletLogNetBean.ResultBean result = walletLogNetBean.getResult();
                            List<WalletLogNetBean.ResultBean.MoneydetaillistBean> moneydetaillist = result.getMoneydetaillist();
                            List<WalletLogNetBean.ResultBean.MoneyinConfigBean> moneyin_config = result.getMoneyin_config();
                            newDataSize = moneydetaillist.size();
                            if (vipTopUpSelectNetBeanList.size() == 0) {
                                boolean status = true;
                                for (int i = 0; i < moneyin_config.size(); i++) {
                                    if (i != 0)
                                        status = false;
                                    WalletLogNetBean.ResultBean.MoneyinConfigBean moneyinConfigBean = moneyin_config.get(i);
                                    WalletLogNetBean.ResultBean.MoneyinConfigBean moneyinConfig = new WalletLogNetBean.ResultBean.MoneyinConfigBean(moneyinConfigBean.getCreatedate(),
                                            moneyinConfigBean.getMoney_gift(),
                                            moneyinConfigBean.getMoney_in(),
                                            moneyinConfigBean.getMoneyinconfigid(),
                                            status);
                                    vipTopUpSelectNetBeanList.add(moneyinConfig);
                                }
                                refreshView(0);
                            }

                            if (pageNumber == 1)
                                list.clear();
                            list.addAll(moneydetaillist);
                            refreshView(1);
                            if (onWalletLogListener != null)
                                onWalletLogListener.OnWalletLogListener(result);
                        } else {
                            toastUtil.showToast(walletLogNetBean.getMessage());
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
     * 充值
     */
    public void TopUpNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MONEY_IN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("moneyinconfigid", vipTopUpSelectNetBeanList.get(selectIndex).getMoneyinconfigid());
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.TopUp(map)
                .compose(RxUtil.<TopUpNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TopUpNetBean>(toastUtil) {
                    @Override
                    public void onNext(TopUpNetBean topUpNetBean) {
                        if (topUpNetBean.getStatus() == 1) {
                            if (onWalletLogListener != null)
                                onWalletLogListener.OnTopUpListener(topUpNetBean.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OnWalletLogListener {
        void OnWalletLogListener(WalletLogNetBean.ResultBean result);

        void OnTopUpListener(TopUpNetBean.ResultBean result);
    }

    private OnWalletLogListener onWalletLogListener;

    public void setOnWalletLogListener(OnWalletLogListener onWalletLogListener) {
        this.onWalletLogListener = onWalletLogListener;
    }


}
