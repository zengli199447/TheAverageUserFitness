package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.adapter.DynamicPhotoAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerReleaseNewDynamic extends ControllerClassObserver implements DynamicPhotoAdapter.PhotoClickListener{

    RecyclerView recycler_view;
    private AlbumBuilder albumBuilder;
    private ShowDialog instance;
    private DynamicPhotoAdapter dynamicPhotoAdapter;
    private int deletPosition;

    public ControllerReleaseNewDynamic(RecyclerView recycler_view) {
        this.recycler_view = recycler_view;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.PHOTO_REFRESH:
                refreshAdapter();
                break;
            case EventCode.PHOTO_SAVE:
                DataClass.AlbumFileList.remove(deletPosition);
                refreshAdapter();
                break;
        }
    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        albumBuilder = new AlbumBuilder(context);
        instance = ShowDialog.getInstance();
        initAdapter();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context,false,3));
        dynamicPhotoAdapter = new DynamicPhotoAdapter(context, DataClass.AlbumFileList);
        recycler_view.setAdapter(dynamicPhotoAdapter);
        dynamicPhotoAdapter.setPhotoClickListener(this);
    }

    private void refreshAdapter() {
        dynamicPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void AddClickListener() {
        albumBuilder.ImageSelection(9);
    }

    @Override
    public void PhotoClickListener(int positon) {
        ArrayList<String> strings = new ArrayList<>();
        for (AlbumFile albumFile : DataClass.AlbumFileList) {
            strings.add(albumFile.getPath());
        }
        albumBuilder.ImageTheExhibition(strings, true, positon);
    }

    @Override
    public void DeletPhotoClickListener(int positon) {
        deletPosition = positon;
        ShowOrSelect(EventCode.PHOTO_OR_REMOVER);
    }

    /**
     * 提示
     *
     * @param type (内容保存/图片删除)
     */
    public void ShowOrSelect(int type) {
        switch (type) {
            case EventCode.DYNAMIC_OR_SAVE:
                instance.showConfirmOrNoDialog(context, context.getString(R.string.dynamic_or_save), EventCode.ONSTART, EventCode.DYNAMIC_SAVE, EventCode.DYNAMIC_CANCLE);
                break;
            case EventCode.PHOTO_OR_REMOVER:
                instance.showConfirmOrNoDialog(context, context.getString(R.string.photo_or_remover), EventCode.ONSTART, EventCode.PHOTO_SAVE, EventCode.PHOTO_CANCLE);
                break;
        }
    }


}
