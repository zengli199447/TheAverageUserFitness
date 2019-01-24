package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.model.bean.DynamicDetailsNetBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/18.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DynamicCommentsAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<DynamicDetailsNetBean.ResultBean.ReplyBean> list;

    public DynamicCommentsAdapter(Context context, List<DynamicDetailsNetBean.ResultBean.ReplyBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_comments, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView user_neck_name = holder.itemView.findViewById(R.id.user_neck_name);
        TextView user_neck_name_to = holder.itemView.findViewById(R.id.user_neck_name_to);
        TextView reply = holder.itemView.findViewById(R.id.reply);

        TextView content = holder.itemView.findViewById(R.id.content);
        RelativeLayout placeholder_filling = holder.itemView.findViewById(R.id.placeholder_filling);
        View line = holder.itemView.findViewById(R.id.line);
        if (list.size() > 0) {
            DynamicDetailsNetBean.ResultBean.ReplyBean replyBean = list.get(position);
            if (!replyBean.getUsername_to().isEmpty() && !replyBean.getUserid_to().equals("0")) {
                user_neck_name.setText(replyBean.getUsername_from());
                user_neck_name_to.setText(replyBean.getUsername_to());
                reply.setVisibility(View.VISIBLE);
                user_neck_name_to.setVisibility(View.VISIBLE);
            } else {
                reply.setVisibility(View.GONE);
                user_neck_name_to.setVisibility(View.GONE);
                user_neck_name.setText(replyBean.getUsername_from());
            }
            content.setText(replyBean.getContent());
        }
        if (position == 0) {
//            placeholder_filling.setVisibility(View.VISIBLE);
            line.setVisibility(View.GONE);
        } else {
//            placeholder_filling.setVisibility(View.GONE);
            line.setVisibility(View.VISIBLE);
        }
        onClickListener(user_neck_name, position, 0);
        onClickListener(user_neck_name_to, position, 1);
    }

    private void onClickListener(View view, final int position, final int type) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replyClickListener != null)
                    replyClickListener.onReplyClickListener(position, type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface ReplyClickListener {
        void onReplyClickListener(int position, int type);

    }

    private ReplyClickListener replyClickListener;

    public void setReplyClickListener(ReplyClickListener replyClickListener) {
        this.replyClickListener = replyClickListener;
    }
}
