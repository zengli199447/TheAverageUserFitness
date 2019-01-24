package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.model.bean.FriendsCircleSelectBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsCircleSelectAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<FriendsCircleSelectBean> list;

    public FriendsCircleSelectAdapter(Context context, List<FriendsCircleSelectBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends_circle_select, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView content = holder.itemView.findViewById(R.id.content);
        View line = holder.itemView.findViewById(R.id.line);
        final FriendsCircleSelectBean friendsCircleSelectBean = list.get(position);
        content.setText(friendsCircleSelectBean.getTypeName());

        if (friendsCircleSelectBean.isStatus()) {
            content.setTextColor(context.getResources().getColor(R.color.blue_bar));
        } else {
            content.setTextColor(context.getResources().getColor(R.color.black_overlay));
        }

        if (position == list.size() - 1) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendsCircleSelectListener != null)
                    friendsCircleSelectListener.onFriendsCircleSelectListener(friendsCircleSelectBean.getTypeName(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface FriendsCircleSelectListener {
        void onFriendsCircleSelectListener(String content, int position);
    }

    private FriendsCircleSelectListener friendsCircleSelectListener;

    public void setFriendsCircleSelectListener(FriendsCircleSelectListener friendsCircleSelectListener) {
        this.friendsCircleSelectListener = friendsCircleSelectListener;
    }


}
