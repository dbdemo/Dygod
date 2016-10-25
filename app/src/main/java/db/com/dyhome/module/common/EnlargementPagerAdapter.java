package db.com.dyhome.module.common;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.ArrayList;

import db.com.dyhome.R;
import db.com.dyhome.utils.ImageLoaderUtils;
import db.com.dyhome.widget.gestureimageview.GestureImageView;

public class EnlargementPagerAdapter extends PagerAdapter {

    private ArrayList<String> mArrayUrl;
    private Context mContext;

    public EnlargementPagerAdapter(Context context, ArrayList<String> arrayUrl) {
        mContext = context;
        mArrayUrl = arrayUrl;
    }

    @Override
    public int getCount() {
        return mArrayUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_enlargement, null);
        final GestureImageView gestureImageView = (GestureImageView) view.findViewById(R.id.dmImageView);
        ProgressBar pro = (ProgressBar) view.findViewById(R.id.dmImageViewPro);
        ImageLoaderUtils.displayAvatar(mArrayUrl.get(position), gestureImageView, null);
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

  /*  class ImageLoadingProgressListenerSty implements ImageLoadingProgressListener {

        private ProgressBar pro;

        public ImageLoadingProgressListenerSty(ProgressBar pro) {
            this.pro = pro;
        }

        @Override
        public void onProgressUpdate(String imageUri, View view, int current, int total) {
            int proSize = (current / total) * 100;
            System.out.println("加载进度："+current);
            pro.setProgress(proSize);
            if(proSize==100){
                pro.setVisibility(View.GONE);
            }
        }
    }*/
}