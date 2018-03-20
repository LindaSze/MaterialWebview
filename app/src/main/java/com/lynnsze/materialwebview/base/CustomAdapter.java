package com.lynnsze.materialwebview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class CustomAdapter<T> extends RecyclerView.Adapter<CustomViewHolder> {

    private int mItemLayoutId;
    private Context mContext;
    private ArrayList<T> mDataList;
    private onRecyclerViewItemClick mOnRvItemClick;

    public CustomAdapter(Context context, ArrayList<T> items, int itemLayoutId, onRecyclerViewItemClick onItemClick) {
        this.mContext = context;
        this.mDataList = items;
        this.mItemLayoutId = itemLayoutId;
        this.mOnRvItemClick = onItemClick;
    }

    public ArrayList<T> getData() {
        return mDataList;
    }

    public void setData(ArrayList<T> items) {
        this.mDataList = items;
    }


    public T getItem(int position) {
        return mDataList.get(position);
    }

    public void insert(T item, int position) {
        if (item == null)
            return;
        if (mDataList == null)
            return;
        mDataList.add(position, item);
        notifyItemInserted(position);
    }

    public void delete(int position) {
        if (mDataList == null)
            return;
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void add(T item) {
        if (item == null)
            return;
        if (mDataList == null)
            return;
        mDataList.ensureCapacity(getItemCount() + 1);
        mDataList.add(item);
    }

    public void clear() {
        mDataList.clear();
    }

    @Override
    public int getItemCount() {
        if (mDataList == null)
            return 0;
        return mDataList.size();
    }


    public abstract void buildItemViewHolder(CustomViewHolder holder, T t, int position);


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CustomViewHolder.createViewHolder(mContext, parent, mItemLayoutId);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnRvItemClick != null)
                    mOnRvItemClick.onItemClick(view, position);
            }
        });
        buildItemViewHolder(holder, getItem(position), position);
    }

    public interface onRecyclerViewItemClick {
        void onItemClick(View v, int position);
    }
}
