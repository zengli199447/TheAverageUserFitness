package com.example.administrator.sportsfitness.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.model.bean.WalletLogNetBean;
import com.example.administrator.sportsfitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsfitness.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class BillingAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<WalletLogNetBean.ResultBean.MoneydetaillistBean> list;

    public BillingAdapter(Context context, List<WalletLogNetBean.ResultBean.MoneydetaillistBean> list) {
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
        View line = holder.itemView.findViewById(R.id.line);

        WalletLogNetBean.ResultBean.MoneydetaillistBean moneydetaillistBean = list.get(position);

        SystemUtil.textMagicTool(context, content, moneydetaillistBean.getRemark(), moneydetaillistBean.getCreatedate(),
                R.dimen.dp15, R.dimen.dp12, R.color.black, R.color.gray_light_text, "\n");

        amount.setText(moneydetaillistBean.getOptmoney());

        if (moneydetaillistBean.getOptmoney().contains("+")) {
            amount.setTextColor(context.getResources().getColor(R.color.blue_bar));
        } else {
            amount.setTextColor(context.getResources().getColor(R.color.gray_light_text));
        }

        if (position == list.size() - 1) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }


}
