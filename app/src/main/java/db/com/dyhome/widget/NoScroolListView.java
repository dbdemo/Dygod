package db.com.dyhome.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zdb on 2017/1/22.
 */

public class NoScroolListView extends ListView {
    public NoScroolListView(Context context) {
        super(context);
    }

    public NoScroolListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScroolListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
