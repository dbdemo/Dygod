package db.com.dygod.module.common;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import db.com.dygod.R;

public class EnlargementPagerAdapter extends PagerAdapter {

        private  ArrayList<String> mArrayUrl;
        private Context mContext;

        public EnlargementPagerAdapter(Context context, ArrayList<String> arrayUrl) {
            mContext = context;
            mArrayUrl=arrayUrl;
        }

    @Override
    public int getCount() {
        return mArrayUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_enlargement, null);
       // final GestureImageView gestureImageView= (GestureImageView) view.findViewById(R.id.dmImageView);

       // PineConeApplication.getInstance().getImageLoaders().displayImage(mArrayUrl.get(position), gestureImageView );
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}