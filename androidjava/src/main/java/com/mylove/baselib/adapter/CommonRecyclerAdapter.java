package com.mylove.baselib.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylove.baselib.viewholder.RecyclerHolder;

import java.util.List;

/**
 * @author LYjw
 */

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {
    /**
     * 上下文
     */
    private Context context;
    /**
     * 数据源
     */
    private List<T> list;
    /**
     * 布局器
     */
    private LayoutInflater inflater;
    /**
     * 布局id
     */
    private int itemLayoutId;
    /**
     * 是否在滚动
     */
    private boolean isScrolling;
    /**
     * 点击事件监听器
     */
    private OnItemClickListener listener;
    /**
     * 长按监听器
     */
    private OnItemLongClickListener longClickListener;
    private RecyclerView recyclerView;
    /**
     * 说明是带有Header的
     */
    private int TYPE_HEADER = 0;
    /**
     * 说明是带有Footer的
     */
    private int TYPE_FOOTER = 1;
    /**
     * 说明是不带有header和footer的
     */
    private int TYPE_NORMAL = 2;
    private View mHeaderView;
    private View mFooterView;

    public CommonRecyclerAdapter(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        this.inflater = LayoutInflater.from(context);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("headerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            this.mHeaderView = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void addFooterView(View footerView) {
        if (haveFootView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            this.mFooterView = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public boolean haveHeaderView() {
        return mHeaderView != null;
    }

    public boolean haveFootView() {
        return mFooterView != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFootView() && position == getItemCount() - 1;
    }

    /**
     * 重写这个方法，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        } else if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    /**
     * 在RecyclerView提供数据的时候调用
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (this.recyclerView == null && this.recyclerView != recyclerView) {
                this.recyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    private void ifGridLayoutManager() {
        if (recyclerView == null) {
            return;
        }
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ? ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 定义一个点击事件接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }

    interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView parent, View view, int position);
    }

    /**
     * 插入一项
     */
    public void insert(T item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 删除一项
     *
     * @param position 删除位置
     */
    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void notifyDataSetChanged(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 创建view
     */
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return RecyclerHolder.getRecyclerHolder(context, mHeaderView);
        } else if (mFooterView != null && viewType == TYPE_FOOTER) {
            return RecyclerHolder.getRecyclerHolder(context, mFooterView);
        } else {
            View view = inflater.inflate(itemLayoutId, parent, false);
            return RecyclerHolder.getRecyclerHolder(context, view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && v != null && recyclerView != null) {
                    int i = recyclerView.getChildAdapterPosition(v);
                    listener.onItemClick(recyclerView, v, i);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null && v != null && recyclerView != null) {
                    int i = recyclerView.getChildAdapterPosition(v);
                    longClickListener.onItemLongClick(recyclerView, v, i);
                    return true;
                }
                return false;
            }
        });
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) {
                position--;
            }
            if (position < list.size()) {
                convert(holder, list.get(position), position, isScrolling);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = list == null ? 0 : list.size();
        if (mHeaderView != null) {
            count++;
        }
        if (mFooterView != null) {
            count++;
        }
        return count;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    /**
     * 填充RecyclerView适配器的方法，子类需要重写
     *
     * @param mHolder     ViewHolder
     * @param item        子项
     * @param position    位置
     * @param isScrolling 是否在滑动
     */
    public abstract void convert(RecyclerHolder mHolder, T item, int position, boolean isScrolling);
}
