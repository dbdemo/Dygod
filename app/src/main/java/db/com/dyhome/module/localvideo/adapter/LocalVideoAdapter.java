package db.com.dyhome.module.localvideo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import db.com.dyhome.R;
import db.com.dyhome.bean.LocalVideoEntity;
import db.com.dyhome.utils.UnitsUtils;

/**
 * Created by Administrator on 2016/11/2.
 */

public class LocalVideoAdapter extends RecyclerView.Adapter<LocalVideoAdapter.ViewHolder> {
    private List<LocalVideoEntity> data;
    private boolean isCb = true;

    public LocalVideoAdapter(List<LocalVideoEntity> data) {
        this.data = data;
    }

    public boolean isCb() {
        return isCb;
    }

    public void setCb(boolean cb) {
        isCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_video, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocalVideoEntity entity = data.get(position);
        holder.title.setText(entity.getTitle());
        holder.size.setText(UnitsUtils.byteToPart(entity.getSize()));
        holder.image.setImageBitmap(entity.getImage());
        holder.isCb.setVisibility(isCb() ? View.GONE : View.VISIBLE);
        holder.isCb.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView size;
        public CheckBox isCb;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.local_video_image);
            title = (TextView) itemView.findViewById(R.id.local_video_title);
            size = (TextView) itemView.findViewById(R.id.local_video_size);
            isCb = (CheckBox) itemView.findViewById(R.id.local_video_cb);
        }
    }
}
