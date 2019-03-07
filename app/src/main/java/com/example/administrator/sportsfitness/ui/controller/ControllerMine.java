package com.example.administrator.sportsfitness.ui.controller;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.InfoAboutNetBean;
import com.example.administrator.sportsfitness.model.bean.UserInfoNetBean;
import com.example.administrator.sportsfitness.model.bean.WalletLogNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.activity.component.OrderFormActivity;
import com.example.administrator.sportsfitness.ui.activity.component.WebConcentratedActivity;
import com.example.administrator.sportsfitness.ui.adapter.MineToolsAdapter;
import com.example.administrator.sportsfitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.example.administrator.sportsfitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerMine extends ControllerClassObserver implements MineToolsAdapter.MineToolsClickListener {

    RecyclerView recycler_view;
    private MineToolsAdapter mineToolsAdapter;
    private ProgressDialog progressDialog;

    public ControllerMine(RecyclerView recycler_view) {
        this.recycler_view = recycler_view;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.MINE_INFO_REFRESH:
                NetPersonalContent();
                NetInfoAbout();
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
        progressDialog = ShowDialog.getInstance().showProgressStatus(context);
        progressDialog.show();
        initAdapter();
        NetPersonalContent();
        NetInfoAbout();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        mineToolsAdapter = new MineToolsAdapter(context);
        recycler_view.setAdapter(mineToolsAdapter);
        mineToolsAdapter.setMineToolsClickListener(this);
        mineToolsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMineToolsClickListener(int position) {
        switch (position) {
            case 0:
                context.startActivity(new Intent(context, OrderFormActivity.class));
                break;
            case 1:
                Intent helpCenterIntent = new Intent(context, WebConcentratedActivity.class);
                helpCenterIntent.putExtra("link", "do=kefu");
                helpCenterIntent.putExtra("titleName", context.getString(R.string.help_center));
                context.startActivity(helpCenterIntent);
                break;
        }
    }

    //获取用户信息
    public void NetPersonalContent() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UserInfo(map)
                .compose(RxUtil.<UserInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UserInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(UserInfoNetBean userInfoNetBean) {
                        if (userInfoNetBean.getStatus() == 1) {
                            UserInfoNetBean.ResultBean result = userInfoNetBean.getResult();
                            DataClass.USERID = result.getUserid();
                            DataClass.AGE = result.getAge();
                            DataClass.USERPHOTO = result.getPhoto();
                            DataClass.GENDER = result.getSex();
                            DataClass.UNAME = result.getSecondname();
                            DataClass.BRITHDAY = result.getBrithday();
                            DataClass.CARD_NUMBER = result.getCardno();
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            if (onPersonalContentNetWorkListener != null)
                                onPersonalContentNetWorkListener.onPersonalContentNetWorkListener(userInfoNetBean.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //用户相关数据获取
    private void NetInfoAbout() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_CENTER_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.InfoAbout(map)
                .compose(RxUtil.<InfoAboutNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<InfoAboutNetBean>(toastUtil) {
                    @Override
                    public void onNext(InfoAboutNetBean infoAboutNetBean) {
                        if (infoAboutNetBean.getStatus() == 1) {
                            if (onPersonalContentNetWorkListener != null)
                                onPersonalContentNetWorkListener.onNetInfoAboutListener(infoAboutNetBean.getResult());
                        } else {
                            toastUtil.showToast(infoAboutNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OnPersonalContentNetWorkListener {
        void onPersonalContentNetWorkListener(UserInfoNetBean.ResultBean result);

        void onNetInfoAboutListener(InfoAboutNetBean.ResultBean result);

    }

    private OnPersonalContentNetWorkListener onPersonalContentNetWorkListener;

    public void setOnPersonalContentNetWorkListener(OnPersonalContentNetWorkListener onPersonalContentNetWorkListener) {
        this.onPersonalContentNetWorkListener = onPersonalContentNetWorkListener;
    }

}
