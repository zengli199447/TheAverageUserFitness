package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.model.bean.CategoryNetBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CategoryAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<CategoryNetBean> list;

    public CategoryAdapter(Context context, List<CategoryNetBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        CategoryNetBean categoryNetBean = list.get(position);
        View tag_life = holder.itemView.findViewById(R.id.tag_life);
        TextView category = holder.itemView.findViewById(R.id.category);
        if (categoryNetBean.isSelectStatus()) {
            tag_life.setVisibility(View.VISIBLE);
            category.setBackground(context.getResources().getDrawable(R.drawable.white));
            category.setTextColor(context.getResources().getColor(R.color.blue_bar));
        } else {
            tag_life.setVisibility(View.GONE);
            category.setBackground(context.getResources().getDrawable(R.drawable.gray_light));
            category.setTextColor(context.getResources().getColor(R.color.black_overlay));
        }
        category.setText(categoryNetBean.getType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categroyItemClickListener != null)
                    categroyItemClickListener.onCategroyItemClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface CategroyItemClickListener {
        void onCategroyItemClickListener(int positon);
    }

    private CategroyItemClickListener categroyItemClickListener;

    public void setCategroyItemClickListener(CategroyItemClickListener categroyItemClickListener) {
        this.categroyItemClickListener = categroyItemClickListener;
    }

}
