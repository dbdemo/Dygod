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
 * Created by zdb on 2016/11/2.
 */

public class LocalVideoAdapter extends RecyclerView.Adapter<LocalVideoAdapter.ViewHolder> {
    private List<LocalVideoEntity> data;
    private boolean isCb = true;
    private ItemClickListener itemClickListener;
    private ItemCheckedListener itemCheckedListener;
    public boolean allCheck = false;

    public boolean isAllCheck() {
        return allCheck;
    }

    public void setAllCheck(boolean allCheck) {
        this.allCheck = allCheck;
    }

    public LocalVideoAdapter(List<LocalVideoEntity> data, ItemClickListener itemClickListener, ItemCheckedListener itemCheckedListener) {
        this.data = data;
        this.itemClickListener = itemClickListener;
        this.itemCheckedListener = itemCheckedListener;
    }

    public boolean isCb() {
        return isCb;
    }

    public void setCb(boolean cb) {
        allCheck = false;
        isCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_video, parent, false);
        ViewHolder holder = new ViewHolder(itemView, itemClickListener, itemCheckedListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocalVideoEntity entity = data.get(position);
        holder.title.setText(entity.getTitle());
        holder.size.setText(UnitsUtils.byteToPart(entity.getSize()));
        holder.image.setImageBitmap(entity.getImage());
        holder.isCb.setVisibility(isCb() ? View.GONE : View.VISIBLE);
        holder.setChecked(isAllCheck());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;
        public TextView title;
        public TextView size;
        public CheckBox isCb;
        private ItemClickListener itemClickListener;
        private ItemCheckedListener itemCheckedListener;

        public void setChecked(boolean checked) {
            if (!String.valueOf(isCb.isChecked()).equals(String.valueOf(checked))) {
                isCb.setChecked(checked);
                if (itemCheckedListener != null) {
                    itemCheckedListener.onCheckedListener(checked, getAdapterPosition());
                }
            }
        }

        public ViewHolder(View itemView, ItemClickListener itemClickListener, ItemCheckedListener itemCheckedListener) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.local_video_image);
            title = (TextView) itemView.findViewById(R.id.local_video_title);
            size = (TextView) itemView.findViewById(R.id.local_video_size);
            isCb = (CheckBox) itemView.findViewById(R.id.local_video_cb);
            itemView.setOnClickListener(this);
            this.itemClickListener = itemClickListener;
            this.itemCheckedListener = itemCheckedListener;
        }

        @Override
        public void onClick(View v) {
            if (isCb()) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getPosition());
                }
                return;
            }
            setChecked(!isCb.isChecked());
        }
    }

    /***
     * item点击事件
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    /***
     * item点击事件
     */
    public interface ItemCheckedListener {
        void onCheckedListener(boolean check, int position);
    }
}
