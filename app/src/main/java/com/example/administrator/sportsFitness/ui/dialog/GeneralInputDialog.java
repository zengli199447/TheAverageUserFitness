package com.example.administrator.sportsFitness.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseDialog;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GeneralInputDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.content)
    EditText content_text;
    @BindView(R.id.title)
    TextView title;

    private Context context;
    private int eventCode;

    protected GeneralInputDialog(Context context, int themeResId, int EventCode) {
        super(context, themeResId);
        this.context = context;
        this.eventCode = EventCode;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_general_input;
    }

    @Override
    protected void initClass() {
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        switch (eventCode) {
            case EventCode.REPORT_INPUT:
                content_text.setHint(context.getString(R.string.input_report_content));
                title.setText(context.getString(R.string.report_content));
                break;
        }
        handler.sendEmptyMessageDelayed(0,100);
    }

    @Override
    protected void initListener() {

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            SystemUtil.windowToDark(context);
        }
    };

    @OnClick({R.id.btn_commit, R.id.cancle})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                if (content_text.getText().toString().isEmpty()) {
                    new ToastUtil(context).showToast(context.getString(R.string.empty_content));
                } else {
                    RxBus.getDefault().post(new CommonEvent(eventCode, content_text.getText().toString()));
                    dismiss();
                }
                break;
            case R.id.cancle:
                dismiss();
                break;
        }
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }

    public void showKeyboard() {
        if (content_text != null) {
            //设置可获得焦点
            content_text.setFocusable(true);
            content_text.setFocusableInTouchMode(true);
            //请求获得焦点
            content_text.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) content_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(content_text, 0);
        }
    }

}
