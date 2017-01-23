package db.com.dyhome.module.main.recommend.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.R;
import db.com.dyhome.bean.FillmEntity;
import db.com.dyhome.bean.MainNesEntity;
import db.com.dyhome.define.UrlConstant;
import db.com.dyhome.utils.ImageLoaderUtils;

/**
 * Created by zdb on 2016/10/20.
 */

public class RecommendNewsRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FillmEntity> mData;
    private RecyclerViewItemClickListener mItemClickListener;
    private RecyclerViewLoadMoreListener mLoadMoreListener;
    private ViewHolderFooter mViewHolderFooter;

    private final int FOOTER_ITEM = 1;

    public RecommendNewsRecyAdapter(List<FillmEntity> mData, RecyclerViewItemClickListener listener, RecyclerViewLoadMoreListener loadMoreListener) {
        this.mData = (ArrayList<FillmEntity>) mData;
        this.mItemClickListener = listener;
        this.mLoadMoreListener = loadMoreListener;
    }

    /***
     * 正在加载的提示信息
     */
    public void setFooterLoningInfo() {
        if (mViewHolderFooter == null) {
            return;
        }
        mViewHolderFooter.footerPro.setVisibility(View.VISIBLE);
        mViewHolderFooter.footerText.setText("正在加载数据");
        mViewHolderFooter.footerLayout.setClickable(false);
    }

    /***
     * 没有信息时的提示
     */
    public void setFooterNoDataInfo(String str) {
        if (mViewHolderFooter == null) {
            return;
        }
        mViewHolderFooter.footerPro.setVisibility(View.GONE);
        mViewHolderFooter.footerText.setText(str);
        mViewHolderFooter.footerLayout.setClickable(false);
    }


    /***
     * 加载错误的时候的提示信息
     */
    public void setLoadErrInfo() {
        if (mViewHolderFooter == null) {
            return;
        }
        mViewHolderFooter.footerPro.setVisibility(View.GONE);
        mViewHolderFooter.footerText.setText("加载错误，点我重试");
        mViewHolderFooter.footerLayout.setClickable(true);
    }

    /****
     * 隐藏 或者显示Footer
     */
    public void setFooterItemVisibility() {
        if (mViewHolderFooter == null) {
            return;
        }
        ViewGroup.LayoutParams params = mViewHolderFooter.footerLayout.getLayoutParams();
        params.height = 0;
        mViewHolderFooter.footerLayout.setLayoutParams(params);
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return FOOTER_ITEM;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_ITEM) {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_common, parent, false);
            return new ViewHolderFooter(footerView, mLoadMoreListener);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common, parent, false);
        return new ViewHolder(itemView, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderFooter) {
            mViewHolderFooter = (ViewHolderFooter) holder;
            if (mLoadMoreListener != null) {
                setFooterLoningInfo();
                mLoadMoreListener.onLoadMore();
            } else {
                setFooterItemVisibility();
            }
        } else {
            ((ViewHolder) holder).title.setText(mData.get(position).getTitle());
            ((ViewHolder) holder).time.setText(mData.get(position).getTime());
            ((ViewHolder) holder).tags.setText(mData.get(position).getGrade());
            ImageLoaderUtils.displayAvatar(mData.get(position).getImg(), ((ViewHolder) holder).imgs);
            ((ViewHolder) holder).filename.setText(mData.get(position).getDesc());
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size() == 0) {
            return 0;
        }
        return mData.size() + 1;
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(RecyclerViewItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    class ViewHolderFooter extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView footerText;
        public ProgressBar footerPro;
        public LinearLayout footerLayout;
        private RecyclerViewLoadMoreListener loadMoreListener;

        public ViewHolderFooter(View itemView, RecyclerViewLoadMoreListener loadMoreListener) {
            super(itemView);
            this.loadMoreListener = loadMoreListener;
            footerLayout = (LinearLayout) itemView.findViewById(R.id.footer_load_layout);
            footerLayout.setOnClickListener(this);
            footerPro = (ProgressBar) itemView.findViewById(R.id.footer_load_pro);
            footerText = (TextView) itemView.findViewById(R.id.footer_load_more);
        }

        @Override
        public void onClick(View v) {
            setFooterLoningInfo();
            if (loadMoreListener != null) {
                loadMoreListener.onLoadMore();
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public TextView title;
        public TextView time;

        public ImageView imgs;
        public TextView tags;
        public TextView filename;

        private RecyclerViewItemClickListener listener;

        public ViewHolder(View itemView, RecyclerViewItemClickListener listener) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.mainnews_item_time);
            title = (TextView) itemView.findViewById(R.id.mainnews_item_title);
            imgs = (ImageView) itemView.findViewById(R.id.mainnews_news_img);
            tags = (TextView) itemView.findViewById(R.id.mainnews_item_tags);
            filename = (TextView) itemView.findViewById(R.id.mainnews_item_filename);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, getPosition());
            }
        }
    }

    /***
     * item点击事件
     */
    public interface RecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface RecyclerViewLoadMoreListener {
        void onLoadMore();
    }
}


