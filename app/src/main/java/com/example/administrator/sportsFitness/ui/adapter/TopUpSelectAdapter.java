package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.model.bean.VipTopUpSelectNetBean;
import com.example.administrator.sportsFitness.model.bean.WalletLogNetBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TopUpSelectAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<WalletLogNetBean.ResultBean.MoneyinConfigBean> list;

    public TopUpSelectAdapter(Context context, List<WalletLogNetBean.ResultBean.MoneyinConfigBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_up_select, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        refreshView(holder, list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectClickListener != null)
                    onSelectClickListener.OnSelectClickListener(position);
            }
        });

    }

    public void refreshView(MyViewHolder holder, WalletLogNetBean.ResultBean.MoneyinConfigBean moneyinConfigBean) {
        RelativeLayout bg_layout = holder.itemView.findViewById(R.id.bg_layout);
        TextView money = holder.itemView.findViewById(R.id.money);
        TextView preferential = holder.itemView.findViewById(R.id.preferential);
        if (moneyinConfigBean.isSelectStatus()) {
            bg_layout.setBackground(context.getResources().getDrawable(R.drawable.corners_immersed_square_blue));
            SystemUtil.textMagicToolTypeFace(context, money, context.getString(R.string.money),
                    String.valueOf(moneyinConfigBean.getMoney_in()), R.dimen.dp10, R.dimen.dp15, R.color.white, R.color.white, "", false);

            preferential.setTextColor(context.getResources().getColor(R.color.white));
            preferential.setText(new StringBuffer().append(context.getString(R.string.giving)).append(moneyinConfigBean.getMoney_gift()).toString());

        } else {
            bg_layout.setBackground(context.getResources().getDrawable(R.drawable.corners_hollow_bg_gray));
            SystemUtil.textMagicToolTypeFace(context, money, context.getString(R.string.money),
                    String.valueOf(moneyinConfigBean.getMoney_in()), R.dimen.dp10, R.dimen.dp15, R.color.black, R.color.black, "", false);

            preferential.setTextColor(context.getResources().getColor(R.color.red_text));
            preferential.setText(new StringBuffer().append(context.getString(R.string.giving)).append(moneyinConfigBean.getMoney_gift()).toString());

        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 6 : list.size();
    }

    public interface OnSelectClickListener {
        void OnSelectClickListener(int position);
    }

    private OnSelectClickListener onSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        this.onSelectClickListener = onSelectClickListener;
    }

}
