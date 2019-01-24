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
import com.example.administrator.sportsFitness.model.bean.InfoAboutNetBean;
import com.example.administrator.sportsFitness.model.bean.UserInfoNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.component.CardPageActivity;
import com.example.administrator.sportsFitness.ui.activity.component.CollectionActivity;
import com.example.administrator.sportsFitness.ui.activity.component.FriendsCircleActivity;
import com.example.administrator.sportsFitness.ui.activity.component.MoreDataReferenceActivity;
import com.example.administrator.sportsFitness.ui.activity.component.ShowGeneraActivity;
import com.example.administrator.sportsFitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsFitness.ui.activity.component.WalletActivity;
import com.example.administrator.sportsFitness.ui.activity.component.WebConcentratedActivity;
import com.example.administrator.sportsFitness.ui.controller.ControllerMine;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MineFragment extends BaseFragment implements ControllerMine.OnPersonalContentNetWorkListener {

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
    public void onResume() {
        super.onResume();
        SystemUtil.textMagicTool(getActivity(), balance, DataClass.MONEY, getString(R.string.balance), R.dimen.dp14, R.dimen.dp12, R.color.red_text, R.color.black_overlay, "\n");
    }

    @Override
    protected void initListener() {
        controllerMine.setOnPersonalContentNetWorkListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.ranking_list, R.id.weekly, R.id.CV, R.id.sign_in, R.id.collection, R.id.friends,
            R.id.dynamic, R.id.balance, R.id.card, R.id.user_photo, R.id.setting})
    @Override
    protected void onClickAble(View view) {
        Intent webIntent = new Intent(getActivity(), WebConcentratedActivity.class);
        switch (view.getId()) {
            case R.id.ranking_list:
                webIntent.putExtra("link", new StringBuffer().append("do=ranklist1&userid=").append(DataClass.USERID).toString());
                webIntent.putExtra("titleName", getString(R.string.ranking_list));
                startActivity(webIntent);
                break;
            case R.id.weekly:
                webIntent.putExtra("link", new StringBuffer().append("do=week_bao&userid=").append(DataClass.USERID).toString());
                webIntent.putExtra("titleName", getString(R.string.weekly));
                webIntent.putExtra("titleAbout", getString(R.string.the_share));
                startActivity(webIntent);
                break;
            case R.id.CV:
                webIntent.putExtra("link", new StringBuffer().append("do=record&userid=").append(DataClass.USERID).toString());
                webIntent.putExtra("titleName", getString(R.string.CV));
                startActivity(webIntent);
                break;
            case R.id.sign_in:
                webIntent.putExtra("link_all", new StringBuffer().append(DataClass.URL).append("pf_wx.php?act=qiandao&do=qiandao&uid=").append(DataClass.USERID).toString());
                webIntent.putExtra("titleName", getString(R.string.sign_in));
                startActivity(webIntent);
                break;
            case R.id.collection:
                getActivity().startActivity(new Intent(getActivity(), CollectionActivity.class));
                break;
            case R.id.friends:
                getActivity().startActivity(new Intent(getActivity(), FriendsCircleActivity.class).setFlags(0));
                break;
            case R.id.dynamic:
                Intent moreDataReferenceIntent = new Intent(getActivity(), MoreDataReferenceActivity.class);
                moreDataReferenceIntent.setFlags(EventCode.DYNAMIC);
                moreDataReferenceIntent.putExtra("relatedId", DataClass.USERID);
                moreDataReferenceIntent.putExtra("relatedName", DataClass.UNAME);
                moreDataReferenceIntent.putExtra("relatedType", "3");
                getActivity().startActivity(moreDataReferenceIntent);
                break;
            case R.id.balance:
                getActivity().startActivity(new Intent(getActivity(), WalletActivity.class));
                break;
            case R.id.card:
                getActivity().startActivity(new Intent(getActivity(), CardPageActivity.class));
                break;
            case R.id.user_photo:
                Intent theDetailsInformationIntent = new Intent(getActivity(), TheDetailsInformationActivity.class);
                theDetailsInformationIntent.putExtra("userId", DataClass.USERID);
                theDetailsInformationIntent.putExtra("userName", DataClass.UNAME);
                getActivity().startActivity(theDetailsInformationIntent);
                break;
            case R.id.setting:
                getActivity().startActivity(new Intent(getActivity(), ShowGeneraActivity.class).setFlags(EventCode.ACCOUNT_MANAGEMENT));
                break;
        }
    }

    public void refreshView() {

    }

    @Override
    public void onPersonalContentNetWorkListener(UserInfoNetBean.ResultBean result) {

        Glide.with(getActivity()).load(SystemUtil.JudgeUrl(result.getPhoto())).error(R.drawable.banner_off).into(user_photo);

        user_nice_name.setText(DataClass.UNAME);

        user_content.setText(result.getSignature_show());

    }

    @Override
    public void onNetInfoAboutListener(InfoAboutNetBean.ResultBean result) {

        SystemUtil.textMagicTool(getActivity(), collection, result.getCollect(), getString(R.string.collection), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n");

        SystemUtil.textMagicTool(getActivity(), friends, result.getFriend(), getString(R.string.friends), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n");

        SystemUtil.textMagicTool(getActivity(), dynamic, result.getNews(), getString(R.string.dynamic), R.dimen.dp14, R.dimen.dp12, R.color.white, R.color.white, "\n");

        SystemUtil.textMagicTool(getActivity(), balance, result.getMoneytotal(), getString(R.string.balance), R.dimen.dp14, R.dimen.dp12, R.color.red_text, R.color.black_overlay, "\n");

        SystemUtil.textMagicTool(getActivity(), card, result.getCardtype(), getString(R.string.card), R.dimen.dp15, R.dimen.dp12, R.color.red_text, R.color.black_overlay, "\n");

    }


}
