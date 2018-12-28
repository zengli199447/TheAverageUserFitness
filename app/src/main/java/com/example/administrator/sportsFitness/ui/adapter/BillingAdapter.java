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
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class BillingAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;

    public BillingAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billing, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView content = holder.itemView.findViewById(R.id.content);
        TextView amount = holder.itemView.findViewById(R.id.amount);

        SystemUtil.textMagicTool(context, content, "账单类型", "2018-05-26   15:52",
                R.dimen.dp15, R.dimen.dp12, R.color.black, R.color.gray_light_text, "\n");

        amount.setText("+2000.00");
        amount.setTextColor(context.getResources().getColor(R.color.blue_bar));

    }

    @Override
    public int getItemCount() {
        return list == null ? 6 : list.size();
    }


}
