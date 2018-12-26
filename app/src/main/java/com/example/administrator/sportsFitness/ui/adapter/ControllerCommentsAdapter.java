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
import com.example.administrator.sportsFitness.ui.view.ShinyView;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCommentsAdapter extends RecyclerView.Adapter<MyViewHolder> implements RecyclerChildAdapter.ChildClickListener {

    Context context;
    List<Object> list;
    boolean status;
    int visibilityCount;

    public ControllerCommentsAdapter(Context context, List<Object> list, boolean status) {
        this.context = context;
        this.list = list;
        this.status = status;
    }

    public ControllerCommentsAdapter(Context context, List<Object> list, boolean status, int visibilityCount) {
        this.context = context;
        this.list = list;
        this.status = status;
        this.visibilityCount = visibilityCount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_controller_comments, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_name = holder.itemView.findViewById(R.id.user_name);
        ShinyView shiny_view = holder.itemView.findViewById(R.id.shiny_view);
        TextView creat_time = holder.itemView.findViewById(R.id.creat_time);
        TextView comments_content = holder.itemView.findViewById(R.id.comments_content);
        RelativeLayout recycler_view_layout = holder.itemView.findViewById(R.id.recycler_view_layout);
        RecyclerView recycler_view = holder.itemView.findViewById(R.id.recycler_view);
        TextView commtens_about = holder.itemView.findViewById(R.id.commtens_about);
        View line = holder.itemView.findViewById(R.id.line);


        Glide.with(context).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(user_img);

        user_name.setText(DataClass.UNAME);

        comments_content.setText("一组超棒的按摩指南，累了、睡前都可以做，做完非常舒服~~ get");

        SystemUtil.textMagicTool(context, commtens_about, context.getString(R.string.comments_additional), "评论内容",
                R.dimen.dp14, R.dimen.dp12, R.color.blue_bar, R.color.black_overlay, "\n");

        recycler_view_layout.setVisibility(View.VISIBLE);

        initRecyclerView(recycler_view, Arrays.asList(context.getResources().getStringArray(R.array.photo_list)));


        initCheckClickListener(user_img, position);
        initCheckClickListener(holder.itemView, position);

        ViewBuilder.ChangeLinearLayoutViewMagin(context, comments_content, 50, 5, 0, 0);
        ViewBuilder.ChangeLinearLayoutViewMagin(context, recycler_view_layout, 50, 10, 0, 0);
        ViewBuilder.ChangeLinearLayoutViewMagin(context, commtens_about, 65, 0, 0, 10);

        if (status && visibilityCount == 1)
            line.setVisibility(View.GONE);
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
                if (commentsParentClickListener != null)
                    commentsParentClickListener.onCommentsCheckClickListener(position, v.getId());
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
        if (commentsParentClickListener != null)
            commentsParentClickListener.onCommentsChildClickListener(position, object);
    }


    public interface CommentsParentClickListener {
        void onCommentsChildClickListener(int position, String object);

        void onCommentsCheckClickListener(int position, int id);
    }

    private CommentsParentClickListener commentsParentClickListener;

    public void setCommentsParentClickListener(CommentsParentClickListener commentsParentClickListener) {
        this.commentsParentClickListener = commentsParentClickListener;
    }


}
