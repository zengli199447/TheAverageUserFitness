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
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.OrderFormNetBean;
import com.example.administrator.sportsfitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.umeng.commonsdk.debug.E;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/3.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OrderFormAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<OrderFormNetBean.ResultBean.OrderBean> list;

    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    private int loadState = 2;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;
    public final int LOADING = 1;

    public OrderFormAdapter(Context context, List<OrderFormNetBean.ResultBean.OrderBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        int Type = 0;
        if (position == list.size()) {
            Type = TYPE_FOOTER;
        } else {
            Type = TYPE_ITEM;
        }
        return Type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_form, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        ImageView img = holder.itemView.findViewById(R.id.img);
        TextView order_content = holder.itemView.findViewById(R.id.order_content);
        TextView order_creat_time = holder.itemView.findViewById(R.id.order_creat_time);
        TextView order_money = holder.itemView.findViewById(R.id.order_money);
        View line_placeholder = holder.itemView.findViewById(R.id.line_placeholder);
        ImageView img_angle = holder.itemView.findViewById(R.id.img_angle);

        RelativeLayout progress_bar = holder.itemView.findViewById(R.id.progress_bar);

        if (position == list.size()) {
            switch (loadState) {
                case LOADING:
                    progress_bar.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    progress_bar.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    progress_bar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            OrderFormNetBean.ResultBean.OrderBean orderBean = list.get(position);

            switch (orderBean.getDatatype()) {
                case 1:
                    img.setVisibility(View.GONE);
                    img_angle.setVisibility(View.VISIBLE);
                    Glide.with(context).load(SystemUtil.JudgeUrl(orderBean.getListimg())).error(R.drawable.banner_off).into(img_angle);
                    order_creat_time.setText(orderBean.getFulladdress());
                    SystemUtil.textMagicTool(context, order_money, orderBean.getProducttotalmoney(), context.getString(R.string.price_prompt),
                            R.dimen.dp14, R.dimen.dp10, R.color.black, R.color.black, "");
                    order_content.setText(orderBean.getShopname());
                    break;
                case 2:
                    img.setVisibility(View.GONE);
                    img_angle.setVisibility(View.VISIBLE);
                    Glide.with(context).load(SystemUtil.JudgeUrl(orderBean.getListimg())).error(R.drawable.banner_off).into(img_angle);
                    SystemUtil.textMagicTool(context, order_creat_time, orderBean.getFulladdress(), orderBean.getTime_txt(),
                            R.dimen.dp13, R.dimen.dp13, R.color.black_overlay, R.color.black_overlay, "\n");
                    SystemUtil.textMagicTool(context, order_money, context.getString(R.string.money), orderBean.getProducttotalmoney(),
                            R.dimen.dp10, R.dimen.dp14, R.color.black, R.color.black, "");
                    order_content.setText(orderBean.getCoursesname());
                    break;
                case 3:
                    img.setVisibility(View.VISIBLE);
                    img_angle.setVisibility(View.GONE);
                    Glide.with(context).load(SystemUtil.JudgeUrl(orderBean.getListimg())).error(R.drawable.banner_off).into(img);
                    order_creat_time.setText(orderBean.getTime_txt());
                    SystemUtil.textMagicTool(context, order_money, context.getString(R.string.money), orderBean.getProducttotalmoney(),
                            R.dimen.dp10, R.dimen.dp14, R.color.black, R.color.black, "");
                    order_content.setText(orderBean.getCoursesname());
                    break;
            }

            refreshView(holder, orderBean, position);

            if (position != list.size() - 1) {
                line_placeholder.setVisibility(View.VISIBLE);
            } else {
                line_placeholder.setVisibility(View.GONE);
            }

        }
    }

    //刷新
    public void refreshView(MyViewHolder holder, OrderFormNetBean.ResultBean.OrderBean orderBean, int position) {
        View line = holder.itemView.findViewById(R.id.line);
        TextView controller_right = holder.itemView.findViewById(R.id.controller_right);
        TextView controller_life = holder.itemView.findViewById(R.id.controller_life);
        TextView order_status = holder.itemView.findViewById(R.id.order_status);
        order_status.setText(orderBean.getState());
        order_status.setTextColor(context.getResources().getColor(R.color.blue_bar));
        line.setVisibility(View.VISIBLE);
        controller_life.setVisibility(View.VISIBLE);
        controller_right.setVisibility(View.VISIBLE);
        controller_life.setText(context.getString(R.string.cancle_order));

        if (orderBean.getState().equals(context.getString(R.string.already_complete)) || orderBean.getState().equals(context.getString(R.string.already_cancle)) || orderBean.getState().equals(context.getString(R.string.waiting_complete))) {

            if (orderBean.getState().equals(context.getString(R.string.waiting_complete))) {
                controller_right.setVisibility(View.VISIBLE);
                controller_life.setVisibility(View.GONE);
                controller_right.setText(context.getString(R.string.complete_order));
            }

            if (orderBean.getState().equals(context.getString(R.string.already_complete))) {
                controller_life.setVisibility(View.GONE);
                controller_right.setVisibility(View.VISIBLE);
                if (orderBean.getIfcommented().equals("0")) {
                    controller_right.setText(context.getString(R.string.evaluation));
                } else {
                    controller_right.setText(context.getString(R.string.additional_evaluation));
                }
            }

            if (orderBean.getState().equals(context.getString(R.string.already_cancle))) {
                controller_life.setVisibility(View.GONE);
                controller_right.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                order_status.setTextColor(context.getResources().getColor(R.color.gray_light_text));
            }
        } else {
            controller_life.setVisibility(View.VISIBLE);
            controller_right.setVisibility(View.VISIBLE);
            if (orderBean.getState().equals(context.getString(R.string.waiting_payment))) {
                controller_right.setText(context.getString(R.string.take_payment));
            }
        }
        onClickListener(holder.itemView, position);
        onClickListener(controller_life, position);
        onClickListener(controller_right, position);
    }

    private void onClickListener(final View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderFormClickListener != null)
                    onOrderFormClickListener.onOrderFormClickListener(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyItemChanged(list.size());
    }

    public interface OnOrderFormClickListener {
        void onOrderFormClickListener(View view, int position);
    }

    private OnOrderFormClickListener onOrderFormClickListener;

    public void setOnOrderFormClickListener(OnOrderFormClickListener onOrderFormClickListener) {
        this.onOrderFormClickListener = onOrderFormClickListener;
    }


}
