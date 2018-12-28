package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SelectDiversifiedFormAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;

    public SelectDiversifiedFormAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_select_diversified_form, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final AppCompatCheckBox check_select = holder.itemView.findViewById(R.id.check_select);
        ImageView friend_img = holder.itemView.findViewById(R.id.friend_img);
        TextView friend_content = holder.itemView.findViewById(R.id.friend_content);
        View line = holder.itemView.findViewById(R.id.line);

        Glide.with(context).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(friend_img);
        SystemUtil.textMagicTool(context, friend_content, DataClass.UNAME, "在健身中不断磨练自己"
                , R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.black_overlay, "\n");


        if (position == 0) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckRefreshView(check_select, position);
            }
        });

    }

    private void CheckRefreshView(AppCompatCheckBox checkSelect, int position) {
        if (checkSelect.isChecked()) {
            checkSelect.setChecked(false);
            checkItemListener.onCheckItemListener(position, false);
        } else {
            checkSelect.setChecked(true);
            checkItemListener.onCheckItemListener(position, true);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 6 : list.size();
    }

    public interface CheckItemListener {
        void onCheckItemListener(int position, boolean status);
    }

    private CheckItemListener checkItemListener;

    public void setCheckItemListener(CheckItemListener checkItemListener) {
        this.checkItemListener = checkItemListener;
    }



}
