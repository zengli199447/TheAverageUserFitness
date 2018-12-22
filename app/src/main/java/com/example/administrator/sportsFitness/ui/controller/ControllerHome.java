package com.example.administrator.sportsFitness.ui.controller;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.db.entity.SearchDBInfo;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.ui.view.FlowLayout;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerHome extends ControllerClassObserver implements View.OnKeyListener {

    EditText search_edit;
    FlowLayout history_search_layout;
    FrameLayout frameLayout;
    private LayoutInflater mInflater;

    public ControllerHome(EditText search_edit, FlowLayout history_search_layout, FrameLayout frameLayout) {
        this.search_edit = search_edit;
        this.history_search_layout = history_search_layout;
        this.frameLayout = frameLayout;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.SEARCH_CLEAR_ALL_COMMITE:
                dataManager.deleteSearchDBInfo(DataClass.USERID);
                HistorySearchView();
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
        search_edit.setOnKeyListener(this);
        mInflater = LayoutInflater.from(context);
        HistorySearchView();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            String inputContent = search_edit.getText().toString();
            if (inputContent.isEmpty())
                return true;
            searchAction(inputContent);
        }
        return false;
    }

    //历史搜索
    private void HistorySearchView() {
        history_search_layout.removeAllViews();
        List<SearchDBInfo> searchDBInfos = dataManager.querySearchDBInfo(DataClass.USERID);
        for (int i = 0; i < searchDBInfos.size(); i++) {
            if (i > context.getResources().getInteger(R.integer.search_history_log))
                return;
            TextView searchContent = (TextView) mInflater.inflate(R.layout.item_search_label, history_search_layout, false);
            searchContent.setText(searchDBInfos.get(searchDBInfos.size() - 1 - i).getSearchContent());
            history_search_layout.addView(searchContent);
            searchContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = ((TextView) v).getText().toString();
                    search_edit.setText("");
                    searchAction(s);
                    ViewBuilder.closeKeybord(search_edit);
                }
            });
        }
    }

    //搜索操作
    private void searchAction(String content) {
        RxBus.getDefault().post(new CommonEvent(EventCode.SEARCH_ACTION, content));
        frameLayout.setVisibility(View.VISIBLE);
        dataManager.insertSearchDBInfo(new SearchDBInfo(DataClass.USERID, content));
        HistorySearchView();
    }


}
