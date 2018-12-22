package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.model.bean.ArrangeBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ArrangeAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<ArrangeBean> list;

    public ArrangeAdapter(Context context, List<ArrangeBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_arrange_directory, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView content = holder.itemView.findViewById(R.id.content);
        ImageView tag_icon = holder.itemView.findViewById(R.id.tag_icon);
        ArrangeBean arrangeBean = list.get(position);
        SystemUtil.textMagicTool(context, content, arrangeBean.getType(), arrangeBean.getAnnotation(),
                R.dimen.album_dp_15, R.dimen.dp13, R.color.black, R.color.gray_light_text, "\n");
        int file = 0;
        switch (arrangeBean.getIndex()) {
            case 0:
                file = R.drawable.tease_friends_icon;
                break;
            case 1:
                file = R.drawable.treat_icon;
                break;
            case 2:
                file = R.drawable.personal_training_icon;
                break;
            case 3:
                file = R.drawable.training_camp_icon;
                break;
            case 4:
                file = R.drawable.trip_icon;
                break;
        }
        Glide.with(context).load(file).into(tag_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItenClickListener != null)
                    onItenClickListener.OnItenClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItenClickListener {
        void OnItenClickListener(int position);
    }

    private OnItenClickListener onItenClickListener;

    public void setOnItenClickListener(OnItenClickListener onItenClickListener) {
        this.onItenClickListener = onItenClickListener;
    }


}
