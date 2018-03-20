package com.lynnsze.materialwebview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mItemView;
    private Context mContext;
    protected int mLayoutId;


    public CustomViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mItemView = itemView;
        this.mViews = new SparseArray<View>();
    }


    public static CustomViewHolder createViewHolder(Context context,
                                                    ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        CustomViewHolder holder = new CustomViewHolder(context, itemView);
        holder.mLayoutId = layoutId;
        return holder;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public View getItemView() {
        return mItemView;
    }


    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public CustomViewHolder setBackgroundColor(int viewId, int color)
    {
        View view = getView(viewId);
        if(view==null)return this;
        view.setBackgroundColor(color);
        return this;
    }

    public CustomViewHolder setItemBackgroundColor( int color)
    {
        if(mItemView==null)return this;
        mItemView.setBackgroundColor(color);
        return this;
    }


    public CustomViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CustomViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        if (view == null) return this;
        view.setTextColor(textColor);
        return this;
    }



    public CustomViewHolder setOnClickListener(int viewId,
                                               View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public CustomViewHolder setItemClickListener(View.OnClickListener listener) {
        if (mItemView == null) return this;
        mItemView.setOnClickListener(listener);
        return this;
    }
}