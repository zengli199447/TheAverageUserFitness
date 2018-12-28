package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MyTripAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;

    public MyTripAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_trip, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView creat_time = holder.itemView.findViewById(R.id.creat_time);
        TextView type = holder.itemView.findViewById(R.id.type);
        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        ImageView gym_img = holder.itemView.findViewById(R.id.gym_img);
        TextView content = holder.itemView.findViewById(R.id.content);

        SystemUtil.textMagicTool(context, content, "果粒橙教练", "地点坐标",
                R.dimen.dp14, R.dimen.dp13, R.color.black_overlay, R.color.gray_light_text, "\n");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTripClickListener != null)
                    onTripClickListener.OnTripClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 3 : list.size();
    }

    public interface OnTripClickListener {
        void OnTripClickListener(int position);
    }

    private OnTripClickListener onTripClickListener;

    public void setOnTripClickListener(OnTripClickListener onTripClickListener) {
        this.onTripClickListener = onTripClickListener;
    }


}
