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

    public FlowLayoutBuilder(Context context, FlowLayout flowLayout) {
        this.context = context;
        this.flowLayout = flowLayout;
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

    public void initLayout(List<Object> objectList, boolean status) {
        list.clear();
        flowLayout.removeAllViews();
        list.addAll(Arrays.asList(context.getResources().getStringArray(R.array.arrange)));
        for (int i = 0; i < list.size(); i++) {
            if (i > context.getResources().getInteger(R.integer.search_history_log))
                return;
            FrameLayout inflate = (FrameLayout) mInflater.inflate(R.layout.item_tag_label, flowLayout, false);
            TextView contentTextView = inflate.findViewById(R.id.contentTextView);
            contentTextView.setBackground(colorList.get(random.nextInt(colorList.size()) + 0));
            ImageView delete = inflate.findViewById(R.id.delete);
            if (status)
                delete.setVisibility(View.GONE);
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
                    flowClickListener.onFlowClickListener(v, position);
            }
        });
    }

    public interface FlowClickListener {
        void onFlowClickListener(View view, final int position);
    }

    private FlowClickListener flowClickListener;

    public void setFlowClickListener(FlowClickListener flowClickListener) {
        this.flowClickListener = flowClickListener;
    }


}
