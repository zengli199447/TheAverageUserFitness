package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class RecyclerChildAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<String> list;
    boolean viewStatus;
    boolean status;
    int parentIndex;

    /**
     * @param context 文络
     * @param list 数据集
     * @param status  列表、详情 宽度切换标示
     */
    public RecyclerChildAdapter(Context context, List<String> list, boolean status, int parentIndex) {
        this.context = context;
        this.list = list;
        this.status = status;
        this.parentIndex = parentIndex;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic_photo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final String object = list.get(position);
        ImageView photo_img = holder.itemView.findViewById(R.id.photo_img);
        int width = 0;
        if (status) {
            width = DataClass.WINDOWS_WIDTH - 70;
        } else {
            width = DataClass.WINDOWS_WIDTH - 30;
        }
        if (viewStatus) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (list.size() > 2) {
                layoutParams.height = SystemUtil.dp2px(context, (width / 3) - 2);
            } else if (list.size() == 2) {
                layoutParams.height = SystemUtil.dp2px(context, (width / 3) + 10);
            } else {
                layoutParams.height = SystemUtil.dp2px(context, width * 2 / 5);
            }
            holder.itemView.setLayoutParams(layoutParams);
        }
        Glide.with(context).load(SystemUtil.JudgeUrl(list.get(position))).error(R.drawable.banner_off).into(photo_img);
        photo_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childClickListener != null)
                    childClickListener.onChildClickListener(position, parentIndex);
            }
        });

    }

    @Override
    public int getItemCount() {
//        if (list.size() == 1 || list.size() > 2)
            viewStatus = true;
        return list.size() == 0 ? 0 : list.size();
    }

    public interface ChildClickListener {
        void onChildClickListener(int position, int parentIndex);
    }

    private ChildClickListener childClickListener;

    public void setChildClickListener(ChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }


}
