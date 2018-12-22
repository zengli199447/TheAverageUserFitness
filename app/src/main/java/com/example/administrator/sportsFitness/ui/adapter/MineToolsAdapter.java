package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MineToolsAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    private final List<String> list;

    public MineToolsAdapter(Context context) {
        this.context = context;
        list = Arrays.asList(context.getResources().getStringArray(R.array.mine_tools));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_tools, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView type = holder.itemView.findViewById(R.id.type);
        View line = holder.itemView.findViewById(R.id.line);
        type.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mineToolsClickListener != null)
                    mineToolsClickListener.onMineToolsClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface MineToolsClickListener {
        void onMineToolsClickListener(int position);
    }

    private MineToolsClickListener mineToolsClickListener;

    public void setMineToolsClickListener(MineToolsClickListener mineToolsClickListener) {
        this.mineToolsClickListener = mineToolsClickListener;
    }

}
