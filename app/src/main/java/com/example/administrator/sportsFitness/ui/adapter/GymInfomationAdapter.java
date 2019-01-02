package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GymInfomationAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;

    public GymInfomationAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position != list.size()) {
            type = 0;
        } else {
            type = 1;
        }
        return type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gym_infomation, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_footer, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView people_name = holder.itemView.findViewById(R.id.people_name);
        TextView people_phone = holder.itemView.findViewById(R.id.people_phone);
        TextView add_friend = holder.itemView.findViewById(R.id.add_friend);
        if (people_name != null) {
            people_name.setText(DataClass.UNAME);
            people_phone.setText("10086");
        }
        if (add_friend != null)
            add_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddClickListener != null)
                        onAddClickListener.onAddClickListener();
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    public interface OnAddClickListener {
        void onAddClickListener();
    }

    private OnAddClickListener onAddClickListener;

    public void setOnAddClickListener(OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
    }

}
