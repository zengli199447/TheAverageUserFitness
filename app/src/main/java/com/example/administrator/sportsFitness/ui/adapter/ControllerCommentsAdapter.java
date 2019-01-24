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
import com.example.administrator.sportsFitness.model.bean.CommentsNetBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.ui.view.ShinyView;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCommentsAdapter extends RecyclerView.Adapter<MyViewHolder> implements RecyclerChildAdapter.ChildClickListener {

    Context context;
    List<CommentsNetBean.ResultBean.CommentBean> list;
    boolean status;
    int visibilityCount;

    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    private int loadState = 2;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;
    public final int LOADING = 1;

    public ControllerCommentsAdapter(Context context, List<CommentsNetBean.ResultBean.CommentBean> list, boolean status) {
        this.context = context;
        this.list = list;
        this.status = status;
    }

    public ControllerCommentsAdapter(Context context, List<CommentsNetBean.ResultBean.CommentBean> list, boolean status, int visibilityCount) {
        this.context = context;
        this.list = list;
        this.status = status;
        this.visibilityCount = visibilityCount;
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_controller_comments, parent, false);
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
        ShinyView shiny_view = holder.itemView.findViewById(R.id.shiny_view);
        TextView creat_time = holder.itemView.findViewById(R.id.creat_time);
        TextView comments_content = holder.itemView.findViewById(R.id.comments_content);
        RelativeLayout recycler_view_layout = holder.itemView.findViewById(R.id.recycler_view_layout);
        RecyclerView recycler_view = holder.itemView.findViewById(R.id.recycler_view);
        TextView commtens_about = holder.itemView.findViewById(R.id.commtens_about);
        View line = holder.itemView.findViewById(R.id.line);
        View line_placeholder = holder.itemView.findViewById(R.id.line_placeholder);

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
            CommentsNetBean.ResultBean.CommentBean commentBean = list.get(position);

            Glide.with(context).load(SystemUtil.JudgeUrl(commentBean.getPhoto())).error(R.drawable.banner_off).into(user_img);

            user_name.setText(commentBean.getSecondname());

            shiny_view.setStarMark(Float.valueOf(commentBean.getScore()));

            creat_time.setText(commentBean.getCreatedate());

            comments_content.setText(commentBean.getRemark());

            if (commentBean.getRemark_again().isEmpty()) {
                commtens_about.setVisibility(View.GONE);
            } else {
                commtens_about.setVisibility(View.VISIBLE);
                SystemUtil.textMagicTool(context, commtens_about, context.getString(R.string.comments_additional), commentBean.getRemark_again(),
                        R.dimen.dp14, R.dimen.dp12, R.color.blue_bar, R.color.black_overlay, "\n");
            }

            List<CommentsNetBean.ResultBean.CommentBean.ImgBean> imgBeanList = commentBean.getImg();
            if (imgBeanList.size() > 0) {
                recycler_view_layout.setVisibility(View.VISIBLE);
                List<String> photoList = new ArrayList<>();
                for (CommentsNetBean.ResultBean.CommentBean.ImgBean imgBean : imgBeanList) {
                    photoList.add(imgBean.getImgpath());
                }
                initRecyclerView(recycler_view, photoList, position);
            } else {
                recycler_view_layout.setVisibility(View.GONE);
            }

            initCheckClickListener(user_img, position);
            initCheckClickListener(holder.itemView, position);

            ViewBuilder.ChangeLinearLayoutViewMagin(context, comments_content, 50, 5, 0, 0);
            ViewBuilder.ChangeLinearLayoutViewMagin(context, recycler_view_layout, 50, 10, 0, 0);
            ViewBuilder.ChangeLinearLayoutViewMagin(context, commtens_about, 65, 10, 15, 10);

            if (status || position == list.size() - 1 || commentBean.getRemark_again().isEmpty()) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }
        }

    }

    //类图模式(单张、两张、更多)
    private void initRecyclerView(RecyclerView recycler_view, List<String> imgList, int parentIndex) {
        int spanCount = 0;
        if (imgList.size() == 1) {
            spanCount = 1;
        } else if (imgList.size() == 2) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
        recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, spanCount));
        RecyclerChildAdapter recyclerChildAdapter = new RecyclerChildAdapter(context, imgList, true, parentIndex);
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
            size = list.size() + 1;
        }
        return list.size() == 0 ? 0 : size;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        if (!status)
            notifyItemChanged(list.size());
    }

    @Override
    public void onChildClickListener(int position, int parentIndex) {
        if (commentsParentClickListener != null)
            commentsParentClickListener.onCommentsChildClickListener(position, parentIndex);
    }


    public interface CommentsParentClickListener {
        void onCommentsChildClickListener(int position, int parentIndex);

        void onCommentsCheckClickListener(int position, int id);
    }

    private CommentsParentClickListener commentsParentClickListener;

    public void setCommentsParentClickListener(CommentsParentClickListener commentsParentClickListener) {
        this.commentsParentClickListener = commentsParentClickListener;
    }

}
