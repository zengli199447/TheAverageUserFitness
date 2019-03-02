package com.example.administrator.sportsFitness.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.ui.view.FlowLayout;
import com.example.administrator.sportsFitness.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 作者：真理 Created by Administrator on 2018/12/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FlowLayoutBuilder {

    private final Random random;
    String TAG = getClass().getSimpleName();

    private final LayoutInflater mInflater;
    Context context;
    FlowLayout flowLayout;
    private List<String> list = new ArrayList<>();
    private List<Drawable> colorList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private int type;

    public FlowLayoutBuilder(Context context, FlowLayout flowLayout, int type) {
        this.context = context;
        this.flowLayout = flowLayout;
        this.type = type;
        mInflater = LayoutInflater.from(context);
        random = new Random();
        initColors();
    }

    private void initColors() {
        colorList.add(context.getResources().getDrawable(R.drawable.corners_soild_layout_colorful_accent));
        colorList.add(context.getResources().getDrawable(R.drawable.corners_soild_layout_colorful_blue));
        colorList.add(context.getResources().getDrawable(R.drawable.corners_soild_layout_colorful_orange));
        colorList.add(context.getResources().getDrawable(R.drawable.corners_soild_layout_colorful_orange_max));
        colorList.add(context.getResources().getDrawable(R.drawable.corners_soild_layout_colorful_purple));
        colorList.add(context.getResources().getDrawable(R.drawable.corners_soild_layout_colorful_red));
        colorList.add(context.getResources().getDrawable(R.drawable.corners_soild_layout_colorful_red_end));
        colorList.add(context.getResources().getDrawable(R.drawable.corners_soild_layout_colorful_yellow));
    }

    public void initLayout(List<String> list, boolean status, boolean deleteStatus) {
        flowLayout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            if (i > context.getResources().getInteger(R.integer.search_history_log))
                return;
            FrameLayout inflate = (FrameLayout) mInflater.inflate(R.layout.item_tag_label, flowLayout, false);
            TextView contentTextView = inflate.findViewById(R.id.contentTextView);
            if (i == list.size() - 1 && status) {
                contentTextView.setBackground(context.getResources().getDrawable(R.drawable.corners_hollow_bg_gray));
                contentTextView.setTextColor(context.getResources().getColor(R.color.gray_light_text));
            } else {
                if (type == 0) {
                    contentTextView.setBackground(colorList.get(random.nextInt(colorList.size()) + 0));
                    contentTextView.setTextColor(context.getResources().getColor(R.color.white));
                } else {
                    contentTextView.setBackground(context.getResources().getDrawable(R.drawable.corners_hollow_bg_gray));
                    contentTextView.setTextColor(context.getResources().getColor(R.color.gray_light_text));
                }
            }
            ImageView delete = inflate.findViewById(R.id.delete);
            if (deleteStatus)
                delete.setVisibility(View.VISIBLE);
            contentTextView.setText(list.get(i));
            flowLayout.addView(inflate);
            OnClickListener(contentTextView, i);
            OnClickListener(delete, i);
        }
    }

    private void OnClickListener(View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flowClickListener != null)
                    flowClickListener.onFlowClickListener(v, position, type);
            }
        });
    }

    public interface FlowClickListener {
        void onFlowClickListener(View view, final int position, int type);
    }

    private FlowClickListener flowClickListener;

    public void setFlowClickListener(FlowClickListener flowClickListener) {
        this.flowClickListener = flowClickListener;
    }


}
