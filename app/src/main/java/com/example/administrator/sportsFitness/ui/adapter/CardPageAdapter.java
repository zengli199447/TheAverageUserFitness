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
 * 作者：真理 Created by Administrator on 2018/12/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CardPageAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;

    public CardPageAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_page, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView card_info = holder.itemView.findViewById(R.id.card_info);
        TextView handle_card_status = holder.itemView.findViewById(R.id.handle_card_status);
        TextView price = holder.itemView.findViewById(R.id.price);

        price.setText("价格");

        if (position == 0) {
            handle_card_status.setText(context.getString(R.string.renewal));
            handle_card_status.setBackground(context.getResources().getDrawable(R.drawable.corners_immersed_in_gray));
            SystemUtil.textMagicTool(context, card_info, "卡种类型", "截止日期",
                    R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light_text, "\n");
        } else {
            handle_card_status.setText(context.getString(R.string.handle_card));
            handle_card_status.setBackground(context.getResources().getDrawable(R.drawable.corners_immersed_in_blue));
            card_info.setText("卡种类型");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null)
                    onClickListener.onClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 3 : list.size();
    }

    public interface OnClickListener {
        void onClickListener(int position);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
