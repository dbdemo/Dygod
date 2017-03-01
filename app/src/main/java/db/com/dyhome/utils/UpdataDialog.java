package db.com.dyhome.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import db.com.dyhome.R;

/**
 * Created by zdb on 2017/3/1.
 */

public class UpdataDialog extends Dialog {
    private TextView title;
    private TextView content;
    private TextView confirm;
    private TextView cancle;
    private View portrait_line;


    public UpdataDialog(Context context) {
        super(context, R.style.DialogStyle);
        initView();
    }

    private void initView() {
        setCancelable(false);
        setContentView(R.layout.dialog_layout);
        title = (TextView) findViewById(R.id.notification_title);
        content = (TextView) findViewById(R.id.notification_context);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        confirm = (TextView) findViewById(R.id.notification_confirm);
        cancle = (TextView) findViewById(R.id.notification_cancle);
        portrait_line = findViewById(R.id.dialog_cancle_firm_portrait_line);
        title.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);
        cancle.setVisibility(View.GONE);
        portrait_line.setVisibility(View.GONE);
    }

    public void setTitle(String str) {
        title.setVisibility(View.VISIBLE);
        title.setText(str);
    }

    public void setContext(String str) {
        content.setVisibility(View.VISIBLE);
        content.setText(str);
    }


    /**
     * 设置点击外部取消弹窗
     *
     * @param b true 可以取消
     */
    public void setCanceledOnTouchOutside(Boolean b) {
        this.setCanceledOnTouchOutside(b);
    }

    public void setConfirm(String str, View.OnClickListener listener) {
        if (TextUtils.isEmpty(str)) {
            str = "确认";
        }
        confirm.setVisibility(View.VISIBLE);
        confirm.setText(str);
        if (listener != null) {
            confirm.setOnClickListener(listener);
        }
    }

    public void setCancle(String str, View.OnClickListener listener) {
        if (TextUtils.isEmpty(str)) {
            str = "取消";
        }
        portrait_line.setVisibility(View.VISIBLE);
        cancle.setVisibility(View.VISIBLE);
        cancle.setText(str);
        if (listener != null) {
            cancle.setOnClickListener(listener);
        }
    }
}
