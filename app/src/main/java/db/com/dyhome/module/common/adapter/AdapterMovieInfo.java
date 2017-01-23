package db.com.dyhome.module.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import db.com.dyhome.R;
import db.com.dyhome.bean.DownloadEntity;

/**
 * Created by zdb on 2017/1/20.
 */

public class AdapterMovieInfo extends RecyclerView.Adapter<AdapterMovieInfo.MovieInfoHolder> {
    private List<DownloadEntity> data;
    private Context context;
    private RecyclerViewItemClickListener mItemClickListener;


    public void setmItemClickListener(RecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public AdapterMovieInfo(List<DownloadEntity> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public AdapterMovieInfo.MovieInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_movieinfo_list, parent, false);
        return new MovieInfoHolder(rootView,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(MovieInfoHolder holder, int position) {
        holder.item_movieinfo_name.setText(data.get(position).getName() + "");

        if (data.get(position).getContents() != null) {
            String des = "";
            for (int i = 0; i < data.get(position).getContents().size(); i++) {
                des = des + data.get(position).getContents().get(i) + "\n";
            }
            holder.item_movieinfo_des.setText(des + "");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MovieInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView item_movieinfo_name;
        public TextView item_movieinfo_des;
        private RecyclerViewItemClickListener listener;

        public MovieInfoHolder(View itemView ,RecyclerViewItemClickListener listener) {
            super(itemView);
            item_movieinfo_name = (TextView) itemView.findViewById(R.id.item_movieinfo_name);
            item_movieinfo_des = (TextView) itemView.findViewById(R.id.item_movieinfo_des);
            itemView.setOnClickListener(this);
            this.listener=listener;
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                listener.onItemClick(v,getLayoutPosition());
            }
        }
    }

    /***
     * item点击事件
     */
    public interface RecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
}
