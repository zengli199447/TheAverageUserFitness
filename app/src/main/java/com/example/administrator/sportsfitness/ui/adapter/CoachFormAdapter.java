package com.example.administrator.sportsfitness.ui.adapter;

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
import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.model.bean.CoachFormNetBean;
import com.example.administrator.sportsfitness.model.bean.FriendsCircleRelatedNetBean;
import com.example.administrator.sportsfitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsfitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CoachFormAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;

    public CoachFormAdapter(Context context, List<Object> list) {
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

        CoachFormNetBean.ResultBean.ShopBean shopBean = (CoachFormNetBean.ResultBean.ShopBean) list.get(position);

        Glide.with(context).load(SystemUtil.JudgeUrl(shopBean.getPhoto())).error(R.drawable.banner_off).into(user_img);

        user_content.setText(shopBean.getSignature());

        user_name.setText(shopBean.getSecondname());

        distance.setVisibility(View.GONE);

        if (position == list.size() - 1) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCoachClickListener != null)
                    onCoachClickListener.onCoachClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface OnCoachClickListener {
        void onCoachClickListener(int position);
    }

    private OnCoachClickListener onCoachClickListener;

    public void setOnCoachClickListener(OnCoachClickListener onCoachClickListener) {
        this.onCoachClickListener = onCoachClickListener;
    }

}
