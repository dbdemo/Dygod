package db.com.dyhome.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import db.com.dyhome.R;

/**
 * Created by zdb on 2016/5/24.
 */
public class StyleDialog extends Dialog{
    private Context context;
    private String desc;
    private TextView descView;
    public interface ClickListenerInterface {
         void doConfirm();
         void doCancel();
    }
    public StyleDialog(Context context,String desc) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.desc=desc;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    public void setDesc(String desc){
        descView.setText(desc);
    }
    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_styledialog, null);
        setContentView(view);
        descView = (TextView)view.findViewById(R.id.desc);
        descView.setText(desc);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8);
        lp.height=(int)(d.heightPixels*0.6);// 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }
}