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
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.FitnessCourseNetBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.ui.view.ShinyView;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FitnessCourseAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;
    boolean maginStatus;
    int visibilityCount = -1;

    public FitnessCourseAdapter(Context context, List<Object> list, boolean maginStatus) {
        this.context = context;
        this.list = list;
        this.maginStatus = maginStatus;
    }

    public FitnessCourseAdapter(Context context, List<Object> list, boolean maginStatus, int visibilityCount) {
        this.context = context;
        this.list = list;
        this.maginStatus = maginStatus;
        this.visibilityCount = visibilityCount;
    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitness_course, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_the_gym_layout, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coach_layout, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        View line = holder.itemView.findViewById(R.id.line);
        ImageView img = holder.itemView.findViewById(R.id.img);
        TextView title = holder.itemView.findViewById(R.id.title);
        TextView hot = holder.itemView.findViewById(R.id.hot);
        TextView price = holder.itemView.findViewById(R.id.price);
        TextView sign_up = holder.itemView.findViewById(R.id.sign_up);
        RelativeLayout layout_content = holder.itemView.findViewById(R.id.layout_content);
        //No.1
        TextView time = holder.itemView.findViewById(R.id.time);
        TextView coordinates = holder.itemView.findViewById(R.id.coordinates);

        //N02.
        TextView distance = holder.itemView.findViewById(R.id.distance);
        ShinyView shiny_view = holder.itemView.findViewById(R.id.shiny_view);

        //No3.
        TextView convention = holder.itemView.findViewById(R.id.convention);

        if (maginStatus) {
            ViewBuilder.ChangeLinearLayoutViewMagin(context, layout_content, 15, 15, 15, 15);
            ViewBuilder.ChangeLinearLayoutViewMagin(context, line, 15, 0, 0, 0);
        }

        if (position == 0) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        SystemUtil.textMagicTool(context, price, context.getString(R.string.money), "159",
                R.dimen.dp10, R.dimen.dp16, R.color.red_text, R.color.red_text, "");

        Glide.with(context).load(DataClass.USERPHOTO).error(R.drawable.banner_off).into(img);

        onClickListener(holder.itemView, position);
        onClickListener(sign_up, position);
    }

    private void onClickListener(View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFitnessCourseClickListener != null)
                    onFitnessCourseClickListener.onFitnessCourseClickListener(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null)
            count = list.size();
        if (visibilityCount > 0)
            count = visibilityCount;
        if (visibilityCount > 0) {
            return list == null ? 2 : count;
        } else {
            return list == null ? 12 : count;
        }
    }

    public interface onFitnessCourseClickListener {
        void onFitnessCourseClickListener(View view, int position);
    }

    private onFitnessCourseClickListener onFitnessCourseClickListener;

    public void setOnFitnessCourseClickListener(onFitnessCourseClickListener onFitnessCourseClickListener) {
        this.onFitnessCourseClickListener = onFitnessCourseClickListener;
    }

}
