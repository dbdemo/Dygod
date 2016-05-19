package db.com.dygod.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zdb on 2016/5/17.
 */
public abstract class BaseFragment extends Fragment {

    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
