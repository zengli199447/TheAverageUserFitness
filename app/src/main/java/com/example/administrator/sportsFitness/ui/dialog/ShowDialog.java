package com.example.administrator.sportsFitness.ui.dialog;

import android.content.Context;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/3/19.
 */

public class ShowDialog {

    private static ShowDialog instance = new ShowDialog();

    private ShowDialog() {
    }

    public static ShowDialog getInstance() {
        return instance;
    }


    public void showInputDialog(final Context context, int eventCode) {
        final GeneralInputDialog generalInputDialog = new GeneralInputDialog(context, R.style.dialog, eventCode);
        generalInputDialog.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                generalInputDialog.showKeyboard();
            }
        }, 200);
    }

    public ConfirmOrNoDialog showConfirmOrNoDialog(final Context context, String content, int type, int commit, int cancle) {
        final ConfirmOrNoDialog confirmOrNoDialog = new ConfirmOrNoDialog(context, R.style.dialog, content, type,commit,cancle);
        confirmOrNoDialog.show();
        SystemUtil.windowToDark(context);
        return confirmOrNoDialog;
    }

}
