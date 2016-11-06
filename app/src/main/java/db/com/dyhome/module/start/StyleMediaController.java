package db.com.dyhome.module.start;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import db.com.dyhome.R;
import io.vov.vitamio.widget.MediaController;

/**
 * Created by Administrator on 2016/11/3.
 */

public class StyleMediaController extends MediaController {
    private Context context;

    public StyleMediaController(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected View makeControllerView() {
        return null;
    }
}
