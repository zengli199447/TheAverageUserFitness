package com.example.administrator.sportsFitness.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseFragment;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.component.CardPageActivity;
import com.example.administrator.sportsFitness.ui.activity.component.FriendsCircleActivity;
import com.example.administrator.sportsFitness.ui.activity.component.GeneralFormActivity;
import com.example.administrator.sportsFitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsFitness.ui.activity.component.WalletActivity;
import com.example.administrator.sportsFitness.ui.controller.ControllerMine;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.user_photo)
    ImageView user_photo;
    @BindView(R.id.user_nice_name)
    TextView user_nice_name;
    @BindView(R.id.user_content)
    TextView user_content;

    @BindView(R.id.collection)
    TextView collection;
    @BindView(R.id.friends)
    TextView friends;
    @BindView(R.id.dynamic)
    TextView dynamic;
    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.card)
    TextView card;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private ControllerMine controllerMine;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initClass() {
        controllerMine = new ControllerMine(recycler_view);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerMine;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        refreshView();
        recycler_view.setFocusable(false);
    }

    @Override
    protected void initListener() {

    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.ranking_list, R.id.weekly, R.id.CV, R.id.sign_in, R.id.collection, R.id.friends,
            R.id.dynamic, R.id.balance, R.id.card, R.id.user_photo})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.ranking_list:
                break;
            case R.id.weekly:
                break;
            case R.id.CV:
                break;
            case R.id.sign_in:
                break;
            case R.id.collection:
                break;
            case R.id.friends:
                getActivity().startActivity(new Intent(getActivity(), FriendsCircleActivity.class));
                break;
            case R.id.dynamic:
                Intent generalFormIntent = new Intent(getActivity(), GeneralFormActivity.class);
                generalFormIntent.setFlags(EventCode.DYNAMIC);
                generalFormIntent.putExtra("relatedId", DataClass.USERID);
                generalFormIntent.putExtra("relatedName", DataClass.UNAME);
                getActivity().startActivity(generalFormIntent);
                break;
            case R.id.balance:
                getActivity().startActivity(new Intent(getActivity(), WalletActivity.class));
                break;
            case R.id.card:
                getActivity().startActivity(new Intent(getActivity(), CardPageActivity.class));
                break;
            case R.id.user_photo:
                Intent theDetailsInformationIntent = new Intent(getActivity(), TheDetailsInformationActivity.class);
                theDetailsInformationIntent.setFlags(EventCode.PERSONLA);
                theDetailsInformationIntent.putExtra("userId", DataClass.USERID);
                getActivity().startActivity(theDetailsInformationIntent);
                break;
        }
    }

    public void refreshView() {
        Glide.with(getActivity()).load(DataClass.USERPHOTO).error(R.drawable.banner_off).into(user_photo);

        user_nice_name.setText(DataClass.UNAME);

        user_content.setText("身体是革命的本钱，全民健身动起来！加油加油加油！");

        SystemUtil.textMagicTool(getActivity(), collection, "352", getString(R.string.collection), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n");

        SystemUtil.textMagicTool(getActivity(), friends, "62", getString(R.string.friends), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n");

        SystemUtil.textMagicTool(getActivity(), dynamic, "19", getString(R.string.dynamic), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n");

        SystemUtil.textMagicTool(getActivity(), balance, "165.50", getString(R.string.balance), R.dimen.dp14, R.dimen.dp12, R.color.red_text, R.color.black_overlay, "\n");

        SystemUtil.textMagicTool(getActivity(), card, "月卡", getString(R.string.card), R.dimen.dp15, R.dimen.dp12, R.color.red_text, R.color.black_overlay, "\n");


    }


}
