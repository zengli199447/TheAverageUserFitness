package com.example.administrator.sportsfitness.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.FriendsCircleNetBean;
import com.example.administrator.sportsfitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsfitness.ui.adapter.RecyclerChildAdapter;
import com.example.administrator.sportsfitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.StickyBoContentTextBuilder;
import com.example.administrator.sportsfitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerFriendsAdapter extends RecyclerView.Adapter<MyViewHolder> implements RecyclerChildAdapter.ChildClickListener, StickyBoContentTextBuilder.OnStickyBoContentTextClickListener {

    String TAG = getClass().getSimpleName();

    Context context;
    List<FriendsCircleNetBean.ResultBean.NewsBean> list;
    boolean status;
    int visibilityCount;

    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    private int loadState = 2;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;
    public final int LOADING = 1;
    private StickyBoContentTextBuilder stickyBoContentTextBuilder;

    public ControllerFriendsAdapter(Context context, List<FriendsCircleNetBean.ResultBean.NewsBean> list, boolean status) {
        this.context = context;
        this.list = list;
        this.status = status;
        stickyBoContentTextBuilder = new StickyBoContentTextBuilder();
        stickyBoContentTextBuilder.setOnStickyBoContentTextClickListener(this);
    }

    public ControllerFriendsAdapter(Context context, List<FriendsCircleNetBean.ResultBean.NewsBean> list, boolean status, int visibilityCount) {
        this.context = context;
        this.list = list;
        this.status = status;
        this.visibilityCount = visibilityCount;
        stickyBoContentTextBuilder = new StickyBoContentTextBuilder();
        stickyBoContentTextBuilder.setOnStickyBoContentTextClickListener(this);
    }

    @Override
    public int getItemViewType(int position) {
        int Type = 0;
        if (position == list.size()) {
            Type = TYPE_FOOTER;
        } else {
            Type = TYPE_ITEM;
        }
        return Type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_controller_friends, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_name = holder.itemView.findViewById(R.id.user_name);
        ImageView about = holder.itemView.findViewById(R.id.about);
        TextView dynamic_content = holder.itemView.findViewById(R.id.dynamic_content);
        RelativeLayout recycler_view_layout = holder.itemView.findViewById(R.id.recycler_view_layout);
        RecyclerView recycler_view = holder.itemView.findViewById(R.id.recycler_view);
        AppCompatCheckBox support_check = holder.itemView.findViewById(R.id.support_check);
        TextView comments = holder.itemView.findViewById(R.id.comments);
        TextView forwarding = holder.itemView.findViewById(R.id.forwarding);
        View line = holder.itemView.findViewById(R.id.line);
        RelativeLayout controller_layout = holder.itemView.findViewById(R.id.controller_layout);
        ImageView clear_dynamic = holder.itemView.findViewById(R.id.clear_dynamic);
        RelativeLayout progress_bar = holder.itemView.findViewById(R.id.progress_bar);

        if (position == list.size()) {
            switch (loadState) {
                case LOADING:
                    progress_bar.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    progress_bar.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    progress_bar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            FriendsCircleNetBean.ResultBean.NewsBean newsBean = list.get(position);

            Glide.with(context).load(SystemUtil.JudgeUrl(newsBean.getPhoto())).error(R.drawable.banner_off).into(user_img);

            SystemUtil.textMagicTool(context, user_name, newsBean.getSecondname(), newsBean.getCreatedate_show(), R.dimen.dp15, R.dimen.dp10,
                    R.color.blue_bar, R.color.gray_light_text, "\n");

            comments.setText(new StringBuffer().append(context.getString(R.string.comments)).append(SystemUtil.JudgeCount(newsBean.getTotal_ping())).toString());

            forwarding.setText(new StringBuffer().append(context.getString(R.string.forwarding)).append(SystemUtil.JudgeCount(newsBean.getTotal_zhuan())).toString());

            support_check.setText(SystemUtil.JudgeCount(newsBean.getTotal_zan()));

            List<FriendsCircleNetBean.ResultBean.NewsBean.ImgpathjsonBean> imgpathjson = newsBean.getImgpathjson();

            if (newsBean.getContent().isEmpty() && newsBean.getUser_zhuan().size() == 0) {
                dynamic_content.setVisibility(View.GONE);
            } else {
                dynamic_content.setVisibility(View.VISIBLE);
                List<FriendsCircleNetBean.ResultBean.NewsBean.UserZhuanBean> user_zhuan = newsBean.getUser_zhuan();
                if (user_zhuan.size() > 0) {
                    String sizeLine = "";
                    for (int i = 0; i < user_zhuan.size(); i++) {
                        String content = new StringBuffer().append(DataClass.FORWARDING_TAG).append(user_zhuan.get(i).getSecondname()).append(" ").toString();
                        sizeLine = new StringBuffer().append(sizeLine).append(content).toString();
                    }
                    dynamic_content.setText(stickyBoContentTextBuilder.getWeiBoContent(new StringBuffer().append(sizeLine).append(newsBean.getContent()).toString(), context, dynamic_content, position));
                } else {
                    dynamic_content.setText(newsBean.getContent());
                }
            }

            if (imgpathjson.size() > 0) {
                recycler_view_layout.setVisibility(View.VISIBLE);
                List<String> photoList = new ArrayList<>();
                for (FriendsCircleNetBean.ResultBean.NewsBean.ImgpathjsonBean imgpathjsonBean : imgpathjson) {
                    photoList.add(imgpathjsonBean.getImgpath());
                }
                initRecyclerView(recycler_view, photoList, position);
            } else {
                recycler_view_layout.setVisibility(View.GONE);
            }

            if (newsBean.getIfzan_cleck().equals("1")) {
                support_check.setChecked(true);
            } else {
                support_check.setChecked(false);
            }

            initCheckClickListener(about, position);
            initCheckClickListener(user_img, position);
            initCheckClickListener(forwarding, position);
            initCheckClickListener(support_check, position);
            initCheckClickListener(comments, position);
            initCheckClickListener(holder.itemView, position);
            initCheckClickListener(clear_dynamic, position);
            initCheckClickListener(dynamic_content, position);

            ViewBuilder.ChangeLinearLayoutViewMagin(context, dynamic_content, 50, 5, 0, 0);
            ViewBuilder.ChangeLinearLayoutViewMagin(context, recycler_view_layout, 50, 10, 0, 0);
            ViewBuilder.ChangeLinearLayoutViewMagin(context, controller_layout, 50, 0, 0, 0);

            if (status || position == list.size() - 1) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }

            if (DataClass.USERID.equals(newsBean.getUserid())) {
                clear_dynamic.setVisibility(View.VISIBLE);
                about.setVisibility(View.GONE);
            } else {
                clear_dynamic.setVisibility(View.GONE);
                about.setVisibility(View.VISIBLE);
            }

        }
    }

    //类图模式(单张、两张、更多)
    private void initRecyclerView(RecyclerView recycler_view, List<String> imgList, int position) {
        int spanCount = 0;
        if (imgList.size() == 1) {
            spanCount = 1;
        } else if (imgList.size() == 2) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
        recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, spanCount));
        RecyclerChildAdapter recyclerChildAdapter = new RecyclerChildAdapter(context, imgList, true, position);
        recycler_view.setAdapter(recyclerChildAdapter);
        recyclerChildAdapter.notifyDataSetChanged();
        recyclerChildAdapter.setChildClickListener(this);
    }

    //check集
    private void initCheckClickListener(View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dynamicParentClickListener != null)
                    dynamicParentClickListener.onCheckClickListener(position, v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (status) {
            size = visibilityCount;
        } else {
            size = list.size() + 1;
        }
        return list.size() == 0 ? 0 : size;
    }

    @Override
    public void onChildClickListener(int position, int parentIndex) {
        if (dynamicParentClickListener != null)
            dynamicParentClickListener.onChildClickListener(position, parentIndex);
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        if (!status)
            notifyItemChanged(list.size());
    }

    @Override
    public void onStickyBoContentTextClickListener(String content, int index) {
        Intent intent = new Intent(context, TheDetailsInformationActivity.class);
        FriendsCircleNetBean.ResultBean.NewsBean newsBean = list.get(index);
        for (FriendsCircleNetBean.ResultBean.NewsBean.UserZhuanBean zhuanBean : newsBean.getUser_zhuan()) {
            if (content.contains(zhuanBean.getSecondname())) {
                intent.putExtra("userId", zhuanBean.getUserid());
                intent.putExtra("userName", zhuanBean.getSecondname());
                context.startActivity(intent);
            }
        }
    }

    public interface DynamicParentClickListener {
        void onChildClickListener(int position, int parentIndex);

        void onCheckClickListener(int position, int id);
    }

    private DynamicParentClickListener dynamicParentClickListener;

    public void setDynamicParentClickListener(DynamicParentClickListener dynamicParentClickListener) {
        this.dynamicParentClickListener = dynamicParentClickListener;
    }


}
