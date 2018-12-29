package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
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
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerFriendsAdapter extends RecyclerView.Adapter<MyViewHolder> implements RecyclerChildAdapter.ChildClickListener {

    Context context;
    List<Object> list;
    boolean status;
    int visibilityCount;

    public ControllerFriendsAdapter(Context context, List<Object> list, boolean status) {
        this.context = context;
        this.list = list;
        this.status = status;
    }

    public ControllerFriendsAdapter(Context context, List<Object> list, boolean status, int visibilityCount) {
        this.context = context;
        this.list = list;
        this.status = status;
        this.visibilityCount = visibilityCount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_controller_friends, parent, false);
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

        Glide.with(context).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(user_img);

        SystemUtil.textMagicTool(context, user_name, DataClass.UNAME, "今天  15:20", R.dimen.dp15, R.dimen.dp10,
                R.color.blue_bar, R.color.gray_light_text, "\n");

        dynamic_content.setText("一组超棒的按摩指南，累了、睡前都可以做，做完非常舒服~~ get");

        comments.setText(new StringBuffer().append(context.getString(R.string.comments)).append(SystemUtil.JudgeCount(1000)).toString());

        forwarding.setText(new StringBuffer().append(context.getString(R.string.forwarding)).append(SystemUtil.JudgeCount(1000)).toString());

        recycler_view_layout.setVisibility(View.VISIBLE);

        initRecyclerView(recycler_view, Arrays.asList(context.getResources().getStringArray(R.array.photo_list)));

        initCheckClickListener(about, position);
        initCheckClickListener(user_img, position);
        initCheckClickListener(forwarding, position);
        initCheckClickListener(support_check, position);
        initCheckClickListener(comments, position);
        initCheckClickListener(holder.itemView, position);

        ViewBuilder.ChangeLinearLayoutViewMagin(context, dynamic_content, 50, 5, 0, 0);
        ViewBuilder.ChangeLinearLayoutViewMagin(context, recycler_view_layout, 50, 10, 0, 0);
        ViewBuilder.ChangeLinearLayoutViewMagin(context, controller_layout, 50, 0, 0, 0);

        if (status)
            line.setVisibility(View.GONE);

        if (false) {
            about.setImageDrawable(context.getResources().getDrawable(R.drawable.basket_icon));
        }


    }

    //类图模式(单张、两张、更多)
    private void initRecyclerView(RecyclerView recycler_view, List<String> imgList) {
        int spanCount = 0;
        if (imgList.size() == 1) {
            spanCount = 1;
        } else if (imgList.size() == 2) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
        recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, spanCount));
        RecyclerChildAdapter recyclerChildAdapter = new RecyclerChildAdapter(context, imgList, true);
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
            size = 6;
        }
        return list == null ? size : list.size();
    }

    @Override
    public void onChildClickListener(int position, String object) {
        if (dynamicParentClickListener != null)
            dynamicParentClickListener.onChildClickListener(position, object);
    }


    public interface DynamicParentClickListener {
        void onChildClickListener(int position, String object);

        void onCheckClickListener(int position, int id);

        void onClearCheckClickListener(int position);

    }

    private DynamicParentClickListener dynamicParentClickListener;

    public void setDynamicParentClickListener(DynamicParentClickListener dynamicParentClickListener) {
        this.dynamicParentClickListener = dynamicParentClickListener;
    }


}
