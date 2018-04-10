package com.narancommunity.app.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class EasyRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    private LayoutInflater mInflater;
    protected List<T> mList;
    protected final Object mLock = new Object();
    private boolean mNotifyOnChange = true;

    public EasyRecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.setList(list);
    }

    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        synchronized (mLock) {
            if (list == null)
                this.mList = new ArrayList<>();
            else
                this.mList = list;
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public void add(int location, T object) {
        synchronized (mLock) {
            if (mList == null) {
                this.mList = new ArrayList<>();
            }
            mList.add(location, object);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public boolean add(T object) {
        synchronized (mLock) {
            if (this.mList == null) {
                this.mList = new ArrayList<>();
            }
            boolean success = false;
            success = mList.add(object);
            if (mNotifyOnChange)
                notifyDataSetChanged();
            return success;
        }
    }

    public void addAll(int location, List<? extends T> list) {
        synchronized (mLock) {
            if (mList == null) {
                this.mList = new ArrayList<>();
            }
            mList.addAll(location, list);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public void addAll(List<? extends T> list) {
        synchronized (mLock) {
            if (mList == null) {
                this.mList = new ArrayList<>();
            }
            mList.addAll(list);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public T remove(int location) {
        synchronized (mLock) {
            T object = null;
            if (mList != null) {
                object = mList.remove(location);
                if (mNotifyOnChange)
                    notifyDataSetChanged();
            }
            return object;
        }
    }

    public void remove(T object) {
        synchronized (mLock) {
            if (mList == null) {
                return;
            }
            mList.remove(object);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public boolean removeAll(List<?> list) {
        synchronized (mLock) {
            boolean success = false;
            if (mList != null) {
                success = mList.removeAll(list);
                if (mNotifyOnChange)
                    notifyDataSetChanged();
            }
            return success;
        }
    }

    public void clear() {
        synchronized (mLock) {
            if (mList == null) {
                return;
            }
            mList.clear();
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            if (mList == null) {
                return;
            }
            Collections.sort(mList, comparator);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }
}
