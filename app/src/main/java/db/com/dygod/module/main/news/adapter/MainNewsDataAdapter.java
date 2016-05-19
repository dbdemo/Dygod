package db.com.dygod.module.main.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import db.com.dygod.R;
import db.com.dygod.bean.MainNesEntity;

/**
 * Created by zdb on 2016/5/19.
 */
public class MainNewsDataAdapter extends BaseAdapter {

    private ArrayList<MainNesEntity> mMainNesEntities;
    private Context mContext;

    public MainNewsDataAdapter(Context mContext,ArrayList<MainNesEntity> mMainNesEntities) {
        this.mMainNesEntities = mMainNesEntities;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mMainNesEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.mainnews_item, null);
            viewHolder.time = (TextView) convertView.findViewById(R.id.mainnews_item_time);
            viewHolder.title = (TextView) convertView.findViewById(R.id.mainnews_item_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(mMainNesEntities.get(position).getTitle());
        viewHolder.time.setText(mMainNesEntities.get(position).getTime());

        return convertView;
    }

    class ViewHolder {
        public TextView title;
        public TextView time;
    }

}
