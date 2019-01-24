package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.model.bean.StudentFormNetBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class StudentFormAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;

    public StudentFormAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ImageView user_img = holder.itemView.findViewById(R.id.user_img);
        TextView user_name = holder.itemView.findViewById(R.id.user_name);
        TextView user_content = holder.itemView.findViewById(R.id.user_content);
        TextView distance = holder.itemView.findViewById(R.id.distance);
        View line = holder.itemView.findViewById(R.id.line);
        RelativeLayout progress_bar = holder.itemView.findViewById(R.id.progress_bar);


        StudentFormNetBean.ResultBean.StudentBean studentBean =(StudentFormNetBean.ResultBean.StudentBean) list.get(position);

        Glide.with(context).load(SystemUtil.JudgeUrl(studentBean.getPhoto())).error(R.drawable.banner_off).into(user_img);

        user_content.setText(studentBean.getSignature());

        user_name.setText(studentBean.getSecondname());

        distance.setVisibility(View.GONE);

        if (position == list.size() - 1) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStudentClickListener != null)
                    onStudentClickListener.onStudentClickListener(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface OnStudentClickListener {
        void onStudentClickListener(int position);
    }

    private OnStudentClickListener onStudentClickListener;

    public void setOnStudentClickListener(OnStudentClickListener onStudentClickListener) {
        this.onStudentClickListener = onStudentClickListener;
    }

}
