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
import com.example.administrator.sportsfitness.model.bean.CoachPrivateNetBean;
import com.example.administrator.sportsfitness.model.bean.CourseFormNetBean;
import com.example.administrator.sportsfitness.model.bean.FitnessCourseNetBean;
import com.example.administrator.sportsfitness.model.bean.GymFormNetBean;
import com.example.administrator.sportsfitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsfitness.ui.view.ShinyView;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.ViewBuilder;

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

    private final int TYPE_ITEM = 0;
    private final int TYPE_FOOTER = 3;
    private int loadState = 2;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;
    public final int LOADING = 1;
    public final int LOADING_GONE = -1;


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
        int Type = -1;
        if (position == list.size()) {
            Type = TYPE_FOOTER;
        } else {
            Object object = list.get(position);
            if (object instanceof CourseFormNetBean.ResultBean.CoursesBean) {
                Type = 0;
            } else if (object instanceof CoachPrivateNetBean.ResultBean.CoursesBean) {
                Type = 1;
            } else if (object instanceof GymFormNetBean.ResultBean.ShopBean) {
                Type = 2;
            }
        }
        return Type;
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coach_layout, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_the_gym_layout, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_magin_limit_footer_view, parent, false);
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
        RelativeLayout progress_bar = holder.itemView.findViewById(R.id.progress_bar);
        RelativeLayout footer_layout = holder.itemView.findViewById(R.id.footer_layout);


        //No.1
        TextView time = holder.itemView.findViewById(R.id.time);
        TextView coordinates = holder.itemView.findViewById(R.id.coordinates);

        //N02.
        TextView distance = holder.itemView.findViewById(R.id.distance);
        ShinyView shiny_view = holder.itemView.findViewById(R.id.shiny_view);

        //No3.
        TextView convention = holder.itemView.findViewById(R.id.convention);

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
                case LOADING_GONE:
                    ViewGroup.LayoutParams layoutParams = footer_layout.getLayoutParams();
                    layoutParams.height = 0;
                    footer_layout.setLayoutParams(layoutParams);
                    break;
                default:
                    break;
            }
        } else {
            switch (getItemViewType(position)) {
                case 0:
                    CourseFormNetBean.ResultBean.CoursesBean coursesBean = (CourseFormNetBean.ResultBean.CoursesBean) list.get(position);

                    title.setText(coursesBean.getCoursesname());

                    Glide.with(context).load(SystemUtil.JudgeUrl(coursesBean.getListimg())).error(R.drawable.banner_off).into(img);

                    SystemUtil.textMagicTool(context, price, context.getString(R.string.money), coursesBean.getPrice(), R.dimen.dp10, R.dimen.dp16, R.color.red_text, R.color.red_text, "");

                    time.setText(new StringBuffer().append(coursesBean.getDate_start()).append("-").append(coursesBean.getDate_end())
                            .append(" | ").append(coursesBean.getTime_start()).append("-").append(coursesBean.getTime_end()).toString());

                    hot.setText(coursesBean.getState());

                    coordinates.setText(coursesBean.getShopname());

                    if (Integer.valueOf(coursesBean.getYuyuetotal()) == Integer.valueOf(coursesBean.getTotal())) {
                        sign_up.setText(context.getString(R.string.full));
                        sign_up.setBackground(context.getResources().getDrawable(R.drawable.corners_immersed_in_gray));
                    } else {
                        sign_up.setText(context.getString(R.string.sign_up));
                        sign_up.setBackground(context.getResources().getDrawable(R.drawable.corners_immersed_in_blue));
                    }
                    break;
                case 1:
                    CoachPrivateNetBean.ResultBean.CoursesBean coursesPrivateBean = (CoachPrivateNetBean.ResultBean.CoursesBean) list.get(position);

                    title.setText(coursesPrivateBean.getSecondname());

                    time.setText(new StringBuffer().append(coursesPrivateBean.getDate_start()).append(" | ").
                            append(coursesPrivateBean.getTime_start()).append("-").append(coursesPrivateBean.getTime_end()).toString());

                    SystemUtil.textMagicTool(context, price, context.getString(R.string.money), coursesPrivateBean.getPrice(), R.dimen.dp10, R.dimen.dp16, R.color.red_text, R.color.red_text, "");

                    Glide.with(context).load(SystemUtil.JudgeUrl(coursesPrivateBean.getPhoto())).error(R.drawable.banner_off).into(img);

                    distance.setText(SystemUtil.JudgeFormatDistance(coursesPrivateBean.getDistance()));

                    shiny_view.setStarMark(Float.valueOf(coursesPrivateBean.getScore()));

                    convention.setText(new StringBuffer().append(coursesPrivateBean.getTotal_yuyue()).append(context.getString(R.string.appointment_count)));

                    sign_up.setText(context.getString(R.string.convention));

                    break;
                case 2:
                    GymFormNetBean.ResultBean.ShopBean shopBean = (GymFormNetBean.ResultBean.ShopBean) list.get(position);

                    title.setText(shopBean.getShopname());

                    time.setText(new StringBuffer().append(shopBean.getTime_start()).append("-").append(shopBean.getTime_end())
                            .append(" | ").append(shopBean.getPeopletotal()).append(context.getString(R.string.people_total_consumption)).toString());

                    SystemUtil.textMagicTool(context, price, shopBean.getPrice(), context.getString(R.string.price_prompt),
                            R.dimen.dp16, R.dimen.dp10, R.color.red_text, R.color.red_text, "");

                    Glide.with(context).load(SystemUtil.JudgeUrl(shopBean.getPhoto())).error(R.drawable.banner_off).into(img);

                    distance.setText(SystemUtil.JudgeFormatDistance(shopBean.getDistance()));

                    shiny_view.setStarMark(Float.valueOf(shopBean.getScore()));

                    sign_up.setText(context.getString(R.string.convention));

                    if (shopBean.getSelectTag() != null && !shopBean.getSelectTag().isEmpty())
                        sign_up.setText(shopBean.getSelectTag());

                    break;
            }

            if (maginStatus) {
                ViewBuilder.ChangeLinearLayoutViewMagin(context, layout_content, 15, 15, 15, 15);
                ViewBuilder.ChangeLinearLayoutViewMagin(context, line, 15, 0, 0, 0);
            }

            if (position == 0) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }

            onClickListener(holder.itemView, position);
            onClickListener(sign_up, position);

        }

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
            count = list.size() + 1;
        if (visibilityCount > 0)
            count = visibilityCount;
        return list.size() == 0 ? 0 : count;
    }

    public interface onFitnessCourseClickListener {
        void onFitnessCourseClickListener(View view, int position);
    }

    private onFitnessCourseClickListener onFitnessCourseClickListener;

    public void setOnFitnessCourseClickListener(onFitnessCourseClickListener onFitnessCourseClickListener) {
        this.onFitnessCourseClickListener = onFitnessCourseClickListener;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
    }

}
