package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.example.administrator.sportsFitness.model.bean.FriendsCircleRelatedNetBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsCircleRelatedAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<FriendsCircleRelatedNetBean.ResultBean.UserlistBean> list;

    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    private int loadState = 2;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;
    public final int LOADING = 1;

    public FriendsCircleRelatedAdapter(Context context, List<FriendsCircleRelatedNetBean.ResultBean.UserlistBean> list) {
        this.context = context;
        this.list = list;
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_name = holder.itemView.findViewById(R.id.user_name);
        TextView user_content = holder.itemView.findViewById(R.id.user_content);
        TextView distance = holder.itemView.findViewById(R.id.distance);
        View line = holder.itemView.findViewById(R.id.line);
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
            FriendsCircleRelatedNetBean.ResultBean.UserlistBean userlistBean = list.get(position);

            Glide.with(context).load(SystemUtil.JudgeUrl(userlistBean.getPhoto())).error(R.drawable.banner_off).into(user_img);

            user_content.setText(userlistBean.getSignature());

            user_name.setText(userlistBean.getSecondname());

            if (!userlistBean.getDistance().isEmpty()) {
                distance.setVisibility(View.VISIBLE);
                distance.setText(SystemUtil.JudgeFormatDistance(userlistBean.getDistance()));
            } else {
                distance.setVisibility(View.GONE);
            }

            if (position == list.size() - 1) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFriendClickListener != null)
                        onFriendClickListener.onFriendClickListener(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyItemChanged(list.size());
    }

    public interface OnFriendClickListener {
        void onFriendClickListener(int position);
    }

    private OnFriendClickListener onFriendClickListener;

    public void setOnFriendClickListener(OnFriendClickListener onFriendClickListener) {
        this.onFriendClickListener = onFriendClickListener;
    }

}
