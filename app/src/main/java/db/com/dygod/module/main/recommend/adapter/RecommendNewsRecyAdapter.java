package db.com.dygod.module.main.recommend.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import db.com.dygod.R;
import db.com.dygod.bean.MainNesEntity;

/**
 * Created by zdb on 2016/10/20.
 */

public class RecommendNewsRecyAdapter extends RecyclerView.Adapter<RecommendNewsRecyAdapter.ViewHolder> {

    private ArrayList<MainNesEntity> mMainNesEntities;
    private RecyclerViewItemClickListener mItemClickListener;

    public RecommendNewsRecyAdapter(ArrayList<MainNesEntity> mMainNesEntities, RecyclerViewItemClickListener listener) {
        this.mMainNesEntities = mMainNesEntities;
        this.mItemClickListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= View.inflate(parent.getContext(),R.layout.item_common,null);
        return new ViewHolder(itemView,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mMainNesEntities.get(position).getTitle());
        holder.time.setText(mMainNesEntities.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mMainNesEntities.size();
    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(RecyclerViewItemClickListener listener){
        this.mItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public TextView time;
        private RecyclerViewItemClickListener listener;

        public ViewHolder(View itemView,RecyclerViewItemClickListener listener) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.mainnews_item_time);
            title = (TextView) itemView.findViewById(R.id.mainnews_item_title);
            itemView.setOnClickListener(this);
            this.listener=listener;
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                listener.onItemClick(v,getPosition());
            }
        }
    }
    /***
     * item点击事件
     */
    public interface RecyclerViewItemClickListener{
        void onItemClick(View view,int position);
    }
}


